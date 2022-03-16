package adventure.menu;

import adventure.player.PlayerCharacter;
import adventure.player.Ranger;
import adventure.player.Warrior;
import adventure.player.Wizard;

import java.util.Scanner;

public class CharacterCreation {

  public static final String[] CLASS_LIST = {
      "Warrior",
      "Ranger",
      "Wizard",
  };

  public static final String[] RACE_LIST = {
      "Elf",
      "Half-Orc",
      "Human",
      "Halfling",
      "Dwarf"
  };

  public static final String[] GENDER_LIST = {
      "Male",
      "Female"
  };

  private static Scanner input = new Scanner(System.in);

  public static PlayerCharacter createCharacter() {
    PlayerCharacter player;

    do {
      String playerName = enterPlayerName();
      String gender = enterGender();
      String race = enterRace();

      int classIndex = enterPlayerClass();

      switch (classIndex) {
        case 0 -> player = new Warrior(playerName, race, gender);
        case 1 -> player = new Ranger(playerName, race, gender);
        case 2 -> player = new Wizard(playerName, race, gender);
        default -> player = null;
      }
      System.out.printf("Your Character is named %s and a %s %s %s. Is that correct?\n", playerName, gender, race, CLASS_LIST[classIndex]);
      if (!Menu.enterConfirmation()) {
        player = null;
      }
    } while (player == null);

    return player;
  }


  private static String enterPlayerName() {
    String playerName;
    do {
      System.out.println("Please enter the name of your character:");
      playerName = input.nextLine().trim();
      if (playerName.equals("")) {
        System.out.println("Name can't be empty");
        playerName = null;
      } else if (playerName.length() < 4 || playerName.length() > 20) {
        System.out.println("Name must be 4 to 20 characters long");
        playerName = null;
      }
    } while (playerName == null);

    return playerName;
  }

  private static String enterGender() {
    String gender;
    do {
      System.out.println("Choose your gender");
      Menu.printOptions(GENDER_LIST, false);
      try {
        gender = GENDER_LIST[Menu.inputInt()];
      } catch (IndexOutOfBoundsException e) {
        System.out.println(Menu.INPUT_INVALID);
        gender = null;
      }
    } while (gender == null);

    return gender;
  }

  private static String enterRace() {
    String race;
    do {
      System.out.println("Choose your race:");
      Menu.printOptions(RACE_LIST, false);
      try {
        race = RACE_LIST[Menu.inputInt()];
      } catch (IndexOutOfBoundsException e) {
        System.out.println(Menu.INPUT_INVALID);
        race = null;
      }

    } while (race == null);

    return race;
  }

  private static int enterPlayerClass() {
    int classIndex;
    boolean classConfirmed = false;
    do {
      System.out.println("Choose a class to show its description:");
      Menu.printOptions(CLASS_LIST, false);
      classIndex = Menu.inputInt();
      if (classIndex >= CLASS_LIST.length || classIndex < 0) {
        System.out.println(Menu.INPUT_INVALID);
      } else {
        classConfirmed = showClassDescription(classIndex);
      }
    } while (classIndex >= CLASS_LIST.length || classIndex < 0 || !classConfirmed);

    return classIndex;
  }

  private static boolean showClassDescription(int classIndex) {
    TextReader.loadText(CLASS_LIST[classIndex] + "Description.txt");
    System.out.println("Choose this class?");
    return Menu.enterConfirmation();
  }
}
