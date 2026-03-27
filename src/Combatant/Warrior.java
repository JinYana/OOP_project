package Combatant;

public class Warrior extends Player{
	public Warrior() {
		super("Warrior", 260, 40, 20, 30, 3);
	}
	
	@Override
	public Action getSpecialSkill() {
		return new ShieldBash();
	}
	
	public String getSpecialSkillName() {
		return "Shield Bash";
	}
}