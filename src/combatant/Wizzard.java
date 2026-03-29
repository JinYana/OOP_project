package combatant;

import action.Action;

public class Wizzard extends Player{

    public Wizzard() {
        //max hp, attack, defense, speed
        super(200, 50, 10, 20);
    }

    @Override
    public Action getSpecialSkill() {
        return null;
    }
}
