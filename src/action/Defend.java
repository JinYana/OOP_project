package action;

import combatant.Combatant;

public class Defend extends Action {

    Defend(){
        requiresTarget = false;
        aoe = false;
    }

    @Override
    public void execute(Combatant actor, Combatant target) {
        System.out.println(actor.getLabel() + " uses Defend!");
    }
}