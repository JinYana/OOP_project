package action;

import combatant.Combatant;

public class ShieldBash extends Action {
    String name;

    public ShieldBash(){
        super("Shield Bash");
        name = "Shield Bash";
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