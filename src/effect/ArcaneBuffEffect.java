package effect;

import combatant.Combatant;

public class ArcaneBuffEffect extends StatusEffect {
    private int boost;

    public ArcaneBuffEffect(int duration, int boost) {
        super(duration);
        this.boost = boost;
        name = "ARCANE BUFF";
    }


    @Override
    public void onApply(Combatant target) {
        target.setAttack(target.getAttack() + boost);

    }


    // Arcane Buff lasts till end of engine.level, no need to remove effect
    @Override
    public void onRemove(Combatant target) {

    }
}