package action;

import combatant.Combatant;
import effect.DefenseBoostEffect;
import ui.GameCLI;

import java.util.ArrayList;

public class Defend extends Action {

    public Defend(){
        super("Defend");
        requiresTarget = false;
        aoe = false;
    }

    @Override
    public void execute(Combatant actor, ArrayList<Combatant> targets, GameCLI ui) {

        DefenseBoostEffect db = new DefenseBoostEffect(2, 10);
        actor.addStatus(db);
        db.onApply(actor);
        System.out.println(actor.getLabel() + " uses Defend (Total Defense: " + actor.getDefense() + ")");


    }
}