package effect;

import combatant.Combatant;

public class DefenseBoostEffect extends StatusEffect {
    private int boost;

    public DefenseBoostEffect(int duration, int boost) {
        super(duration);
        this.boost = boost;
    }

    @Override
    public void effect(Combatant target) {
        System.out.println(target.getLabel() + " gains +" + boost + " defense!");
    }
}