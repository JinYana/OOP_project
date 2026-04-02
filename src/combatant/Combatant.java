package combatant;

import java.util.ArrayList;
import java.util.List;

import action.Action;
import effect.StatusEffect;
import effect.StunEffect;
import engine.BattleEngine;
import items.Item;
import items.Potion;
import ui.GameCLI;

public abstract class Combatant {
    private String name;
    private int hp, maxhp, attack, defense, speed;
    private ArrayList<StatusEffect> statusEffects;
    private int skillCooldown = 3;
    protected boolean isPlayer;
    
    public Combatant(String name, int maxhp, int attack, int defense, int speed) {
        this.name = name;
        this.hp = maxhp;
        this.maxhp = maxhp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.statusEffects = new ArrayList<>();
    }

    //Getter methods
    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxhp() {
        return maxhp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public ArrayList<String> getStatusEffects() {
        ArrayList<String> stringstatus = new ArrayList<>();
        for(StatusEffect s : statusEffects){
            stringstatus.add(s.getName());

        }
        return  stringstatus;
    }
    // End of getter methods

    //Abstract methods
    public abstract Action chooseAction(GameCLI ui, List<Combatant> enemies, Combatant targets);

    public abstract Action getSpecialSkill();
    //End of abstract methods


    //Cooldown methods
    public int getSkillCooldown() {
        return skillCooldown;
    }

    public boolean isSkillAvailable() {
        return this.getSkillCooldown() <= 0;
    }

    public void decrementCoolDown(){}

    public void updateSkillCooldown(){
        skillCooldown--;
    }
    //End of cooldown methods

    //Inventory functions
    public ArrayList<Item> getInventory(){
        return new ArrayList<>();
    }

    public void addItem(Item i) {
    }

    public Item removeItem(int i) {
        return new Potion();
    }
    //End of inventory functions

    public void takeDamage(int damage) {
	    damage = Math.max(0, damage - this.defense);
	    this.hp = Math.max(0, this.hp - damage);
	}

    
    public void heal(int amount) {
        this.hp += amount;
        this.hp = Math.min(this.hp, this.maxhp);
    }
    
    public void addStatus(StatusEffect effect) {
        statusEffects.add(effect);
    } 
    
    public void tickStatus() {
        ArrayList<StatusEffect> expired = new ArrayList<>();

        for (StatusEffect e : this.statusEffects) {
            e.effect(this);
            e.reduceDuration();

            if (e.isExpired()) {
                expired.add(e);
            }
        }

        statusEffects.removeAll(expired);
    }

    public boolean isSmokeBombActive() {
        return this.statusEffects.contains("Smoke Bomb");
    }
    
    public boolean isDefeated() {
        return hp <= 0;
    }
    
    public String getLabel() {
        return this.name + " (HP: " + this.hp + ")";
    }

    public boolean isStunned() {
        for(StatusEffect s : statusEffects){
            if(s instanceof StunEffect){
                return true;
            }
        }

        return false;


    }

    public boolean isPlayer() {
        return isPlayer;
    }


}
