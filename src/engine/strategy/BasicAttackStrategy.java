package engine.strategy;

import action.Action;
import action.BasicAttack;


public class BasicAttackStrategy implements EnemyActionStrategy {


    @Override
    public Action chooseAction() {
        return new BasicAttack();
    }
}
