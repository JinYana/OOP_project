package observers;

import combatant.Combatant;

import java.util.ArrayList;

public class AchievementTracker extends BattleEventListener {

    private int totalDamageDealt = 0;
    private ArrayList<String> unlockedAchivements = new ArrayList<>();

    public void onDamageDealt(int damage) {

        totalDamageDealt += damage;



        if (totalDamageDealt >= 200) {
            unlock("★ I'll do it myself. (Deal 200 total damage without help from power stones)");
        }
    }

    public void onCombatantDefeated(Combatant combatant) {

        if (combatant.isStunned()) {
            unlock("★ Finish Him (Defeat an enemy while stunned)");
        }
    }

    public void onGameEnd(int roundNumber, boolean win) {

        if (roundNumber <= 5 && win) {
            unlock("★ Speedrunner (Win within 5 rounds)");
        }
    }

    private void unlock(String achievement) {
        if(!unlockedAchivements.contains(achievement)){
            unlockedAchivements.add(achievement);
        }

    }

    @Override
    public ArrayList<String> getUnlockedAchievements(){
        return unlockedAchivements;
    }
}
