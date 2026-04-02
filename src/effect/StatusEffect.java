package effect;

import combatant.Combatant;

public abstract class StatusEffect {
    protected String name;
    protected int duration;

    public String getName() {
        return name;
    }

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