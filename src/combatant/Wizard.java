package combatant;

import action.Action;
import action.ArcaneBlast;

public class Wizard extends Player{

    public Wizard() {
        //max hp, attack, defense, speed
        super("Wizard", 200, 50, 10, 20);
    }

    @Override
    public Action getSpecialSkill() {
        return new ArcaneBlast();
    }
}
