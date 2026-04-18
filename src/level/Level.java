package level;

import combatant.Combatant;

import java.util.List;

public class Level {

    public enum Difficulty{
        EASY,
        MEDIUM,
        HARD
    }



    private List<Combatant> initialSpawn;
    private List<Combatant> backupSpawn;
    private boolean backupTriggered;
    private String name;


    public Level(List<Combatant> is, List<Combatant>bs, boolean bt, String n){
        initialSpawn = is;
        backupSpawn = bs;
        backupTriggered = bt;
        name = n;


    }
    public String getName(){
        return name;
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


}
