package items;
import combatant.Combatant;
import ui.GameCLI;

import java.util.ArrayList;

abstract public class Item {

    protected String name;






    public String getName(){
        return name;
    }

    abstract public void use(Combatant actor, ArrayList<Combatant> targets, GameCLI ui);

}
