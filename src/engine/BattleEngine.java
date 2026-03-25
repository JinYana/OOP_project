package engine;

import domain.action.Action;
import domain.action.ArcaneBlast;
import domain.action.BasicAttack;
import domain.action.Defend;
import domain.action.ItemAction;
import domain.action.ShieldBash;
import domain.combatant.Combatant;
import domain.combatant.Enemy;
import domain.combatant.Player;
import domain.level.Level;
import strategy.TurnOrderStrategy;
import ui.GameCLI;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Orchestrates all battle rounds: turn order, action resolution,
 * status effect ticking, win/loss detection, and backup spawning.
 *
 * DIP: depends on TurnOrderStrategy abstraction, not SpeedBasedTurnOrder directly.
 * OCP: new Action or StatusEffect types plug in without modifying this class.
 * SRP: battle flow management only — no UI, no stats, no spawn config.
 */
public class BattleEngine {

    private final Player player;
    private final Level level;
    private final TurnOrderStrategy turnOrderStrategy;
    private final GameCLI ui;

    private final List<Enemy> livingEnemies = new ArrayList<>();
    private int roundNumber = 0;
    private boolean battleOver = false;
    private boolean playerWon  = false;

    public BattleEngine(Player player, Level level,
                        TurnOrderStrategy turnOrderStrategy, GameCLI ui) {
        this.player            = player;
        this.level             = level;
        this.turnOrderStrategy = turnOrderStrategy;
        this.ui                = ui;
        livingEnemies.addAll(level.getInitialSpawn());
    }

    // =========================================================================
    // Public API
    // =========================================================================

    /** Runs the battle to completion. Returns true if the player wins. */
    public boolean run() {
        ui.displayBattleStart(player, livingEnemies, level);

        while (!battleOver) {
            roundNumber++;
            runRound();
        }

        ui.displayBattleEnd(playerWon, player, livingEnemies, roundNumber);
        return playerWon;
    }

    // =========================================================================
    // Round logic
    // =========================================================================

    private void runRound() {
        ui.displayRoundHeader(roundNumber);

        // Build full combatant list and determine turn order for this round
        List<Combatant> allCombatants = buildCombatantList();
        List<Combatant> turnOrder = turnOrderStrategy.determineTurnOrder(allCombatants);

        for (Combatant combatant : turnOrder) {
            if (combatant.isDefeated()) continue;
            if (battleOver) break;
            processTurn(combatant);
            checkBattleEnd();
            if (battleOver) break;
        }

        if (battleOver) return;

        // Backup spawn check at end of round
        checkBackupSpawn();
        checkBattleEnd();

        ui.displayRoundSummary(roundNumber, player, livingEnemies);
    }

    private void processTurn(Combatant combatant) {
        // Tick status effects at the start of this combatant's turn slot.
        // This decrements durations and removes expired effects.
        combatant.tickStatusEffects();

        // If still stunned after tick, skip turn
        if (combatant.isStunned()) {
            ui.displayStunSkip(combatant);
            return;
        }

        if (combatant.isDefeated()) return;

        if (combatant instanceof Player p) {
            processPlayerTurn(p);
        } else if (combatant instanceof Enemy e) {
            processEnemyTurn(e);
        }

        // Decrement special skill cooldown at end of each player turn
        if (combatant instanceof Player p) {
            p.decrementCooldown();
        }
    }

    // -------------------------------------------------------------------------
    // Player turn
    // -------------------------------------------------------------------------

    private void processPlayerTurn(Player player) {
        Action action = ui.promptPlayerAction(player, getLivingEnemies(), this);

        Combatant target = resolveTarget(action, player);
        action.execute(player, target, this);

        // Trigger cooldown only for direct special skill use (not PowerStone)
        if (action instanceof ShieldBash || action instanceof ArcaneBlast) {
            player.triggerSpecialSkillCooldown();
        }

        removeDefeated();
    }

    /**
     * Determines the appropriate target for each action type.
     * AoE actions (ArcaneBlast) pass a placeholder — the action iterates all enemies itself.
     */
    private Combatant resolveTarget(Action action, Player player) {
        if (action instanceof BasicAttack || action instanceof ShieldBash) {
            return ui.promptTargetSelection(getLivingEnemies());
        } else if (action instanceof Defend || action instanceof ItemAction) {
            return player;  // self-targeted
        } else if (action instanceof ArcaneBlast) {
            return livingEnemies.isEmpty() ? player : livingEnemies.get(0); // AoE, target unused
        }
        return player;
    }

    // -------------------------------------------------------------------------
    // Enemy turn
    // -------------------------------------------------------------------------

    private void processEnemyTurn(Enemy enemy) {
        List<Combatant> targets = List.of(player);
        Action action = enemy.chooseAction(targets);
        action.execute(enemy, player, this);

        if (player.isDefeated()) {
            battleOver = true;
            playerWon  = false;
        }
    }

    // -------------------------------------------------------------------------
    // Backup spawn & win/loss
    // -------------------------------------------------------------------------

    private void checkBackupSpawn() {
        List<Enemy> backup = level.checkAndGetBackupSpawn(livingEnemies);
        if (!backup.isEmpty()) {
            livingEnemies.addAll(backup);
            ui.displayBackupSpawn(backup);
        }
    }

    private void checkBattleEnd() {
        if (player.isDefeated()) {
            battleOver = true;
            playerWon  = false;
        } else if (livingEnemies.isEmpty()
                && (!level.hasBackup() || level.isBackupTriggered())) {
            // Win: no living enemies AND no further backup can spawn
            battleOver = true;
            playerWon  = true;
        }
    }

    private void removeDefeated() {
        List<Enemy> defeated = livingEnemies.stream()
                .filter(Combatant::isDefeated)
                .collect(Collectors.toList());
        for (Enemy e : defeated) {
            ui.displayEliminated(e);
        }
        livingEnemies.removeAll(defeated);
    }

    // =========================================================================
    // Public helpers called by Action / Item classes
    // =========================================================================

    /**
     * Returns a snapshot of currently living enemies.
     * Used by ArcaneBlast for AoE resolution.
     */
    public List<Combatant> getLivingEnemies() {
        return new ArrayList<>(livingEnemies);
    }

    /**
     * Returns true if a Smoke Bomb effect is currently active on the given combatant.
     * Called by BasicAttack before resolving enemy damage.
     */
    public boolean isSmokeBombActive(Combatant combatant) {
        return combatant.getActiveEffectNames().contains("Smoke Bomb");
    }

    /**
     * Executes the player's special skill via PowerStone WITHOUT triggering cooldown.
     * UI prompts for a target if needed.
     */
    public void executePowerStoneSkill(Player player) {
        Action skill = player.getSpecialSkill();

        Combatant target;
        if (skill instanceof ArcaneBlast) {
            target = livingEnemies.isEmpty() ? player : livingEnemies.get(0);
        } else {
            target = ui.promptTargetSelection(getLivingEnemies());
        }

        skill.execute(player, target, this);
        // Cooldown is intentionally NOT triggered here — PowerStone spec.
        removeDefeated();
    }

    /** Routes all battle log messages through the UI layer. */
    public void log(String message) {
        ui.displayLog(message);
    }

    // =========================================================================
    // Accessors
    // =========================================================================

    public int getRoundNumber() { return roundNumber; }
    public Player getPlayer()   { return player; }

    private List<Combatant> buildCombatantList() {
        List<Combatant> all = new ArrayList<>();
        all.add(player);
        all.addAll(livingEnemies);
        return all;
    }
}
