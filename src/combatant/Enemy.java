package combatant;

import action.Action;
import strategy.EnemyActionStrategy;

public abstract class Enemy extends Combatant {
	private EnemyActionStrategy actionStrategy;
	
	public Enemy(int maxhp, int attack, int defense, int speed,
			EnemyActionStrategy strategy) {
		super(maxhp, attack, defense, speed);
		this.actionStrategy = strategy;
	}
	
	public Action chooseAction(Combatant target) {
		return actionStrategy.chooseAction(this, target);
	}
	
	@Override
	public String getLabel() {
		return "[Enemy] " + super.getLabel();
	}
	
}