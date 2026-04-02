package effect;

import combatant.Combatant;

public class StunEffect extends StatusEffect {

    public StunEffect(int duration) {
        super(duration);
        name = "Stun";
    }

    @Override
    public void effect(Combatant target) {
        System.out.println(target.getLabel() + " is stunned and cannot act!");
    }
}