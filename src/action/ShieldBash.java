package action;

import combatant.Combatant;
import effect.StunEffect;
import ui.GameCLI;

import java.util.ArrayList;

public class ShieldBash extends Action {
    String name;

    public ShieldBash(){
        super("Shield Bash");
        name = "Shield Bash";
        requiresTarget = true;
        aoe = false;
    }

    @Override
    public int execute(Combatant actor, ArrayList<Combatant> targets, GameCLI ui) {
        actor.applyCooldown();
        return this.PowerStoneExecute(actor, targets, ui);


    }

    @Override
    public int PowerStoneExecute(Combatant actor, ArrayList<Combatant> targets, GameCLI ui) {
        Combatant target = targets.get(0);
        target.takeDamage(actor.getAttack());
        int totaldamage = actor.getAttack();
        target.addStatus(new StunEffect(3));// duration of 3 to skip 2 turns to offset initial tick immediately after stunning enemy
        System.out.println(actor.getLabel() + " uses Shield Bash on " + target.getLabel());
        return totaldamage;


    }
}