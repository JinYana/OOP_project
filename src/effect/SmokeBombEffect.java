package effect;

import combatant.Combatant;

public class SmokeBombEffect extends StatusEffect {

    public SmokeBombEffect(int duration) {

        super(duration);
        name = "SMOKE BOMB";
    }

    @Override
    public void onApply(Combatant target) {

    }

    @Override
    public void onRemove(Combatant target) {

    }


}