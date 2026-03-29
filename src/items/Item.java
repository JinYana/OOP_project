package items;

import combatant.Player;

abstract public class Item {

    private String name;
    protected Player player;

    Item(String n, Player p){
        name = n;
        player = p;
    }

    public String getName(){
        return name;
    }

    abstract public void use();

}
