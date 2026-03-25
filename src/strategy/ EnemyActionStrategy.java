package strategy;

import domain.action.Action;
import domain.combatant.Combatant;
import java.util.List;


public interface EnemyActionStrategy {
    Action chooseAction(Combatant enemy, List<Combatant> targets);
}
