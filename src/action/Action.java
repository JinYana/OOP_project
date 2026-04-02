package action;

import combatant.Combatant;

public abstract class Action {
    protected boolean requiresTarget;
    protected boolean aoe;
    abstract public void execute(Combatant actor, Combatant target);

    public boolean isRequiresTarget(){
        return requiresTarget;
    }

    public boolean isAOE(){
        return aoe;
    }


}