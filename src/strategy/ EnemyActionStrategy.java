package strategy;

import domain.action.Action;
import domain.combatant.Combatant;
import java.util.List;

/**
 * Strategy interface for enemy decision-making.
 * OCP: future AI strategies (random, defensive, etc.) implement this interface
 *      without modifying Enemy or BattleEngine.
 * DIP: Enemy holds this interface, not a concrete strategy.
 */
public interface EnemyActionStrategy {

    /**
     * Selects and returns the action the enemy will perform this turn.
     *
     * @param enemy   the enemy acting
     * @param targets the living player combatants available to attack
     * @return the chosen Action
     */
    Action chooseAction(Combatant enemy, List<Combatant> targets);
}
