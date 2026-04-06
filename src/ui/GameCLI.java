package ui;

import action.*;
import combatant.Combatant;
import effect.StatusEffect;
import items.Item;
import level.GameSetup;
import level.Level;
import java.util.List;
import java.util.Scanner;

public class GameCLI {
	
	private final Scanner scanner = new Scanner(System.in);
	
	public void displayLoadingScreen() {
        printBanner();
        System.out.println("  ╔══════════════════════════════════════════════╗");
        System.out.println("  ║       TURN-BASED COMBAT ARENA                ║");
        System.out.println("  ╚══════════════════════════════════════════════╝");
        System.out.println();

        System.out.println("── PLAYER CLASSES ────────────────────────────────");
        System.out.println("  [1] Warrior  │ HP: 260 │ ATK: 40 │ DEF: 20 │ SPD: 30");
        System.out.println("               │ Special: Shield Bash");
        System.out.println("               │   → Deals BasicAttack dmg + stuns target 2 turns");
        System.out.println();
        System.out.println("  [2] Wizard   │ HP: 200 │ ATK: 50 │ DEF: 10 │ SPD: 20");
        System.out.println("               │ Special: Arcane Blast");
        System.out.println("               │   → AoE BasicAttack dmg; +10 ATK per kill");
        System.out.println();

        System.out.println("── ENEMIES ───────────────────────────────────────");
        System.out.println("  Goblin  │ HP: 55 │ ATK: 35 │ DEF: 15 │ SPD: 25");
        System.out.println("  Wolf    │ HP: 40 │ ATK: 45 │ DEF:  5 │ SPD: 35");
        System.out.println();

        System.out.println("── DIFFICULTY LEVELS ─────────────────────────────");
        System.out.println("  [1] Easy   │ Initial: 3 Goblins");
        System.out.println("  [2] Medium │ Initial: 1 Goblin + 1 Wolf  │ Backup: 2 Wolves");
        System.out.println("  [3] Hard   │ Initial: 2 Goblins           │ Backup: 1 Goblin + 2 Wolves");
        System.out.println();
    }
	
	public GameSetup.PlayerClass promptPlayerClass() {
        System.out.println("Select your class:");
        System.out.println("  [1] Warrior");
        System.out.println("  [2] Wizard");
        int choice = readInt(1, 2);
        return choice == 1 ? GameSetup.PlayerClass.WARRIOR : GameSetup.PlayerClass.WIZARD;
    }
	
	public GameSetup.ItemChoice promptItemChoice(int slotNumber) {
        System.out.println("\nSelect item " + slotNumber + " (duplicates allowed):");
        System.out.println("  [1] Potion        – Heal 100 HP");
        System.out.println("  [2] Power Stone   – Free extra use of special skill");
        System.out.println("  [3] Smoke Bomb    – Enemy attacks deal 0 damage for 2 turns");
        int choice = readInt(1, 3);
        return switch (choice) {
            case 1  -> GameSetup.ItemChoice.POTION;
            case 2  -> GameSetup.ItemChoice.POWERSTONE;
            default -> GameSetup.ItemChoice.SMOKEBOMB;
        };
    }
	
	public Level.Difficulty promptDifficulty() {
        System.out.println("\nSelect difficulty:");
        System.out.println("  [1] Easy");
        System.out.println("  [2] Medium");
        System.out.println("  [3] Hard");
        int choice = readInt(1, 3);
        return switch (choice) {
            case 1  -> Level.Difficulty.EASY;
            case 2  -> Level.Difficulty.MEDIUM;
            default -> Level.Difficulty.HARD;
        };
    }
	
	
	
	public void displayBattleStart(Combatant player, List<Combatant> enemies, Level level) {
        System.out.println();
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("  BATTLE START — " + level.getName());
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("  " + player.getName());
        System.out.println("  Items: " + formatItems(player));
        System.out.println("  Enemies:");
        for (Combatant e : enemies) System.out.println("    • " + e.getName());
        System.out.println("══════════════════════════════════════════════════");
    }
	
	public void displayRoundHeader(int roundNumber) {
        System.out.println();
        System.out.println("┌─────────────────────────────────────────────────");
        System.out.println("│  ROUND " + roundNumber);
        System.out.println("└─────────────────────────────────────────────────");
    }
	
	public void displayRoundSummary(int roundNumber, Combatant player, List<Combatant> livingEnemies) {
        System.out.println();
        System.out.print("  End of Round " + roundNumber + ": "
                + player.getName() + " HP: " + player.getHp() + "/" + player.getMaxhp());
        for (Combatant e : livingEnemies) {
            System.out.print(" | " + e.getName() + " HP: " + e.getHp());
            if (e.stunDuration()> 1) System.out.print(" [STUNNED: " + (e.stunDuration() - 1) + " turns left]");
        }
        System.out.println();

        // Active effects on player
        List<StatusEffect> effects = player.getStatusEffects();
        if (!effects.isEmpty()) {
            boolean headerplayed = false;

            for(StatusEffect e : effects){

                if(e.getDuration() > 1){
                    if(!headerplayed){
                        System.out.print("  Active effects on " + player.getName() + ":");
                        headerplayed = true;
                    }
                    System.out.print(" [" + e.getName() + ": " + (e.getDuration() - 1) + " turns left]");
                }

            }
            System.out.print("\n");
        }

        // Items and cooldown
        System.out.println("  Items: " + formatItems(player)
                + " | Skill CD: " + player.getSkillCooldown() + " rounds");
    }
	
	public void displayStunSkip(Combatant combatant) {
        System.out.println("  " + combatant.getName() + " -> STUNNED: Turn skipped");
    }

    public void displayEliminated(Combatant combatant) {
        System.out.println("  " + combatant.getName() + " ELIMINATED!");
    }

    public void displayBackupSpawn(List<Combatant> backupEnemies) {
        System.out.println();
        System.out.println("  !! All initial enemies defeated — BACKUP SPAWN !!");
        for (Combatant e : backupEnemies) {
            System.out.println("     + " + e.getLabel());
        }
    }
	

    
    
    public Action promptPlayerAction(Combatant player) {
        System.out.println();
        System.out.println("  " + player.getName() + "'s turn — choose an action:");
        System.out.println("    [1] Basic Attack");
        System.out.println("    [2] Defend (+10 DEF for 2 turns)");

        // Items
        List<Item> inventory = player.getInventory();
        if (!inventory.isEmpty()) {
            System.out.println("    [3] Use Item");
        } else {
            System.out.println("    [3] Use Item  (no items remaining)");
        }

        // Special skill
        if (player.isSkillAvailable()) {
            System.out.println("    [4] " + player.getSpecialSkill().getName() + " (READY)");
        } else {
            System.out.println("    [4] " + player.getSpecialSkill().getName()
                    + " (cooldown: " + player.getSkillCooldown() + " turns)");
        }

        int choice = readInt(1, 4);

        return switch (choice) {
            case 1 -> new BasicAttack();
            case 2 -> new Defend();
            case 3 -> {
                if (inventory.isEmpty()) {
                    System.out.println("  No items! Defaulting to Basic Attack.");
                    yield new BasicAttack();
                }
                Item chosen = promptItemSelection(player);
                yield new ItemAction(chosen);
            }
            case 4 -> {
                if (!player.isSkillAvailable()) {
                    System.out.println("  Special skill on cooldown! Defaulting to Basic Attack.");
                    yield new BasicAttack();
                }
                yield player.getSpecialSkill();
            }
            default -> new BasicAttack();
        };
    }
    
    private Item promptItemSelection(Combatant player) {
        List<Item> inventory = player.getInventory();
        System.out.println("  Select item:");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println("    [" + (i + 1) + "] " + inventory.get(i).getName());
        }
        int choice = readInt(1, inventory.size()) - 1;
        return player.removeItem(choice);
    }
    
    public Combatant promptTargetSelection(List<Combatant> targets) {
        if (targets.size() == 1) return targets.get(0);
        System.out.println("  Select target:");
        for (int i = 0; i < targets.size(); i++) {
            System.out.println("    [" + (i + 1) + "] " + targets.get(i).getName()
                    + " (HP: " + targets.get(i).getHp() + ")");
        }
        int choice = readInt(1, targets.size()) - 1;
        return targets.get(choice);
    }
    
    public void displayBattleEnd(boolean playerWon, Combatant player, List<Combatant> remainingEnemies, int totalRounds) {
    	System.out.println();
        System.out.println("══════════════════════════════════════════════════");
        if (playerWon) {
            System.out.println("  ★ VICTORY! Congratulations, you defeated all enemies!");
            System.out.println("  Statistics:");
            System.out.println("    Remaining HP : " + player.getHp() + " / " + player.getMaxhp());
            System.out.println("    Total Rounds : " + totalRounds);
            System.out.println("    Items left   : " + formatItems(player));
        } else {
            System.out.println("  ✗ DEFEATED. Don't give up, try again!");
            System.out.println("  Statistics:");
            System.out.println("    Enemies remaining  : " + remainingEnemies.size());
            System.out.println("    Total Rounds Survived: " + totalRounds);
        }
        System.out.println("══════════════════════════════════════════════════");
    	
    }
    

    //Helper
    
    private String formatItems(Combatant player) {
        List<Item> items = player.getInventory();
        if (items.isEmpty()) return "(none)";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            sb.append(items.get(i).getName());
            if (i < items.size() - 1) sb.append(", ");
        }
        return sb.toString();
    }

    private void printBanner() {
        System.out.println();
        System.out.println("  =========================================");
    }
    
    public int readInt(int min, int max) {
        while (true) {
            System.out.print("  > ");
            try {
                String line = scanner.nextLine().trim();
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) return value;
                System.out.println("  Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input. Please enter a number.");
            }
        }
    }
    





}
