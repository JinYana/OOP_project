package level;

import combatant.Enemy;

import java.util.ArrayList;
import java.util.List;

public class Level {



    private List<Enemy> initialSpawn = new ArrayList<>();
    private List<Enemy> backupSpawn = new ArrayList<>();
    private boolean backupTriggered;

    Level(List<Enemy> is, List<Enemy>bs, boolean bt){
        initialSpawn = is;
        backupSpawn = bs;
        backupTriggered = bt;


    }

    public static void easy(){

    }
    public static void medium(){

    }
    public static void hard(){

    }
}
