package adventure.campaign;

import adventure.player.PlayerCharacter;
import adventure.scenery.Room;

import java.util.ArrayList;

/**
 * This super class contains the layout and different sceneries of the campaign. All playable campaign inherit from
 * this class.
 */
public class Campaign {

  protected PlayerCharacter player;

  //Fields for layout and different rooms or sceneries of the campaign
  public int[][] layout;
  protected ArrayList<Room> dungeon = new ArrayList<>();
  protected ArrayList<int[]> roomTracker = new ArrayList<>();

  public Campaign() {

  }

  public Campaign(PlayerCharacter player) {
    this.player = player;
  }

  public ArrayList<Room> getDungeon() {
    return dungeon;
  }

  public ArrayList<int[]> getRoomTracker() {
    return roomTracker;
  }

  public PlayerCharacter getPlayer() {
    return player;
  }

  /**
   * Searches campaign for the room with corresponding coordinates.
   * @param x contains the x-coordinate
   * @param y contains the y-coordinate
   * @return returns the room if found. Otherwise will return null
   */
  public Room searchForRoom(int x, int y) {
    for (Room room : dungeon) {
      if (room.X == x && room.Y == y) {
        return room;
      }
    }
    return null;
  }

  /**
   * Returns the coordinates of the last visited room by a specific amount
   * @param amount contains the amount of how many steps to go back in the tracker
   * @return returns the coordinate of the visited room by so many steps. If the steps foes further
   * than the first room, it returns the coordinates of the first room
   */
  public int[] lastRoomNumber(int amount) {
    if (roomTracker.size() > 1) {
      return roomTracker.get(roomTracker.size() - amount - 1);
    } else {
      return roomTracker.get(0);
    }
  }
}
