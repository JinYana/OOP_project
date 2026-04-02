package action;

import combatant.Combatant;

public class Defend implements Action {

    @Override
    public void execute(Combatant actor, Combatant target) {
        System.out.println(actor.getLabel() + " uses Defend!");
    }
}