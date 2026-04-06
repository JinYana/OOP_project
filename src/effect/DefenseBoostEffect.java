package effect;

import combatant.Combatant;

public class DefenseBoostEffect extends StatusEffect {
    private int boost;

    public DefenseBoostEffect(int duration, int boost) {
        super(duration);
        this.boost = boost;
        name = "DEFENSE BOOST";
    }



    @Override
    public void onApply(Combatant target) {
        target.setDefense(target.getDefense() + boost);
    }

    @Override
    public void onRemove(Combatant target) {
        target.setDefense(target.getDefense() - boost);

    }
}