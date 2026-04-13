package engine;



import action.Action;
import combatant.Combatant;
import level.Level;
import strategy.TurnOrderStrategy;
import observers.AchievementTracker;
import observers.BattleEventListener;
import ui.GameCLI;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class BattleEngine {

    private final Combatant player;
    private final Level level;
    private final TurnOrderStrategy turnOrderStrategy;
    private final GameCLI ui;

    private final ArrayList<Combatant> livingEnemies = new ArrayList<>();
    private final ArrayList<BattleEventListener> listeners = new ArrayList<>();
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
        listeners.add(new AchievementTracker());
    }

    private void notifyDamageDealt(int damage) {
        for (BattleEventListener l : listeners) {
            l.onDamageDealt(damage);
        }
    }

    private void notifyCombatantDefeated(Combatant target) {
        for (BattleEventListener l : listeners) {
            l.onCombatantDefeated(target);
        }
    }

    private void notifyGameEnd(int round, boolean win) {
        for (BattleEventListener l : listeners) {
            l.onGameEnd(round, win);
        }
    }



    public boolean run() {
        ui.displayBattleStart(player, livingEnemies, level);

        while (!battleOver) {
            roundNumber++;
            runRound();

        }
        notifyGameEnd(roundNumber, playerWon);

        ui.displayBattleEnd(playerWon, player, livingEnemies, roundNumber, listeners);
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
            if (battleOver) {
                break;
            }
            processTurn(combatant);
            checkBattleEnd();
            if (battleOver) break;
        }

        if (battleOver) return;

        // Backup spawn check at end of round
        if(livingEnemies.isEmpty() && level.hasBackup() && !level.isBackupTriggered()){
            spawnBackup();
        }

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

        if (combatant.isPlayer()) {
            processPlayerTurn(combatant);
            // Decrement special skill cooldown at end of each player turn
            combatant.decrementCoolDown();
        } else{
            processEnemyTurn(combatant);
        }



    }

    // Player turn
    private void processPlayerTurn(Combatant player) {

        Action action = player.chooseAction(ui, livingEnemies,  player);


        if(action.isRequiresTarget()){

            Combatant target = ui.promptTargetSelection(livingEnemies);
            ArrayList <Combatant> targetList = new ArrayList<>();
            targetList.add(target);
            int damage = action.execute(player, targetList, ui);
            notifyDamageDealt(damage);

        } else{
            int damage = action.execute(player, livingEnemies, ui);
            notifyDamageDealt(damage);
        }



        removeDefeated();
    }




    // Enemy turn
    private void processEnemyTurn(Combatant enemy) {

        Action action = enemy.chooseAction(ui, livingEnemies,  player);
        ArrayList <Combatant> targetList = new ArrayList<>();
        targetList.add(player);
        action.execute(enemy, targetList, ui);

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
            notifyCombatantDefeated(e);
        }
        livingEnemies.removeAll(defeated);
    }




    private List<Combatant> buildCombatantList() {
        List<Combatant> all = new ArrayList<>();
        all.add(player);
        all.addAll(livingEnemies);
        return all;
    }
}
