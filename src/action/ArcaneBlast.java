package action;

import combatant.Combatant;
import effect.ArcaneBuffEffect;

import java.util.ArrayList;

public class ArcaneBlast extends Action {


    public ArcaneBlast(){
        super("Arcane Blast");
        requiresTarget = false;
        aoe = true;

    }

    @Override
    public void PowerStoneExecute(Combatant actor, ArrayList<Combatant> targets) {
        ArcaneBuffEffect ab = new ArcaneBuffEffect(-100, 10);//duration -100 to ensure it lasts to the end of the level
        for(Combatant e: targets){
            String damage = e.takeDamage(actor.getAttack());
            System.out.println(actor.getLabel() + " uses Arcane Blast on " + e.getLabel() + damage );

            if(e.isDefeated()){

                System.out.println("Arcane Blast has killed " + e.getLabel() + " , Arcane Buff applied to " + actor.getLabel() + " (Attack: " + actor.getAttack() + " -> Attack: " + (actor.getAttack() + 10) + ")");
                ab.onApply(actor);
            }
        }


    }

    @Override
    public void execute(Combatant actor, ArrayList<Combatant> targets) {

        this.PowerStoneExecute(actor, targets);
        actor.applyCooldown();

    }


}