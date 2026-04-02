package items;

import action.Action;
import combatant.Combatant;
import combatant.Player;

public class PowerStone extends Item {

    static int quanity = 0;

    public PowerStone() {

        name = "Power Stone";
    }

    @Override
    public void use(Combatant player) {
        Action skill = player.getSpecialSkill();


    }
}
