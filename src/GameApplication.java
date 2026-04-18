import combatant.Combatant;
import engine.BattleEngine;
import engine.level.GameSetup;
import engine.level.Level;
import engine.strategy.SpeedBasedTurnOrder;
import engine.strategy.TurnOrderStrategy;
import ui.GameCLI;

public class GameApplication {

    public static void main(String[] args) {
        GameCLI ui = new GameCLI();
        GameSetup gameSetup = new GameSetup();
        TurnOrderStrategy turnOrderStrategy = new SpeedBasedTurnOrder();

        ui.displayLoadingScreen();
        Combatant player = gameSetup.buildPlayer(ui);
        Level level = gameSetup.buildLevel(ui);

        BattleEngine battleEngine = new BattleEngine(player, level, turnOrderStrategy, ui);
        battleEngine.run();


    }
}
