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

    public boolean run() {
        ui.displayBattleStart(player, livingEnemies, level);

        while (!battleOver) {
            roundNumber++;
            runRound();
        }

        ui.displayBattleEnd(playerWon, player, livingEnemies, roundNumber);
        return playerWon;
    }


    private void runRound() {
        ui.displayRoundHeader(roundNumber);
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

        checkBackupSpawn();
        checkBattleEnd();

        ui.displayRoundSummary(roundNumber, player, livingEnemies);
    }

    private void processTurn(Combatant combatant) {
        combatant.tickStatusEffects();
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

        if (combatant instanceof Player p) {
            p.decrementCooldown();
        }
    }

    private void processPlayerTurn(Player player) {
        Action action = ui.promptPlayerAction(player, getLivingEnemies(), this);

        Combatant target = resolveTarget(action, player);
        action.execute(player, target, this);

       
        if (action instanceof ShieldBash || action instanceof ArcaneBlast) {
            player.triggerSpecialSkillCooldown();
        }

        removeDefeated();
    }

    private Combatant resolveTarget(Action action, Player player) {
        if (action instanceof BasicAttack || action instanceof ShieldBash) {
            return ui.promptTargetSelection(getLivingEnemies());
        } else if (action instanceof Defend || action instanceof ItemAction) {
            return player;  
        } else if (action instanceof ArcaneBlast) {
            return livingEnemies.isEmpty() ? player : livingEnemies.get(0); // AoE, target unused
        }
        return player;
    }

   
    private void processEnemyTurn(Enemy enemy) {
        List<Combatant> targets = List.of(player);
        Action action = enemy.chooseAction(targets);
        action.execute(enemy, player, this);

        if (player.isDefeated()) {
            battleOver = true;
            playerWon  = false;
        }
    }


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

    public List<Combatant> getLivingEnemies() {
        return new ArrayList<>(livingEnemies);
    }

    public boolean isSmokeBombActive(Combatant combatant) {
        return combatant.getActiveEffectNames().contains("Smoke Bomb");
    }

    public void executePowerStoneSkill(Player player) {
        Action skill = player.getSpecialSkill();

        Combatant target;
        if (skill instanceof ArcaneBlast) {
            target = livingEnemies.isEmpty() ? player : livingEnemies.get(0);
        } else {
            target = ui.promptTargetSelection(getLivingEnemies());
        }

        skill.execute(player, target, this);
        
        removeDefeated();
    }


    public void log(String message) {
        ui.displayLog(message);
    }

  
    public int getRoundNumber() { return roundNumber; }
    public Player getPlayer()   { return player; }

    private List<Combatant> buildCombatantList() {
        List<Combatant> all = new ArrayList<>();
        all.add(player);
        all.addAll(livingEnemies);
        return all;
    }
}
