package Combatant;

import java.util.ArrayList;

public abstract class Player extends Combatant {
	private ArrayList<Item> inventory;
	private int skillCooldown;
	
	public Player(String name, int hp, int maxhp, int attack, int defense, int speed, int coolDown) {
		super(name, hp, maxhp, attack, defense, speed);
		this.inventory = new ArrayList<>();
		this.skillCooldown = coolDown;
	}
	
	public void addItem(Item i) {
		this.inventory.add(i);
	}
	
	public Item removeItem(int i) {
		if (i < 0 || i >= this.inventory.size()) {
			return null;
		}
		
		Item item = this.inventory.remove(i);
		return item;
	}
	
	public void decrementCoolDown() {
		this.skillCooldown --;
		if (this.skillCooldown < 0) {
			this.skillCooldown = 0;
		}
	}
	
	public abstract Action getSpecialSkill();
	
	public boolean isSkillAvailable() {
		return this.skillCooldown <= 0;
	}
}