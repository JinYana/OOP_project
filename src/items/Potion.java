package items;

import combatant.Combatant;
import combatant.Player;

import java.util.ArrayList;

public class Potion extends Item {

    int heal = 100;
    static int quanity = 0;

    public Potion() {

        name = "Potion";
        quanity++;
    }

    @Override
    public void use(Combatant actor, ArrayList<Combatant> targets) {
        actor.heal(heal);
        quanity --;
        System.out.println(actor.getLabel() + " uses " + this.name);

    }
}
