package action;

import combatant.Combatant;
import effect.ArcaneBuffEffect;
import ui.GameCLI;

import java.util.ArrayList;

public class ArcaneBlast extends Action {


    public ArcaneBlast(){
        super("Arcane Blast");
        requiresTarget = false;
        aoe = true;

    }

    @Override
    public int PowerStoneExecute(Combatant actor, ArrayList<Combatant> targets, GameCLI ui) {
        ArcaneBuffEffect ab = new ArcaneBuffEffect(-100, 10);//duration -100 to ensure it lasts to the end of the engine.level
        int totaldamage = 0;
        for(Combatant e: targets){
            String damage = e.takeDamage(actor.getAttack());
            totaldamage += actor.getAttack();
            System.out.println(actor.getLabel() + " uses Arcane Blast on " + e.getLabel() + damage );

            if(e.isDefeated()){

                System.out.println("Arcane Blast has killed " + e.getLabel() + " , Arcane Buff applied to " + actor.getLabel() + " (Attack: " + actor.getAttack() + " -> Attack: " + (actor.getAttack() + 10) + ")");
                ab.onApply(actor);
            }
        }

        return totaldamage;


    }

    @Override
    public int execute(Combatant actor, ArrayList<Combatant> targets, GameCLI ui) {

        actor.applyCooldown();
        return this.PowerStoneExecute(actor, targets, ui);


    }


}