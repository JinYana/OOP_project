package items;
import combatant.Combatant;
import ui.GameCLI;

import java.util.ArrayList;

public class Potion extends Item {

    int heal = 100;
    static int quanity = 0;

    public Potion() {

        name = "Potion";
        quanity++;
    }

    @Override
    public void use(Combatant actor, ArrayList<Combatant> targets, GameCLI ui) {
        actor.heal(heal);
        quanity --;
        System.out.println(actor.getLabel() + " uses " + this.name);

    }
}
