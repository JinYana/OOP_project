package items;

import combatant.Combatant;
import combatant.Player;
import effect.SmokeBombEffect;

public class SmokeBomb extends Item {

    static int quanity = 0;

    public SmokeBomb() {
        name = "Smoke Bomb";

    }

    @Override
    public void use(Combatant player) {
        player.addStatus(new SmokeBombEffect(2));

    }
}
