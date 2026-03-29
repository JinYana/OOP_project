package combatant;

import java.util.ArrayList;

public abstract class Combatant {
    private String name;
    private int hp, maxhp, attack, defense, speed;
    private ArrayList<StatusEffect> statusEffects;
    
    public Combatant( int maxhp, int attack, int defense, int speed) {
        this.hp = maxhp;
        this.maxhp = maxhp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.statusEffects = new ArrayList<>();
    }
    
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
        for (StatusEffect e: this.statusEffects) {
        	e.effect(this);
        }
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
}
