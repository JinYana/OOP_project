package action;

import combatant.Combatant;
import items.Item;

import java.util.ArrayList;

public class ItemAction extends Action{
    Item item;

    public ItemAction(Item i){
        super("Use Item");
        item = i;
        requiresTarget = false;
        aoe = false;
    }
    @Override
    public void execute(Combatant actor, ArrayList<Combatant> targets) {

        item.use(actor, targets);
    }
}
