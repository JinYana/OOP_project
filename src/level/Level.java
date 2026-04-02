package level;

import combatant.Combatant;
import combatant.Enemy;
import combatant.Goblin;
import combatant.Wolf;

import java.util.ArrayList;
import java.util.List;

public class Level {



    private List<Combatant> initialSpawn = new ArrayList<>();
    private List<Combatant> backupSpawn = new ArrayList<>();
    private boolean backupTriggered;

    Level(List<Combatant> is, List<Combatant>bs, boolean bt){
        initialSpawn = is;
        backupSpawn = bs;
        backupTriggered = bt;


    }

    public boolean hasBackup(){
        if(this.backupSpawn != null){
            return true;
        }
        else{
            return false;
        }
    }

    public List<Combatant> getBackupSpawn(){
        backupTriggered = true;
        return backupSpawn;
    }

    public boolean isBackupTriggered(){
        return backupTriggered;
    }


    public List<Combatant> getInitialSpawn(){
        return initialSpawn;
    }

    public static Level easy(){
        List<Enemy> enemyList = new ArrayList<>();

        //initial: 3 goblins, no backup
        enemyList.add(new Goblin());
        enemyList.add(new Goblin());
        enemyList.add(new Goblin());
        return new Level(enemyList, null, false);



    }
    public static Level medium(){
        List<Enemy> enemyList = new ArrayList<>();
        List<Enemy> backupenemyList = new ArrayList<>();

        //initial: 1 wolf 1 goblin
        enemyList.add(new Goblin());
        enemyList.add(new Wolf());

        //backup: 2 wolf
        backupenemyList.add(new Wolf());
        backupenemyList.add(new Wolf());

        return new Level(enemyList, backupenemyList, false);

    }
    public static Level hard(){
        List<Enemy> enemyList = new ArrayList<>();
        List<Enemy> backupenemyList = new ArrayList<>();

        //initial: 2 goblins
        enemyList.add(new Goblin());
        enemyList.add(new Goblin());

        //backup: 2 wolf, 1 goblin
        backupenemyList.add(new Wolf());
        backupenemyList.add(new Wolf());
        backupenemyList.add(new Goblin());

        return new Level(enemyList, backupenemyList, false);
    }
}
