package Combatant;

public abstract class Enemy extends Combatant {
	private EnemyActionStrategy actionStrategy;
	
	public Enemy(String name, int hp, int maxhp, int attack, int defense, int speed,
			EnemyActionStrategy strategy) {
		super(name, hp, maxhp, attack, defense, speed);
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