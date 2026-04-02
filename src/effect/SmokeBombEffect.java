package effect;

import combatant.Combatant;

public class SmokeBombEffect extends StatusEffect {

    public SmokeBombEffect(int duration) {

        super(duration);
        name = "Smoke Bomb";
    }

    @Override
    public void effect(Combatant target) {
        System.out.println(target.getLabel() + " is hidden in smoke!");
    }
}