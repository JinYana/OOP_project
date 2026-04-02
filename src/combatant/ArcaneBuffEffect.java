package combatant;

public class ArcaneBuffEffect extends StatusEffect {
    private int boost;

    public ArcaneBuffEffect(int duration, int boost) {
        super(duration);
        this.boost = boost;
    }

    @Override
    public void effect(Combatant target) {
        System.out.println(target.getLabel() + " gains +" + boost + " magic power!");
    }
}