package action;

import combatant.Combatant;
import effect.SmokeBombEffect;
import effect.StatusEffect;

import java.util.ArrayList;

public class BasicAttack extends Action {

    public BasicAttack(){
        super("Basic Attack");
        requiresTarget = true;
        aoe = false;
    }

    @Override
    public void execute(Combatant actor, ArrayList<Combatant> targets) {
        boolean smokebombActive = false;
        Combatant target = targets.get(0);
        for (StatusEffect e : target.getStatusEffects()) {
            if(e instanceof SmokeBombEffect){
                smokebombActive = true;
                break;
            }
        }

        if(!smokebombActive){
            String damage = target.takeDamage(actor.getAttack());
            System.out.println(actor.getLabel() + " uses Basic Attack on " + target.getLabel() + damage );
        }
        else{
            System.out.println(actor.getLabel() + " uses Basic Attack on " + target.getLabel() + " (Damage: 0, SmokeBomb active)" );

        }



    }
}