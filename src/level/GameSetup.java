package level;

import combatant.Player;
import combatant.Warrior;
import combatant.Wizard;

import java.util.Objects;

public class GameSetup {

    public static Player buildPlayer(String choice){
        if(Objects.equals(choice, "warrior")){
            return new Warrior();

        }
        else{
            return new Wizard();
        }


    }


    public static Level buildLevel(){
            return new Level();
    }
}
