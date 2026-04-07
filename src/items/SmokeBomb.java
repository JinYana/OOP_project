package items;
import combatant.Combatant;
import effect.SmokeBombEffect;
import ui.GameCLI;

import java.util.ArrayList;

public class SmokeBomb extends Item {

    static int quanity = 0;

    public SmokeBomb() {
        name = "Smoke Bomb";
        quanity++;

    }

    @Override
    public void use(Combatant actor, ArrayList<Combatant> targets, GameCLI ui) {
        actor.addStatus(new SmokeBombEffect(2));
        quanity--;

    }
}
