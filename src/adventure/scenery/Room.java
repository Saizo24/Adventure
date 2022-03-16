package adventure.scenery;

import adventure.campaign.Campaign;
import adventure.combat.Encounter;
import adventure.items.*;
import adventure.items.weapons.*;
import adventure.interfaces.UsableItem;
import adventure.menu.TextReader;
import adventure.player.PlayerCharacter;
import java.util.ArrayList;
import java.util.Random;

public class Room {

  private static Random random = new Random();

  public static final String[] ROOM_DESCRIPTION_LIST = TextReader.loadDescription("RoomDescriptions.txt");

  public final Campaign CAMPAIGN;
  public final String ROOM_DESCRIPTION;
  public final String ROOM_NUMBER;

  public final int X;
  public final int Y;
  private boolean isExit;
  private boolean isStart = false;
  private boolean isSearched = false;

  private Encounter encounter = null;

  private String[] roomOptions = {};
  private ArrayList<UsableItem> treasureList = new ArrayList<>();
  private int goldCoins = 0;

  public Room(Campaign CAMPAIGN, int x, int y) {
    this.CAMPAIGN = CAMPAIGN;
    this.X = x;
    this.Y = y;
    this.ROOM_NUMBER = "" + x + y;
    this.ROOM_DESCRIPTION = ROOM_DESCRIPTION_LIST[random.nextInt(ROOM_DESCRIPTION_LIST.length)];
  }

  public Encounter getEncounter() {
    return encounter;
  }

  public void setEncounter(Encounter encounter) {
    this.encounter = encounter;
  }

  public ArrayList<UsableItem> getTreasureList() {
    return treasureList;
  }

  public boolean isExit() {
    return isExit;
  }

  public void setExit(boolean exit) {
    isExit = exit;
  }

  public boolean isStart() {
    return isStart;
  }

  public void setStart(boolean start) {
    isStart = start;
  }

  public boolean isSearched() {
    return isSearched;
  }

  public void setSearched(boolean searched) {
    isSearched = searched;
  }

  public int getGoldCoins() {
    return goldCoins;
  }

  public void playRoom(PlayerCharacter player) {
    System.out.println("Room: " + ROOM_NUMBER);
    System.out.println(ROOM_DESCRIPTION);
    if (encounter != null && !encounter.playEncounter(player)) {
      this.encounter = null;
    }
  }


  public void generateTreasure(int classIndex) {
    int treasureCount = random.nextInt(3) + 1;
    for (int i = 0; i < treasureCount; i++) {
      int treasureType = random.nextInt(4);
      UsableItem loot;
      switch (treasureType) {
        case 0 -> loot = Weapon.generateRandomWeapon(classIndex);
        case 1 -> loot = (random.nextInt(100) > 25 ? new Armor(random, Armor.ARMOR_TYPES.length - classIndex - 1) : new Shield(random, "Shield"));
        default -> loot = new Potion((classIndex < 2 ? 0 : random.nextInt(2)), random.nextInt(Potion.POTENCY.length));
      }
      treasureList.add(loot);
    }
    goldCoins = (random.nextInt(6) + 1) * 50;
  }

  public String[] generateDoorOptions() {
    int doors = countDoors(this.CAMPAIGN.layout, X, Y) - (CAMPAIGN.getRoomTracker().size() > 1 ? 1 : 0);
    int optionCount = 0;
    String[] options = new String[doors];
    int[] lastRoom = CAMPAIGN.lastRoomNumber(0);
    if (ROOM_NUMBER.equalsIgnoreCase("" + lastRoom[0] + lastRoom[1]) && CAMPAIGN.getRoomTracker().size() > 1) {
      lastRoom = CAMPAIGN.lastRoomNumber(1);
    }
    do {
      if (Y - 1 >= 0 && CAMPAIGN.layout[Y - 1][X] == 1 && (lastRoom[0] != X || lastRoom[1] != (Y - 1))) {
        options[optionCount] = "North Door";
        optionCount++;
      }
      if (X + 1 < CAMPAIGN.layout[0].length && CAMPAIGN.layout[Y][X + 1] == 1 && (lastRoom[0] != (X + 1) || lastRoom[1] != Y)) {
        options[optionCount] = "East Door";
        optionCount++;
      }
      if (Y + 1 < CAMPAIGN.layout.length && CAMPAIGN.layout[Y + 1][X] == 1 && (lastRoom[0] != X || lastRoom[1] != (Y + 1))) {
        options[optionCount] = "South Door";
        optionCount++;
      }
      if (X - 1 >= 0 && CAMPAIGN.layout[Y][X - 1] == 1 && (lastRoom[0] != (X - 1) || lastRoom[1] != Y)) {
        options[optionCount] = "West Door";
        optionCount++;
      }

    } while (optionCount < options.length - 2);
    return options;
  }

  public int countDoors(int[][] layout, int x, int y) {
    int doors = 0;
    //East Room
    doors = x + 1 < layout[0].length && layout[y][x + 1] == 1 ? doors + 1 : doors;
    //West Room
    doors = x - 1 >= 0 && layout[y][x - 1] == 1 ? doors + 1 : doors;
    //South Room
    doors = y + 1 < layout.length && layout[y + 1][x] == 1 ? doors + 1 : doors;
    //North Room
    doors = y - 1 >= 0 && layout[y - 1][x] == 1 ? doors + 1 : doors;

    return doors;
  }

}
