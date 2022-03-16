package adventure.menu;

import adventure.campaign.RandomDungeon;
import adventure.player.PlayerCharacter;

import java.util.HashMap;

public class MainMenu {

  private PlayerCharacter player;

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

  private void startGame() {
    player = CharacterCreation.createCharacter();
    chooseCampaign();
  }

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

  private void startRandomDungeon() {
    RandomDungeon randomDungeon = new RandomDungeon(player);
    randomDungeon.playRandomDungeon();
  }

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

  private void openGuide(String guideName) {
    TextReader.loadText(guideName + ".txt");
    Menu.pressEnter();
  }

  private void showRankings() {

  }

}
