package adventure.menu;

import adventure.combat.Enemy;
import adventure.interfaces.EquippableMainHand;
import adventure.interfaces.EquippableOffHand;
import adventure.player.PlayerCharacter;
import adventure.scenery.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Menu {

  public static Scanner input = new Scanner(System.in);

  public static final String INPUT_INVALID = "Invalid entry, please try again.";

  public static final String[] MELEE_ATTACK_LIST = {
      "%s swing%s at %s %s\n",
      "%s viciously attack%s %s %s\n"
  };

  public static final String[] HIT_LIST = {
      "and hit%s for %d damage.\n",
      "and gravely hit%s with a CRITICAL HIT for %d damage.\n"
  };

  public static final String[] MISS_LIST = {
      "but only grazes the armor.\n",
      "but %s barely dodge%s the attack.\n",
      "but %s swiftly block%s the attack.\n",
      "and fumble%s with a CRITICAL FAIL, falling prone.\n"
  };

  public static void printOptions(String[] options, boolean withExit) {
    int startIndex = (withExit ? 1 : 0);
    for (int i = startIndex; i < options.length + startIndex; i++) {
      System.out.println(i + " - " + options[i - startIndex]);
    }
    System.out.print(withExit ? "0 - Exit\n" : "");
  }

  public static void printEnemies(ArrayList<Enemy> enemies) {
    for (int i = 0; i < enemies.size(); i++) {
      System.out.println(i + " - " + enemies.get(i).getName());
    }
  }

  public static int inputInt() {
    int inputNumber = 0;
    boolean check;
    do {
      System.out.println("Please enter a number:");
      try {
        inputNumber = Integer.parseInt(input.nextLine());
        check = true;
      } catch (NumberFormatException e) {
        check = false;
        System.out.println("Entry not a number, please try again.");
      }
    } while (!check);

    return inputNumber;
  }

  public static boolean enterConfirmation() {
    String confirmation;
    do {
      System.out.print(("(Y/N): "));
      confirmation = input.nextLine();
      if (confirmation.equalsIgnoreCase("y")) {
        return true;
      } else if (confirmation.equalsIgnoreCase("n")) {
        return false;
      } else {
        System.out.println(INPUT_INVALID);
      }
    } while (true);
  }

  public static void playLoadingAnimation(int milliseconds) {
    try {
      for (int i = 0; i < 3; i++) {
        Thread.sleep(milliseconds);
        System.out.print(".");
      }
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      e.fillInStackTrace();
    }
    System.out.println();

  }

  public static void showPlayerStatus(PlayerCharacter player) {
    showPlayerInfo(player);
    showPlayerAttributes(player);
    showPlayerEquipment(player);

    pressEnter();
  }

  private static void showPlayerInfo(PlayerCharacter player) {
    System.out.println("--- Player Information ---");
    System.out.println("Name: " + player.getNAME());
    System.out.println("Race: " + player.getRACE());
    System.out.println("Level: Lvl " + player.getLevel() + " " + CharacterCreation.CLASS_LIST[player.CLASS_INDEX]);
    System.out.println("Exp: " + player.getExperiencePoints());

    System.out.println("Health: " + player.getHealthPoints() + " / " + player.getMaxHealthPoints() + " HP");
    System.out.println("Armor Class: " + player.calculateArmorClass());
  }

  private static void showPlayerAttributes(PlayerCharacter player) {
    System.out.println("\n--- Attributes ---");
    System.out.println("Strength: " + player.getStrength());
    System.out.println("Dexterity: " + player.getDexterity());
    System.out.println("Intelligence: " + player.getIntelligence());
  }

  private static void showPlayerEquipment(PlayerCharacter player) {
    System.out.println("\n--- Equipment ---");
    showMainHand(player);
    showOffHand(player);
    showArmor(player);
    System.out.println("---");
  }

  private static void showMainHand(PlayerCharacter player) {
    EquippableMainHand mainHand =  player.getMainHand();
    int weaponStat = player.getAttributes()[mainHand == null ? 0 : mainHand.getATTR_INDEX()];
    System.out.printf("Main Hand: %s\n", mainHand == null ? "Empty" : mainHand.getNAME());
    System.out.printf("Damage: 1%s +%d\n", mainHand == null ? "" : "-" + mainHand.getDAMAGE(), weaponStat);
    System.out.println("Atk. Bonus: +" + player.getAttributes()[mainHand == null ? 0 : mainHand.getATTR_INDEX()] + "\n");
  }

  private static void showOffHand(PlayerCharacter player) {
    EquippableOffHand offHand = player.getOffHand();
    if (offHand == null) {
      System.out.println("Off Hand : Empty");
    } else {
      System.out.println("Off Hand: " + offHand.getNAME());
      offHand.showStats();
    }
  }

  private static void showArmor(PlayerCharacter player) {
    if (player.getArmor() == null) {
      System.out.println("Armor: Empty");
    } else {
      System.out.println("Armor: " + player.getArmor().getNAME());
      player.getArmor().showStats();
    }
  }

  public static void pressEnter() {
    System.out.println("\nPress enter to continue");
    input.nextLine();
  }

  public static void printLayout(int[][] layout) {
    for (int[] arr : layout) {
      System.out.println(Arrays.toString(arr));
    }
  }

}