package combatant;

import action.Action;
import strategy.EnemyActionStrategy;

public class Goblin extends Enemy{
    public Goblin(EnemyActionStrategy strategy) {

        super("Goblin",55, 35, 15, 25, strategy);
    }

    @Override
    public Action getSpecialSkill() {
        return null;
    }
}
