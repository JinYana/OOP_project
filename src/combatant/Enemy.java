package combatant;

import action.Action;
import java.util.List;

import strategy.EnemyActionStrategy;

public abstract class Enemy extends Combatant {
	private EnemyActionStrategy actionStrategy;
	
	public Enemy(int maxhp, int attack, int defense, int speed,
			EnemyActionStrategy strategy) {
		super(maxhp, attack, defense, speed);
		this.actionStrategy = strategy;
	}
	
	public Action chooseAction(List<Combatant> targets) {
		return actionStrategy.chooseAction(this, targets);
	}
	
	@Override
	public String getLabel() {
		return "[Enemy] " + super.getLabel();
	}
	
}