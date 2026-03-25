package strategy;

import domain.action.Action;
import domain.action.BasicAttack;
import domain.combatant.Combatant;
import java.util.List;


public class BasicAttackStrategy implements EnemyActionStrategy {

    @Override
    public Action chooseAction(Combatant enemy, List<Combatant> targets) {
        return new BasicAttack();
    }
}
