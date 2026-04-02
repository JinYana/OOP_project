package action;

import combatant.Combatant;

public class ArcaneBlast extends Action {

    ArcaneBlast(){
        requiresTarget = true;
        aoe = true;
    }

    @Override
    public void execute(Combatant actor, Combatant target) {
        target.takeDamage(actor.getAttack() + 5);
        System.out.println(actor.getLabel() + " uses Arcane Blast on " + target.getLabel());
    }
}