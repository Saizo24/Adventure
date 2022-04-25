package adventure.campaign;

import adventure.combat.Encounter;
import adventure.interfaces.UsableItem;
import adventure.menu.Menu;
import adventure.player.PlayerCharacter;
import adventure.scenery.Room;

import java.util.ArrayList;
import java.util.Random;

public class RandomDungeon extends Campaign {

  public static final int DUNGEON_BASE_WIDTH = 6;
  public static final int DUNGEON_BASE_HEIGHT = 6;

  public final String[] DEFAULT_OPTIONS = {
    "Search this room.",
    "Look for any Doors.",
    "Show Status",
    "Open Inventory",
    "Show Map",
    "Go back",
    "Exit the dungeon"
  };

  public final String[] EXIT_OPTIONS = {
    "Go deeper",
    "Keep exploring"
  };

  private int startRoomNumber;
  private Room activeRoom;
  int currentLevel = 0;

  private Random random = new Random();

  public RandomDungeon() {
    createRandomDungeon(0);
  }

  public RandomDungeon(PlayerCharacter player) {
    super(player);
    createRandomDungeon(0);
  }

  public void playRandomDungeon() {
    openRoomMenu();
  }

  private void openRoomMenu() {
    boolean exiting = false;
    do {
      activeRoom.playRoom(this.player);
      System.out.println("What do want to do?");
      Menu.printOptions(DEFAULT_OPTIONS, false);
      int action = Menu.inputInt();
      switch (action) {
        case 0 -> searchTheRoom();
        case 1 -> getDoors();
        case 2 -> Menu.showPlayerStatus(player);
        case 3 -> {
          player.openInventory();
          Menu.pressEnter();
        }
        case 4 -> showMap();
        case 5 -> goBack();
        case 6 -> exiting = true;
        default -> System.out.println(Menu.INPUT_INVALID);
      }
    } while (!exiting);
  }

  private void searchTheRoom() {
    if (activeRoom.isSearched()) {
      System.out.println("You already searched the room.");
    } else {
      System.out.println("You look through the rubbish");
      if (activeRoom.getTreasureList().isEmpty()) {
        System.out.println("but there's nothing of value here.");
      } else {
        treasureFound();
      }
      activeRoom.setSearched(true);
    }

    Menu.pressEnter();
    if (activeRoom.isExit()) {
      exitFound();
    }
  }

  private void treasureFound() {
    System.out.println("you find a treasure in a secret compartment of the wall.");
    System.out.println("You receive the following:");
    for (UsableItem usableItem : activeRoom.getTreasureList()) {
      System.out.println("- " + usableItem.getNAME());
    }
    System.out.println("- " + activeRoom.getGoldCoins() + " Goldcoins");
    player.getInventory().addAll(activeRoom.getTreasureList());
    player.addGoldCoins(activeRoom.getGoldCoins());
    activeRoom.getTreasureList().clear();
  }

  private void exitFound() {
    boolean check;
    System.out.println("You find a stairway, going deeper into the dungeon.");
    do {
      check = true;
      System.out.println("Would you like to go down the stairs or keep exploring?");
      Menu.printOptions(EXIT_OPTIONS, false);
      int action = Menu.inputInt();
      switch (action) {
        case 0 -> {
          System.out.println("As you walk down the stairs, you feel a little tingle.\n" +
            "You feel the dungeon shift and reform. Suddenly, you can see the daylight behind you.\n" +
            "You are standing at the entrance.");
          createRandomDungeon(currentLevel + 1);
        }
        case 1 -> System.out.println("You move away from the stairway. There is still much to explore here.");
        default -> {
          System.out.println(Menu.INPUT_INVALID);
          check = false;
        }
      }
    } while (!check);

  }

  private void getDoors() {
    int doors = activeRoom.countDoors(layout, activeRoom.X, activeRoom.Y) - (activeRoom.isStart() ? 0 : 1);
    if (doors > 0) {
      System.out.printf("There are %d doors.\n", doors);
      String[] door_options = activeRoom.generateDoorOptions();
      System.out.println("Which door do you want to go through?");
      Menu.printOptions(door_options, false);
      int action = Menu.inputInt();
      switch (door_options[action]) {
        case "North Door" -> setActiveRoom(activeRoom.X, activeRoom.Y - 1);
        case "East Door" -> setActiveRoom(activeRoom.X + 1, activeRoom.Y);
        case "South Door" -> setActiveRoom(activeRoom.X, activeRoom.Y + 1);
        case "West Door" -> setActiveRoom(activeRoom.X - 1, activeRoom.Y);
        default -> {
          break;
        }
      }
      System.out.println("You walk through the " + door_options[action] + ".");
    } else {
      System.out.println("This is a dead end. No other doors here.");
    }
    Menu.pressEnter();
  }

  private void showMap() {
    for (int i = 0; i < layout.length; i++) {
      for (int j = 0; j < layout[i].length; j++) {
        if (layout[i][j] == 1 && searchForRoom(j, i).isVisited() ) {
          System.out.print(searchForRoom(j, i) == activeRoom ? "|1|" : "|x|");
        } else
          System.out.print("| |");
      }
      System.out.println();
    }
  }


  private void goBack() {
    if (roomTracker.size() > (activeRoom.isStart() ? 1 : 0)) {
      System.out.println("You go back to the last room.");
      if (roomTracker.size() > 1) {
        roomTracker.remove(roomTracker.size() - 1);
      }
      setActiveRoom(lastRoomNumber(0)[0], lastRoomNumber(0)[1]);
      roomTracker.remove(roomTracker.size() - 1);
    } else {
      System.out.println("You're at the entrance, you can't go back further.");
    }
    Menu.pressEnter();
  }

  public void setActiveRoom(int x, int y) {
    activeRoom = searchForRoom(x, y);
    int[] coords = {x, y};
    roomTracker.add(coords);
  }

  public void createRandomDungeon(int level) {
    roomTracker.clear();
    generateLayout(DUNGEON_BASE_WIDTH + level, DUNGEON_BASE_HEIGHT + level);
    generateRooms(layout);
    distributeEncounter(dungeon.size() / 3);
    distributeTreasure(dungeon.size() / 3);
    setExitRoom();
    setStartingRoom();
    Menu.printLayout(layout);
    currentLevel++;
  }

  private void generateRooms(int[][] layout) {
    dungeon.clear();
    for (int i = 0; i < layout.length; i++) {
      for (int j = 0; j < layout[i].length; j++) {
        if (layout[i][j] == 1) {
          Room room = new Room(this, j, i);
          dungeon.add(room);
        }
      }
    }
  }

  public void generateLayout(int width, int height) {
    layout = new int[height][width];
    startRoomNumber = random.nextInt(width);
    layout[0][startRoomNumber] = 1;

    layout = fillLayoutSecondIteration(fillLayoutFirstIteration(layout));
  }

  public int[][] fillLayoutFirstIteration(int[][] layout) {
    for (int i = 0; i < layout.length; i += 2) {
      for (int j = 0; j < layout[i].length / 3; j++) {
        layout[i][random.nextInt(layout[i].length)] = 1;
      }
    }
    return layout;
  }

  public int[][] fillLayoutSecondIteration(int[][] layout) {
    for (int i = 1; i < layout.length; i += 2) {
      for (int j = 0; j < layout[i].length; j++) {
        int x = j;
        if (layout[i - 1][x] == 1 || (i + 1 >= layout.length ? false : (layout[i + 1][x] == 1))) {
          layout[i][x] = 1;
          x++;
          while ((x != layout[i].length) && (i + 1 >= layout.length ? false : (layout[i + 1][x] != 1) && layout[i - 1][x] != 1)) {
            layout[i][x] = 1;
            x++;
          }

        }
      }
    }
    return layout;
  }

  private void distributeEncounter(int count) {

    while (count > 0) {
      Room room = dungeon.get(random.nextInt(dungeon.size()));
      if (room.getEncounter() == null) {
        Encounter encounter = new Encounter(room);
        encounter.generateEnemies(random.nextInt(2) + 1, currentLevel);
        room.setEncounter(encounter);
        count--;
      }
    }
  }

  private void distributeTreasure(int count) {
    while (count > 0) {
      Room room = dungeon.get(random.nextInt(dungeon.size()));
      if (room.getTreasureList().isEmpty()) {
        room.generateTreasure(this.player.getCLASS_INDEX());
        count--;
      }
    }
  }

  private void setExitRoom() {
    int y;
    int x;
    do {
      y = layout.length - random.nextInt(layout.length / 3) - 1;
      x = random.nextInt(layout[0].length);
    } while (layout[y][x] != 1);
    Room exit = searchForRoom(x, y);
    exit.setExit(true);
  }

  private void setStartingRoom() {
    setActiveRoom(startRoomNumber, 0);
    activeRoom.setStart(true);
    System.out.println("Starting number: " + startRoomNumber);
    System.out.println("Starting room is " + activeRoom.X + ", " + activeRoom.Y);
  }
}
