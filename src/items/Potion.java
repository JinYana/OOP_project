package items;

import combatant.Player;

public class Potion extends Item {

    int heal = 100;
    static int quanity = 0;

    Potion(String n, Player p) {
        super(n, p);
    }

    @Override
    public void use() {
        player.heal(heal);
        quanity --;

    }
}
