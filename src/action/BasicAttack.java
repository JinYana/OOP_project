package action;

import combatant.Combatant;

public class BasicAttack extends Action {

    public BasicAttack(){
        super("Basic Attack");
        requiresTarget = true;
        aoe = false;
    }

    @Override
    public void execute(Combatant actor, Combatant target) {
        target.takeDamage(actor.getAttack());
        System.out.println(actor.getLabel() + " uses Basic Attack on " + target.getLabel());
    }
}