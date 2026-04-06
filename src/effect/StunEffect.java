package effect;

import combatant.Combatant;

public class StunEffect extends StatusEffect {

    public StunEffect(int duration) {
        super(duration);
        name = "STUN";
    }

    @Override
    public void onApply(Combatant target) {

    }

    @Override
    public void onRemove(Combatant target) {

    }


}