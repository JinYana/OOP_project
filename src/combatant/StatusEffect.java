package combatant;

public abstract class StatusEffect {
    protected int duration;

    public StatusEffect(int duration) {
        this.duration = duration;
    }

    public abstract void effect(Combatant target);

    public void reduceDuration() {
        duration--;
    }

    public boolean isExpired() {
        return duration <= 0;
    }
}