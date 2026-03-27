package Combatant;

public class Wizard extends Player {
	public Wizard() {
		super("Wizard", 200, 50, 10, 20, 3);
	}
	
	@Override
	public Action getSpecialSkill() {
		return new ArcaneBlast();
	}
	
	public String getSpecialSkillName() {
		return "Arcane Blast";
	}
}