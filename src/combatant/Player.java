package combatant;

import action.Action;
import engine.BattleEngine;
import items.Item;
import ui.GameCLI;

import java.util.ArrayList;
import java.util.List;

public abstract class Player extends Combatant {
	private ArrayList<Item> inventory;

	
	public Player(String name, int maxhp, int attack, int defense, int speed) {
		super(name, maxhp, attack, defense, speed);
		this.inventory = new ArrayList<>();
		isPlayer = true;

	}

	@Override
	public void addItem(Item i) {
		this.inventory.add(i);
	}

	@Override
	public ArrayList<Item> getInventory(){
		return inventory;
	}

	@Override
	public Item removeItem(int i) {
		if (i >= 0 && i < this.inventory.size()) {
			Item item = this.inventory.remove(i);
			return item;
		}
		return null;

	}




	public Action chooseAction(GameCLI ui, List<Combatant> enemies, Combatant targets){
		return ui.promptPlayerAction(this, enemies);
	}
	//End of abstract method implementations



	

}