package combatant;

import action.Action;

public class Warrior extends Player {


    public Warrior() {
        //max hp, attack, defense, speed
        super(260, 260, 40, 20);
    }

    @Override
    public Action getSpecialSkill() {
        return null;
    }
}
