package combatant;

import strategy.EnemyActionStrategy;

public class Wolf extends Enemy{
    public Wolf(EnemyActionStrategy strategy) {
        super(40, 45, 5, 35, strategy);
    }
}
