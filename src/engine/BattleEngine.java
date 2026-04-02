package engine;



import action.Action;
import combatant.Combatant;
import level.Level;
import strategy.TurnOrderStrategy;
import ui.GameCLI;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class BattleEngine {

    private final Combatant player;
    private final Level level;
    private final TurnOrderStrategy turnOrderStrategy;
    private final GameCLI ui;

    private final List<Combatant> livingEnemies = new ArrayList<>();
    private int roundNumber = 0;
    private boolean battleOver = false;
    private boolean playerWon  = false;

    public BattleEngine(Combatant player, Level level,
                        TurnOrderStrategy turnOrderStrategy, GameCLI ui) {
        this.player            = player;
        this.level             = level;
        this.turnOrderStrategy = turnOrderStrategy;
        this.ui                = ui;
        livingEnemies.addAll(level.getInitialSpawn());
    }


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


    // Round logic
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
        spawnBackup();
        checkBattleEnd();

        ui.displayRoundSummary(roundNumber, player, livingEnemies);
    }

    private void processTurn(Combatant combatant) {
        // This decrements durations and removes expired effects.
        combatant.tickStatus();

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
            p.decrementCoolDown();
        }
    }

    // Player turn
    private void processPlayerTurn(Combatant player) {
        Action action = ui.promptPlayerAction(player, getLivingEnemies(), this);

        Combatant target = resolveTarget(action, player);
        action.execute(player, target);

        removeDefeated();
    }


    private Combatant resolveTarget(Action action, Combatant player) {

        if(action.isRequiresTarget()){
            return ui.promptTargetSelection(getLivingEnemies());
        }
        else if(action.isAOE()){
            return livingEnemies.isEmpty() ? player : livingEnemies.get(0);
        }
        else{
            return player;
        }

    }

    // Enemy turn
    private void processEnemyTurn(Combatant enemy) {

        Action action = enemy.chooseAction(player);
        action.execute(enemy, player);

        if (player.isDefeated()) {
            battleOver = true;
            playerWon  = false;
        }
    }


    // Backup spawn & win/loss
    private void spawnBackup() {
        if (level.hasBackup()) {
            List<Combatant> backup = level.getBackupSpawn();
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
        List<Combatant> defeated = livingEnemies.stream()
                .filter(Combatant::isDefeated)
                .collect(Collectors.toList());
        for (Combatant e : defeated) {
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
