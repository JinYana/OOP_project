package action;

import combatant.Combatant;

public class ShieldBash extends Action {

    ShieldBash(){
        requiresTarget = true;
        aoe = false;
    }

    @Override
    public void execute(Combatant actor, Combatant target) {
        target.takeDamage(actor.getAttack() / 2);
        System.out.println(actor.getLabel() + " uses Shield Bash on " + target.getLabel());
        actor.decrementCoolDown();

    }
}