package action;

import combatant.Combatant;
import items.Item;
import ui.GameCLI;

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
    public int execute(Combatant actor, ArrayList<Combatant> targets, GameCLI ui) {

        item.use(actor, targets, ui);
        return 0;
    }
}
