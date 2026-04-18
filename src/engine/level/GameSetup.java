package engine.level;

import combatant.*;
import items.Item;
import items.Potion;
import items.PowerStone;
import items.SmokeBomb;
import engine.strategy.BasicAttackStrategy;
import engine.strategy.EnemyActionStrategy;
import ui.GameCLI;

import java.util.ArrayList;

public class GameSetup {



    public enum PlayerClass{
        WARRIOR,
        WIZARD
    }

    public enum ItemChoice{
        POTION,
        POWERSTONE,
        SMOKEBOMB
    }

    private EnemyActionStrategy wolfStrat = new BasicAttackStrategy();
    private EnemyActionStrategy goblinStrat = new BasicAttackStrategy();

    public Combatant buildPlayer(GameCLI ui){
        GameSetup.PlayerClass chosenclass= ui.promptPlayerClass();


        Player p = switch (chosenclass){
            case WARRIOR ->  new Warrior();
            case WIZARD -> new Wizard();
        };

        // choose 2 items
        for(int i = 0; i < 2; i++){
            GameSetup.ItemChoice chosenitem = ui.promptItemChoice(i + 1);
            Item item = switch (chosenitem){
                case POTION -> new Potion();
                case SMOKEBOMB -> new SmokeBomb();
                case POWERSTONE -> new PowerStone();
            };

            p.addItem(item);

        }

        return  p;



    }


    public  Level buildLevel(GameCLI ui){

        Level.Difficulty diff = ui.promptDifficulty();

        ArrayList<Combatant> enemyList;
        ArrayList<Combatant> backupenemyList;
        return switch (diff){
                case EASY:
                    enemyList = new ArrayList<>();
                    //initial: 3 goblins, no backup
                    enemyList.add(new Goblin(goblinStrat));
                    enemyList.add(new Goblin(goblinStrat));
                    enemyList.add(new Goblin(goblinStrat));
                    yield new Level(enemyList, null, false, "Easy");

                case MEDIUM:
                    enemyList = new ArrayList<>();
                    backupenemyList = new ArrayList<>();

                    //initial: 1 wolf 1 goblin
                    enemyList.add(new Goblin(goblinStrat));
                    enemyList.add(new Wolf(wolfStrat));

                    //backup: 2 wolf
                    backupenemyList.add(new Wolf(wolfStrat));
                    backupenemyList.add(new Wolf(wolfStrat));

                    yield new Level(enemyList, backupenemyList, false, "Medium");

                case HARD:
                    enemyList = new ArrayList<>();
                    backupenemyList = new ArrayList<>();

                    //initial: 2 goblins
                    enemyList.add(new Goblin(goblinStrat));
                    enemyList.add(new Goblin(goblinStrat));

                    //backup: 2 wolf, 1 goblin
                    backupenemyList.add(new Wolf(wolfStrat));
                    backupenemyList.add(new Wolf(wolfStrat));
                    backupenemyList.add(new Goblin(goblinStrat));

                    yield new Level(enemyList, backupenemyList, false, "Hard");


               default: //default engine.level is medium
                    enemyList = new ArrayList<>();
                    backupenemyList = new ArrayList<>();

                    //initial: 1 wolf 1 goblin
                    enemyList.add(new Goblin(goblinStrat));
                    enemyList.add(new Wolf(wolfStrat));

                    //backup: 2 wolf
                    backupenemyList.add(new Wolf(wolfStrat));
                    backupenemyList.add(new Wolf(wolfStrat));

                    yield new Level(enemyList, backupenemyList, false, "Medium");

        };
    }
}
