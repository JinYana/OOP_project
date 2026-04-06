package action;

import combatant.Combatant;
import effect.ArcaneBuffEffect;
import effect.StunEffect;

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
    public void execute(Combatant actor, ArrayList<Combatant> targets) {
        this.PowerStoneExecute(actor, targets);
        actor.applyCooldown();

    }

    @Override
    public void PowerStoneExecute(Combatant actor, ArrayList<Combatant> targets) {
        Combatant target = targets.get(0);
        target.takeDamage(actor.getAttack());
        target.addStatus(new StunEffect(3));// duration of 3 to skip 2 turns to offset initial tick immediately after stunning enemy
        System.out.println(actor.getLabel() + " uses Shield Bash on " + target.getLabel());


    }
}