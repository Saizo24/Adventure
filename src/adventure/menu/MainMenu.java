package adventure.menu;

import adventure.campaign.RandomDungeon;
import adventure.player.PlayerCharacter;

/**
 * This Class contains the Main menu und is the starting point of the game. You can either start a
 * new Game, read guides for the game or show the rankings (Work in Progress).
 */
public class MainMenu {

  private PlayerCharacter player;

  /*
   * String arrays containing options of the main menu and guides for printing to the console
   */
  private final String[] MAIN_OPTIONS = {
      "Start Game",
      "Show Guide",
      "Show Rankings"
  };

  private final String[] GUIDE_THEMES = {
      "Basics",
      "Classes",
      "Equipment",
      "Items",
      "Combat"
  };

  private final String[] CAMPAIGN_OPTIONS = {
      "Random Dungeon: The mystical Labyrinth of Zorian"
  };

  /**
   * Main method of the class. From here, user can start the game, open guides or look at the rankings
   */
  public void mainMenu() {
    boolean shutdown = false;
    System.out.println("Welcome to Adventure.");
    do {
      Menu.printOptions(MAIN_OPTIONS, true);

      int option = Menu.inputInt();
      switch (option) {
        case 0 -> shutdown = true;
        case 1 -> startGame();
        case 2 -> guidesMenu();
        case 3 -> showRankings();
        default -> System.out.println(Menu.INPUT_INVALID);
      }
    } while (!shutdown);

  }

  /**
   * Starts the character creation tool and then promts the user to choose a campaign to play
   */
  private void startGame() {
    player = CharacterCreation.createCharacter();
    chooseCampaign();
  }

  /**
   * Prints all campaigns to play and prompts user the choose a campaign
   */
  private void chooseCampaign() {
    int campaign;
    boolean check = false;
    do {
      System.out.println("Choose a campaign:");
      Menu.printOptions(CAMPAIGN_OPTIONS, false);
      campaign = Menu.inputInt();
      switch (campaign) {
        case 0 -> {
          startRandomDungeon();
          check = true;
        }
        default -> {
          System.out.println(Menu.INPUT_INVALID);
          check = false;
        }
      }
    } while (!check);

  }

  /**
   * Starts the default random dungeon campaign
   */
  private void startRandomDungeon() {
    RandomDungeon randomDungeon = new RandomDungeon(player);
    randomDungeon.playRandomDungeon();
  }

  /**
   * This menu lets the user pick a guide to read
   */
  private void guidesMenu() {
    boolean exit = false;
    do {
      System.out.println("What would you like to know more of?");
      Menu.printOptions(GUIDE_THEMES, true);
      int option = Menu.inputInt();
      if (option > 0) {
        openGuide(GUIDE_THEMES[option - 1]);
      } else if (option == 0) {
        exit = true;
      } else {
        System.out.println(Menu.INPUT_INVALID);
      }
    } while (!exit);

  }

  /**
   * Prints the guide for the user to read. Return to the guide menu by pressing enter
   * @param guideName contains name of the guide topic
   */
  private void openGuide(String guideName) {
    TextReader.loadText(guideName + ".txt");
    Menu.pressEnter();
  }

  /**
   * Shows the rankings (Work in Progress)
   */
  private void showRankings() {

  }

}
