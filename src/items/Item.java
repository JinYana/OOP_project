package items;

import combatant.Combatant;
import combatant.Player;

abstract public class Item {

    protected String name;






    public String getName(){
        return name;
    }

    abstract public void use(Combatant player);

}
