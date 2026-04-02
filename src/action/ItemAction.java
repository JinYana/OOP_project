package action;

import combatant.Combatant;
import items.Item;

public class ItemAction extends Action{
    Item item;

    public ItemAction(Item i){
        super("Use Item");
        item = i;
    }
    @Override
    public void execute(Combatant actor, Combatant target) {
        item.use(actor);
    }
}
