package strategy;

import domain.combatant.Combatant;
import java.util.List;

/**
 * Strategy interface for determining turn order each round.
 * OCP/DIP: BattleEngine depends on this abstraction, not on SpeedBasedTurnOrder.
 * Swap or add ordering algorithms without touching the engine.
 */
public interface TurnOrderStrategy {

    /**
     * Produces an ordered list of combatants for the current round.
     * The returned list is a new list — the original is not modified.
     *
     * @param combatants all living combatants in the battle
     * @return ordered list, first element acts first
     */
    List<Combatant> determineTurnOrder(List<Combatant> combatants);
}
