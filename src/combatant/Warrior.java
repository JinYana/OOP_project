package combatant;

import action.Action;
import action.ShieldBash;

public class Warrior extends Player {


    public Warrior() {
        //max hp, attack, defense, speed
        super("Warrior", 260, 260, 40, 20);
    }

    @Override
    public Action getSpecialSkill() {
        return new ShieldBash();
    }
}
