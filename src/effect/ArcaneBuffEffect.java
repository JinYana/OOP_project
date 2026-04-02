package effect;

import combatant.Combatant;

public class ArcaneBuffEffect extends StatusEffect {
    private int boost;

    public ArcaneBuffEffect(int duration, int boost) {
        super(duration);
        this.boost = boost;
        name = "Arcane Buff";
    }

    @Override
    public void effect(Combatant target) {
        System.out.println(target.getLabel() + " gains +" + boost + " magic power!");
    }
}