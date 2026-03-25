package strategy;

import domain.combatant.Combatant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Sorts combatants by speed descending.
 * Equal speed: order is stable (preserves original list order, enemies before players
 * when they share the same speed value, matching the spec examples).
 * SRP: only responsible for ordering logic.
 */
public class SpeedBasedTurnOrder implements TurnOrderStrategy {

    @Override
    public List<Combatant> determineTurnOrder(List<Combatant> combatants) {
        List<Combatant> ordered = new ArrayList<>(combatants);
        // Stable sort: equal-speed combatants keep their original relative order.
        ordered.sort(Comparator.comparingInt(Combatant::getSpeed).reversed());
        return ordered;
    }
}
