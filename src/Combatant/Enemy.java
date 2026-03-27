package Combatant;

public abstract class Enemy extends Combatant {
	private EnemyActionStrategy actionStrategy;
	
	public Enemy(String name, int hp, int attack, int defense, int speed,
			EnemyActionStrategy strategy) {
		super(name, hp, attack, defense, speed);
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