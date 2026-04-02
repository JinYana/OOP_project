package items;

import combatant.Combatant;
import combatant.Player;

public class Potion extends Item {

    int heal = 100;
    static int quanity = 0;

    public Potion() {

        name = "Potion";
    }

    @Override
    public void use(Combatant player) {
        player.heal(heal);
        quanity --;

    }
}
