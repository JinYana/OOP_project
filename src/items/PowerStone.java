package items;

import action.Action;
import combatant.Player;

public class PowerStone extends Item {

    static int quanity = 0;

    PowerStone(String n, Player p) {
        super(n, p);
    }

    @Override
    public void use() {
        Action skill = player.getSpecialSkill();


    }
}
