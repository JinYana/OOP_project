package combatant;

import java.util.ArrayList;

import effect.StatusEffect;
import effect.StunEffect;

public abstract class Combatant {
    private String name;
    private int hp, maxhp, attack, defense, speed;
    private ArrayList<StatusEffect> statusEffects;
    private int skillCooldown = 3;
    
    public Combatant( int maxhp, int attack, int defense, int speed) {
        this.hp = maxhp;
        this.maxhp = maxhp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.statusEffects = new ArrayList<>();
    }

    public abstract void decrementCoolDown();

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
    
    public int getAttack() {
        return attack;
    }

    public int getSkillCooldown() {
        return skillCooldown;
    }

    public void updateSkillCooldown(){
        skillCooldown --;
    }

    public boolean isStunned() {
        for(StatusEffect s : statusEffects){
            if(s instanceof StunEffect){
                return true;
            }
        }

        return false;


    }
}
