package adventure.menu;

import adventure.combat.Encounter;
import adventure.combat.Enemy;
import adventure.player.PlayerCharacter;
import adventure.scenery.Room;

import java.util.ArrayList;

public class CombatMenu {

  private final String[] COMBAT_OPTIONS = {
      "Attack",
      "Open Inventory",
      "Show Status",
      "Escape"
  };

  private Encounter encounter;
  private PlayerCharacter player;

  public CombatMenu(PlayerCharacter player, Encounter encounter) {
    this.player = player;
    this.encounter = encounter;
  }

  public boolean openCombatMenu(Room room) {
    boolean fleeing = false;
    boolean hasActed;
    do {
      hasActed = true;
      System.out.println("Health: " + player.getHealthPoints() + " HP");
      System.out.println("Choose an option.");
      Menu.printOptions(COMBAT_OPTIONS, false);
      int action = Menu.inputInt();
      switch (action) {
        case 0 -> player.attack(encounter.getEnemies());
        case 1 -> hasActed = player.openInventory();
        case 2 -> {
          Menu.showPlayerStatus(player);
          hasActed = false;
        }
        case 3 -> fleeing = fleeBattle(room);
      }
    } while (!hasActed);

    return fleeing;
  }

  private boolean fleeBattle(Room room) {
    if (room.CAMPAIGN.getRoomTracker().size() > 1) {
      System.out.println("You have run from the battle.");
      return true;
    } else {
      System.out.println("There is nowhere to run.");
      return false;
    }
  }
}
