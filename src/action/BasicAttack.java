package action;

import combatant.Combatant;


import java.util.ArrayList;

public class BasicAttack extends Action {

    public BasicAttack(){
        super("Basic Attack");
        requiresTarget = true;
        aoe = false;
    }

    @Override
    public void execute(Combatant actor, ArrayList<Combatant> targets) {

        Combatant target = targets.get(0);


        if(!target.isSmokeBombActive()){
            String damage = target.takeDamage(actor.getAttack());
            System.out.println(actor.getLabel() + " uses Basic Attack on " + target.getLabel() + damage );
        }
        else{
            System.out.println(actor.getLabel() + " uses Basic Attack on " + target.getLabel() + " (Damage: 0, SmokeBomb active)" );

        }



    }
}