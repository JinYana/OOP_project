package items;
import combatant.Combatant;
import ui.GameCLI;
import java.util.ArrayList;
public class PowerStone extends Item {

    static int quanity = 0;

    public PowerStone() {

        name = "Power Stone";
        quanity++;
    }

    @Override
    public void use(Combatant actor, ArrayList<Combatant> targets, GameCLI ui) {
        System.out.println(actor.getLabel() + " activates the Power Stone!");


        if(actor.getSpecialSkill().isRequiresTarget()){
            Combatant target = ui.promptTargetSelection(targets);
            ArrayList <Combatant> targetList = new ArrayList<>();
            targetList.add(target);
            actor.getSpecialSkill().PowerStoneExecute(actor, targetList, ui);
        }
        else{
            actor.getSpecialSkill().PowerStoneExecute(actor, targets, ui);
        }

    }
}
