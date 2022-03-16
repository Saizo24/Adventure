package adventure.campaign;

import adventure.player.PlayerCharacter;
import adventure.scenery.Room;

import java.util.ArrayList;

public class Campaign {

  public int[][] layout;
  protected PlayerCharacter player;

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

  public Room searchForRoom(int x, int y) {
    for (Room room : dungeon) {
      if (room.X == x && room.Y == y) {
        return room;
      }
    }
    return null;
  }

  public int[] lastRoomNumber(int amount) {
    if (roomTracker.size() > 1) {
      return roomTracker.get(roomTracker.size() - amount - 1);
    } else {
      return roomTracker.get(0);
    }
  }
}
