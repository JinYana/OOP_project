package engine.observers;

import combatant.Combatant;

import java.util.ArrayList;

abstract public class BattleEventListener {
    abstract public void onDamageDealt(int damage);
    abstract public void onCombatantDefeated(Combatant combatant);
    abstract public void onGameEnd(int roundNumber, boolean win);
    public ArrayList<String> getUnlockedAchievements(){
        return null;
    };

}
