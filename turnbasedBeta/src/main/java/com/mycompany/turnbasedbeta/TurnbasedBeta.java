package com.mycompany.turnbasedbeta;

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

public class TurnbasedBeta {

    static Scanner scan = new Scanner(System.in);
    static Random rand = new Random();

    static String[] sects = { "Mount Hua Sect", "Southern Edge Sect", "Wudang Sect" };
    static String[][] sectSkills = {
        { "Plum Blossom Sword Art", "Mount Hua Divine Sword", "Flowing Sword Technique", "Seven Sword Stance" },
        { "Southern Edge Sword", "Thousand Step Flow", "Moonlight Edge", "Falling Petal Slash" },
        { "Tai Chi Sword", "Wudang Cloud Steps", "Heavenly Qi Palm", "Waterflow Defense" }
    };

    static String[] mainLocations = {
        "Main Hall", "Training Grounds", "Market", "Plum Blossom Forest",
        "Phantom Valley", "Shraal Mountain", "Secret Tombs of Medicine Great Emperor"
    };

    static class Player {
        String name;
        String sect;
        int hp = 120;
        int qi = 80;
        int atk = 18;
        int def = 7;
        int silver = 50;
        ArrayList<String> skills = new ArrayList<>();
        ArrayList<String> inventory = new ArrayList<>();
    }

    static class Enemy {
        String name;
        String sect;
        int hp;
        int atk;
        int def;
        String skill;
    }

    public static void main(String[] args) {
        System.out.println("Return of the Mount Hua Sect (Inspired Game)");
        Player player = new Player();

        System.out.print("Enter your martial name: ");
        player.name = scan.nextLine();
        System.out.println("Choose your sect:");
        for (int i = 0; i < sects.length; i++) {
            System.out.println((i + 1) + ". " + sects[i]);
        }
        int sectChoice = safeIntInput(1, sects.length) - 1;
        player.sect = sects[sectChoice];

        for (String skill : sectSkills[sectChoice]) {
            player.skills.add(skill);
        }

        System.out.println("Welcome, " + player.name + " of the " + player.sect + "!");

        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Status");
            System.out.println("2. Travel");
            System.out.println("3. Practice Sword Art");
            System.out.println("4. Rest");
            System.out.println("5. Exit");
            System.out.print("Pick: ");
            String choice = scan.nextLine();

            switch (choice) {
                case "1" -> showStatus(player);
                case "2" -> travelMenu(player);
                case "3" -> practiceSkill(player);
                case "4" -> {
                    player.hp = 120;
                    player.qi = 80;
                    System.out.println("You meditate under the plum blossoms and recover your HP and Qi.");
                }
                case "5" -> {
                    System.out.println("May your sword reach the peak of Mount Hua!");
                    return;
                }
                default -> System.out.println("Invalid.");
            }
        }
    }

    static void showStatus(Player p) {
        System.out.println("\n--- Sword Disciple Status ---");
        System.out.println("Name: " + p.name);
        System.out.println("Sect: " + p.sect);
        System.out.println("HP: " + p.hp + "/120");
        System.out.println("Qi: " + p.qi + "/80");
        System.out.println("Attack: " + p.atk + "  Defense: " + p.def);
        System.out.println("Silver: " + p.silver);
        System.out.println("Sword Arts: " + p.skills);
        System.out.println("Inventory: " + p.inventory);
    }

    static void travelMenu(Player player) {
        System.out.println("\nWhere do you wish to go?");
        for (int i = 0; i < mainLocations.length; i++) {
            System.out.println((i + 1) + ". " + mainLocations[i]);
        }
        int loc = safeIntInput(1, mainLocations.length) - 1;
        String place = mainLocations[loc];

        switch (place) {
            case "Main Hall" -> mainHallMenu(player);
            case "Training Grounds" -> trainingMenu(player);
            case "Market" -> marketMenu(player);
            case "Plum Blossom Forest" -> forestMenu(player);
            case "Phantom Valley" -> phantomMenu(player);
            case "Shraal Mountain" -> shraalMenu(player);
            case "Secret Tombs of Medicine Great Emperor" -> tombsMenu(player);
            default -> System.out.println("Nothing here yet.");
        }
    }

    static void mainHallMenu(Player player) {
        System.out.println("\n-- Mount Hua Main Hall --");
        System.out.println("The sect’s core: used for meetings, missions, and announcements.\n" +"");
        System.out.println("The Sect Leader gives a rousing speech about restoring Mount Hua's former glory.");
    }

    static void trainingMenu(Player player) {
        System.out.println("\n-- Training Grounds --");
        player.qi = Math.min(80, player.qi + 8);
        player.atk++;
        System.out.println("Sword intent sharpens. (Attack +1, Qi +8)");

        if (rand.nextInt(3) == 0) {
            System.out.println("A senior disciple challenges you to a spar!");
            battle(player, randomEnemy());
        }
    }

    static void marketMenu(Player player) {
        System.out.println("\n-- Market Street --");
        System.out.println("1. Buy Qi Pill (10 silver)");
        System.out.println("2. Buy Bamboo Sword (18 silver)");
        System.out.println("3. Sell item");
        System.out.println("4. Leave");
        System.out.print("Pick: ");
        String c = scan.nextLine();

        switch (c) {
            case "1" -> {
                if (player.silver >= 10) {
                    player.silver -= 10;
                    player.inventory.add("Qi Pill");
                    System.out.println("You bought a Qi Pill!");
                } else System.out.println("Not enough silver.");
            }
            case "2" -> {
                if (player.silver >= 18) {
                    player.silver -= 18;
                    player.atk += 2;
                    System.out.println("You bought a Bamboo Sword! (Attack +2)");
                } else System.out.println("Not enough silver.");
            }
            case "3" -> {
                if (player.inventory.isEmpty()) {
                    System.out.println("You have nothing to sell.");
                } else {
                    for (int i = 0; i < player.inventory.size(); i++) {
                        System.out.println((i + 1) + ". " + player.inventory.get(i));
                    }
                    System.out.print("Pick item to sell: ");
                    int sell = safeIntInput(1, player.inventory.size()) - 1;
                    String item = player.inventory.remove(sell);
                    int sellValue = 4 + rand.nextInt(9);
                    player.silver += sellValue;
                    System.out.println("You sold " + item + " for " + sellValue + " silver.");
                }
            }
            default -> System.out.println("You leave the market.");
        }
    }

    static void forestMenu(Player player) {
        System.out.println("\n-- Plum Blossom Forest --");
        System.out.println(" A peaceful forest where Mount Hua disciples once trained in sword arts.");
        int event = rand.nextInt(3);
        switch (event) {
            case 0 -> {
                System.out.println("You find a wild ginseng root under a tree!");
                player.inventory.add("Wild Ginseng");
            }
            case 1 -> {
                System.out.println("A rogue swordsman blocks your path!");
                battle(player, randomEnemy());
            }
            case 2 -> {
                System.out.println("You meditate, breathing the forest air. Qi +7.");
                player.qi = Math.min(80, player.qi + 7);
            }
        }
    }

    static void phantomMenu(Player player) {
        System.out.println("\n-- Phantom Valley --");
        System.out.println("A misty ravine known for illusions and hidden dangers.");
        int event = rand.nextInt(3);
        switch (event) {
            case 0 -> {
                System.out.println("You found a Qi Ghostly Pill in a secret cave.");
                player.inventory.add("Qi Ghostly Pill");
            }
            case 1 -> {
                System.out.println("Ghost Cultivators sense you.. and desire to capture your body!");
                battle(player, randomEnemy());
            }
            case 2 -> {
                System.out.println("A hermit guides you through the mist. Qi +55");
                player.qi = Math.min(80, player.qi + 55);
            }
        }
    }

    static void shraalMenu(Player player) {
        System.out.println("\n-- Shraal Mountain --");
        System.out.println("A stormy peak with fierce beasts and wandering martial hermits.");
        int event = rand.nextInt(3);
        switch (event) {
            case 0 -> {
                System.out.println("A mysterious beauty wishes you luck. You received Recover Qi Pills.");
                player.inventory.add("Recover Qi Pills");
            }
            case 1 -> { 
                System.out.println("A bandit ambushes you!");
                battle(player, randomEnemy());
            }
            case 2 -> {
                System.out.println("You find a strange sword manual and are ambushed by Southern Sect disciples!");
                player.inventory.add("Strange Sword Manual");
                battle(player, randomEnemy());
                battle(player, randomEnemy());
                battle(player, randomEnemy());
            }
        }
    }

    static void tombsMenu(Player player) {
        System.out.println("\n-- Tombs of the Medicine Great Emperor --");
        System.out.println("A deadly crypt rumored to hold secrets and treasures.");
        int event = rand.nextInt(3);
        switch (event) {
            case 0 -> {
                System.out.println("Sword marks on the walls inspire you.");
                player.qi = Math.min(80, player.qi + 76);
            }
            case 1 -> {
                System.out.println("A Wudang disciple blocks your way.");
                battle(player, randomEnemy());
                player.inventory.add("Qi Pills");
                player.inventory.add("Sword of Heaven");
            }
            case 2 -> {
                System.out.println("You meet the guardian of the tombs... you feel immense danger.");
                battle(player, randomEnemy());
            }
        }
    }

    static void practiceSkill(Player player) {
        System.out.println("\nWhich sword art to practice?");
        for (int i = 0; i < player.skills.size(); i++) {
            System.out.println((i + 1) + ". " + player.skills.get(i));
        }
        int n = safeIntInput(1, player.skills.size()) - 1;
        String skill = player.skills.get(n);
        System.out.println("You practice " + skill + ". Your Qi increases.");
        player.qi = Math.min(80, player.qi + 10);
    }

    static Enemy randomEnemy() {
        Enemy e = new Enemy();
        String[] names = {
    "Southern Edge Disciple", "Bandit Swordsman", "Strange Hermit", "Plum Blossom Sword Saint",
    "Cheonma the Heavenly Demon", "Southern Edge Sect Master", "Demonic Cult Bishop", "Snow Blossom Thief",
    "Ten Thousand Men Sect Leader", "Black Dragon King", "Thousand Face Gentleman", "Tang Clan Poisoner",
    "Wudang Elder", "Zhongnan Sect Rival", "Namgung Family Patriarch", "Peng Family Assassin",
    "Morong Clan Strategist", "Zhuge Family Tactician", "Nanman Beast Palace Chief", "Northern Ice Palace Lord",
    "Southern Sun Palace Warrior", "Potala Palace Monk", "Mara Blood Palace Butcher", "Nokrim Bandit Chief",
    "Green Forest Bandit Leader", "Pirate Fortress Captain", "Black Ghost Castle Warden", "Hao Sect Spy Master",
    "Northern Blade Jin Mu-Won", "Silent Night Assassin", "Hurricane Shadow Demon", "Spear of Black Wings",
    "Iron Brigade Mercenary", "White Night Witch", "Wudang Sect Sage", "Nine Skies Elder",
    "Central Heavenly Alliance Leader", "Demon of Chaos Tae Mu-Kang", "Yin Yang Hairpin Sage", "Moyong Family Scion",
    "Dam Soo-Cheon the Unyielding", "Moyong Hyun the Swift", "Seo Geum-Hyang the Cold Witch", "Yoo Seon-Myung the Shadow Demon",
    "Mun Cheon the Spear Master", "Jeok-Yeob the Wudang Master", "Yong Mu-Sung the Iron Leader", "Jin Kwan-Ho the Fallen Hero",
    "Eun Ha-Seol the White Night", "Sa-Ryung the Vengeful", "Hwang-Cheol the Loyal"
};
        e.name = names[rand.nextInt(names.length)] + " " + (char) ('A' + rand.nextInt(5));
        e.sect = sects[rand.nextInt(sects.length)];
        e.hp = 50 + rand.nextInt(31);
        e.atk = 12 + rand.nextInt(5);
        e.def = 5 + rand.nextInt(3);
        int sectIndex = Arrays.asList(sects).indexOf(e.sect);
        e.skill = sectSkills[sectIndex][rand.nextInt(4)];
        return e;
    }

    static void battle(Player p, Enemy e) {
        System.out.println("Duel: " + e.name + " (" + e.sect + ") uses " + e.skill + "!");
        while (p.hp > 0 && e.hp > 0) {
            System.out.println("Your HP: " + p.hp + " | Enemy HP: " + e.hp);
            System.out.print("1. Sword Strike  ");
            for (int i = 0; i < p.skills.size(); i++) {
                System.out.print((i + 2) + ". " + p.skills.get(i) + "  ");
            }
            System.out.println((p.skills.size() + 2) + ". Use Qi Pill  " + (p.skills.size() + 3) + ". Retreat");
            String c = scan.nextLine();
            int skillOption;
            try {
                skillOption = Integer.parseInt(c);
            } catch (Exception ex) {
                System.out.println("Invalid input.");
                continue;
            }

            if (skillOption == 1) {
                int dmg = Math.max(1, p.atk - e.def);
                e.hp -= dmg;
                System.out.println("You strike for " + dmg + "!");
            } else if (skillOption >= 2 && skillOption <= (1 + p.skills.size())) {
                int skillIndex = skillOption - 2;
                if (p.qi >= 12) {
                    int dmg = Math.max(1, p.atk + 7 + skillIndex * 2 - e.def);
                    e.hp -= dmg;
                    p.qi -= 12;
                    System.out.println("You use " + p.skills.get(skillIndex) + " for " + dmg + " damage!");
                } else {
                    System.out.println("Not enough Qi!");
                }
            } else if (skillOption == (p.skills.size() + 2)) {
                if (p.inventory.contains("Qi Pill")) {
                    p.qi = Math.min(80, p.qi + 20);
                    p.inventory.remove("Qi Pill");
                    System.out.println("You use a Qi Pill and restore Qi!");
                } else {
                    System.out.println("You have no Qi Pills!");
                }
            } else if (skillOption == (p.skills.size() + 3)) {
                System.out.println("You retreat from the duel!");
                return;
            } else {
                System.out.println("Invalid option.");
                continue;
            }

            if (e.hp > 0) {
                int edmg = Math.max(1, e.atk - p.def);
                p.hp -= edmg;
                System.out.println(e.name + " attacks you for " + edmg + "!");
            }
        }

        if (p.hp <= 0) {
            System.out.println("You were defeated! (Lose 12 silver, HP restored)");
            p.silver = Math.max(0, p.silver - 12);
            p.hp = 120;
        } else {
            System.out.println("You win the duel! (Gain 12 silver)");
            p.silver += 12;
        }
    }

    static int safeIntInput(int min, int max) {
        try {
            int n = Integer.parseInt(scan.nextLine());
            if (n >= min && n <= max) return n;
        } catch (Exception ignored) {}
        System.out.println("Invalid, using " + min);
        return min;
    }
}
