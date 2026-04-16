package strategy;

import action.Action;
import action.BasicAttack;
import combatant.Combatant;
import java.util.List;


public class BasicAttackStrategy implements EnemyActionStrategy {


    @Override
    public Action chooseAction() {
        return new BasicAttack();
    }
}
