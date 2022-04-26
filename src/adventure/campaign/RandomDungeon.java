package adventure.campaign;

import adventure.combat.Encounter;
import adventure.interfaces.UsableItem;
import adventure.menu.Menu;
import adventure.player.PlayerCharacter;
import adventure.scenery.Room;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class contains the default random dungeon. It's a square layout, starting with six rooms in length
 * and width. Increases by 1 with each level you go down in the dungeon. The dungeon is always completely
 * randomly generated, from loot to enemy.
 */
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

  /**
   * This method prints the different options for player in a room. User can choose an option
   * by entering the corresponding number.
   */
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

  /**
   * This methods checks the treasure list of a room. If it contains items, they will be added to
   * the player's inventory. If a player already searched a room, a message will be printed. If
   * the room is the exit to the next level, it will prompt the user.
   */
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

  /**
   * If a room contains treasure, all the items found will be printed and added to the player's inventory
   */
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

  /**
   * If the exit to the next level is found, the user will be prompted to either enter the next level or
   * he can remain in the current level. On entering the next level, the dungeon will be newly generated
   */
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

  /**
   * This method checks the current room for doors leading into other rooms. It will not show the door from which
   * the player just came through. The options for a door will be generated based on the available doors. If the
   * room contains no other doors, a message will be printed.
   */
  private void getDoors() {
    int doors = activeRoom.countDoors(layout, activeRoom.X, activeRoom.Y) - (activeRoom.isStart() ? 0 : 1);
    if (doors > 0) {
      System.out.printf("There are %d doors.\n", doors);
      //generates the door options for printing
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

<<<<<<< Updated upstream
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


=======
  /**
   * This method lets the player return to his last visited room. You can't back out further than the starting
   * room of a level.
   */
>>>>>>> Stashed changes
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

  /**
   * This method sets a room as active, meaning this is the room where the player is currently in. It will be
   * determined by a set of coordinates. If the room is not found for any reason, it will be set as null
   * @param x contains the x-coordinate
   * @param y contains the y-coordinate
   */
  public void setActiveRoom(int x, int y) {
    activeRoom = searchForRoom(x, y);
    int[] coords = {x, y};
    roomTracker.add(coords);
  }

  /**
   * This method randomly generates a whole floor of the dungeon. It randomly generates the layout, rooms, enemies,
   * treasures, starting and exit rooms.
   * @param level determines, how big and difficult the current floor is
   */
  public void createRandomDungeon(int level) {
    roomTracker.clear();
    generateLayout(DUNGEON_BASE_WIDTH + level, DUNGEON_BASE_HEIGHT + level);
    generateRooms(layout);
    distributeEncounter(dungeon.size() / 3);
    distributeTreasure(dungeon.size() / 3);
    setExitRoom();
    setStartingRoom();
    Menu.printLayout(layout); // Only displays for test purposes, must be removed before game release
    currentLevel++;
  }

  /**
   * This method creates room objects according to the layout and saves it.
   * @param layout determines which rooms are needed
   */
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

  /**
   * This method generates a layout of a floor of the dungeon. It generates it in two steps. First,
   * it will ramdomly place rooms all over the layout. Then it will connect all rooms. it also sets
   * the starting room, which will always be on top row.
   * @param width determines the width of the to be generated floor
   * @param height determines the height of the to be generated floor
   */
  public void generateLayout(int width, int height) {
    layout = new int[height][width];
    startRoomNumber = random.nextInt(width);
    layout[0][startRoomNumber] = 1;

    layout = fillLayoutSecondIteration(fillLayoutFirstIteration(layout));
  }

  /**
   * This method is the first step of generating a layout for the current floor. For every two rows, it will
   * pick three random numbers, where the same number can be picked. This results to one to three rooms every
   * second row.
   * @param layout contains the (usually empty) layout to be filled
   * @return the layout, after the first step has filled it with rooms
   */
  public int[][] fillLayoutFirstIteration(int[][] layout) {
    for (int i = 0; i < layout.length; i += 2) {
      for (int j = 0; j < layout[i].length / 3; j++) {
        layout[i][random.nextInt(layout[i].length)] = 1;
      }
    }
    return layout;
  }

  /**
   * This method is the second step of generating a layout for the current floor. The rows, which are still empty,
   * will be now used to connect all rooms. It will check, whether the top or bottom neighbor is a room. It will fill
   * the current row with rooms until the top or bottom neighbor is a room again. This results in all rooms generated
   * in the first step being connected
   * @param layout contains the layout after the first step of filling it with rooms
   * @return the complete layout of a more or less random maze
   */
  public int[][] fillLayoutSecondIteration(int[][] layout) {
    for (int i = 1; i < layout.length; i += 2) {
      for (int j = 0; j < layout[i].length; j++) {
        int x = j;
        //checks whether the top or bottom neighbor is a room or if it's already at the bottom of the floor
        if (layout[i - 1][x] == 1 || (i + 1 >= layout.length ? false : (layout[i + 1][x] == 1))) {
          layout[i][x] = 1;
          x++;
          //keeps filling the row with rooms until it reaches the end of the row or if the top or bottom row is a room
          while ((x != layout[i].length) && (i + 1 >= layout.length ? false : (layout[i + 1][x] != 1) && layout[i - 1][x] != 1)) {
            layout[i][x] = 1;
            x++;
          }

        }
      }
    }
    return layout;
  }

  /**
   * This method fills random rooms with encounters and enemies. It randomly picks out of the list of rooms until
   * a specific amount of encounters has been created
   * @param count determines the amount of encounters to be created
   */
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

  /**
   * This method fills random rooms with treasure. It randomly picks out of the list of rooms until a specific
   * amount of rooms has been filled with treasure.
   * @param count determines the amount of rooms to be filled with treasure
   */
  private void distributeTreasure(int count) {
    while (count > 0) {
      Room room = dungeon.get(random.nextInt(dungeon.size()));
      if (room.getTreasureList().isEmpty()) {
        room.generateTreasure(this.player.getCLASS_INDEX());
        count--;
      }
    }
  }

  /**
   * This method determines which room contains the exit to the next floor. The exit is always in the bottom three
   * row of the dungeon.
   */
  private void setExitRoom() {
    int y;
    int x;
    //generates random sets of coordinate for the exit room until one is found
    do {
      y = layout.length - random.nextInt(layout.length / 3) - 1;
      x = random.nextInt(layout[0].length);
    } while (layout[y][x] != 1);
    Room exit = searchForRoom(x, y);
    exit.setExit(true);
  }

  /**
   * This methods sets the starting room as active room.
   */
  private void setStartingRoom() {
    setActiveRoom(startRoomNumber, 0);
    activeRoom.setStart(true);
    System.out.println("Starting number: " + startRoomNumber);                      // only displays for test purpose, must be removed on game release
    System.out.println("Starting room is " + activeRoom.X + ", " + activeRoom.Y);   // only displays for test purpose, must be removed on game release
  }
}
