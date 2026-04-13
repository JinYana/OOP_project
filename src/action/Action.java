package action;

import combatant.Combatant;
import engine.BattleEngine;
import ui.GameCLI;

import java.util.ArrayList;

public abstract class Action {
    protected boolean requiresTarget;
    protected boolean aoe;
    protected String name;

    public Action(String name){
        this.name = name;
    }

    abstract public int execute(Combatant actor, ArrayList<Combatant> target, GameCLI ui);

    public boolean isRequiresTarget(){
        return requiresTarget;
    }



    public String getName(){
        return name;
    }

    // default implementation empty, only for arcane blast and shield bash
    public int PowerStoneExecute(Combatant actor, ArrayList<Combatant> targets, GameCLI ui){
        return 0;
    }


}