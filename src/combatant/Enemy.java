package combatant;

import action.Action;
import java.util.List;

import engine.strategy.EnemyActionStrategy;
import ui.GameCLI;

public abstract class Enemy extends Combatant {
	private EnemyActionStrategy actionStrategy;

	public Enemy(String name, int maxhp, int attack, int defense, int speed,
			EnemyActionStrategy strategy) {
		super(name, maxhp, attack, defense, speed);
		actionStrategy = strategy;
		isPlayer = false;

	}




	public Action chooseAction(GameCLI ui, List<Combatant> enemies, Combatant targets) {
		return actionStrategy.chooseAction();
	}
	
	@Override
	public String getLabel() {
		return "[Enemy] " + super.getLabel();
	}
	
}