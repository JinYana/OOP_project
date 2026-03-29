package level;

import combatant.Player;
import combatant.Warrior;
import combatant.Wizzard;

import java.util.Objects;

public class GameSetup {

    public static Player buildPlayer(String choice){
        if(Objects.equals(choice, "warrior")){
            return new Warrior();

        }
        else{
            return new Wizzard();
        }


    }


    public static Level buildLevel(){
            return new Level();
    }
}
