package combatant;

import action.Action;
import strategy.EnemyActionStrategy;

public class Wolf extends Enemy{
    public Wolf(EnemyActionStrategy strategy) {
        super("Wolf", 40, 45, 5, 35, strategy);
    }

    @Override
    public Action getSpecialSkill() {
        return null;
    }
}
