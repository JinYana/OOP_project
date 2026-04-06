package combatant;

import java.util.ArrayList;
import java.util.List;

import action.Action;
import effect.SmokeBombEffect;
import effect.StatusEffect;
import effect.StunEffect;
import items.Item;
import items.Potion;
import ui.GameCLI;

public abstract class Combatant {
    private String name;
    private int hp, maxhp, attack, defense, speed;
    private ArrayList<StatusEffect> statusEffects;
    private int skillCooldown = 0;
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

    //--------------Getter methods--------------
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



    public ArrayList<StatusEffect> getStatusEffects(){
        return statusEffects;
    }
    //----------End of getter methods------------


    //--------------Setter methods----------------
    public void setAttack(int a){
        this.attack = a;
    }

    public void setDefense(int d){
        this.defense = d;
    }
    //-----------End of setter methods--------------






    //Abstract methods
    public abstract Action chooseAction(GameCLI ui, List<Combatant> enemies, Combatant targets);

    public abstract Action getSpecialSkill();
    //End of abstract methods


    //------------Cooldown methods-------------------------
    public int getSkillCooldown() {
        return skillCooldown;
    }

    public void applyCooldown() {
        this.skillCooldown = 3;
    }

    public boolean isSkillAvailable() {
        return this.getSkillCooldown() <= 0;
    }

    public void decrementCoolDown(){
        if (this.getSkillCooldown() > 0) {
            skillCooldown--;
        }
    }




    //-------------End of cooldown methods----------------------

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

    public String takeDamage(int damage) {
        int tempDmg = damage;
	    damage = Math.max(0, damage - this.defense);
	    this.hp = Math.max(0, this.hp - damage);
        return "(Damage: " + tempDmg + " - " + defense + " = " + (tempDmg - this.defense) + ")";
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
            e.reduceDuration();

            if (e.isExpired()) {
                expired.add(e);
                e.onRemove(this);
            }
        }

        statusEffects.removeAll(expired);
    }

    public boolean isSmokeBombActive() {
        for (StatusEffect e : this.statusEffects) {
            if(e instanceof SmokeBombEffect){
                return true;
            }
        }
        return false;
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

    public int stunDuration() {
        int largestStun = 0;
        for(StatusEffect s : statusEffects){
            if(s instanceof StunEffect){
                if(s.getDuration() > largestStun){
                    largestStun = s.getDuration();
                }
            }
        }

        return largestStun;


    }

    public boolean isPlayer() {
        return isPlayer;
    }


}
