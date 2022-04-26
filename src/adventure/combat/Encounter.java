package adventure.combat;

import adventure.menu.CombatMenu;
import adventure.menu.Menu;
import adventure.menu.TextReader;
import adventure.player.PlayerCharacter;
import adventure.scenery.Room;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class contains all methods about playing encounters. This include generating and battling enemies. Each encounter
 * corresponds to specific room.
 */
public class Encounter {

  private static final String[] ENCOUTER_DESCRIPTION_LISTPATH = TextReader.loadDescription("EncounterDescriptions.txt");

  private static Random random = new Random();

  private Room room;
  private ArrayList<Enemy> enemies = new ArrayList<>();

  private PlayerCharacter player;

  public Encounter(Room room) {
    this.room = room;
  }

  public ArrayList<Enemy> getEnemies() {
    return enemies;
  }

  /**
   * This method lets the user play an encounter by battling the enemies. It opens a combat menu for the player.
   * Enemies will only attack the player. After each combat, the player will be checked if he's leveling up.
   * @param player
   * @return
   */
  public boolean playEncounter(PlayerCharacter player) {
    this.player = player;
    showEncounterMessage();
    CombatMenu combatMenu = new CombatMenu(this.player, this);
    boolean fleeing = false;
    do {
      fleeing = combatMenu.openCombatMenu(room);
      if (fleeing) {
        break;
      }
      for (Enemy enemy : getEnemies()) {
        enemy.attack(player);
      }
    } while (!getEnemies().isEmpty());
    this.player.levelUp();
    return fleeing;
  }

  /**
   * This method prints the encountered enemies
   */
  private void showEncounterMessage() {
    System.out.println(ENCOUTER_DESCRIPTION_LISTPATH[random.nextInt(ENCOUTER_DESCRIPTION_LISTPATH.length)]);
    System.out.println("You encounter following enemies:");
    Menu.printEnemies(enemies);
    System.out.println("Prepare for Battle!");
    Menu.pressEnter();
  }

  /**
   * This method checks the list of enemies for enemies with 0 or less life points and removes them from the list.
   * It then awards the player with the corresponding experience points. If the enemy list is empty, a message will
   * be printed.
   * @param enemies contains the list of enemies in the encounter
   * @param player contains the current player
   */
  public static void removeDeadEnemies(ArrayList<Enemy> enemies, PlayerCharacter player) {
    for (int i = enemies.size() - 1; i >= 0; i--) {
      if (enemies.get(i).getHealthpoints() <= 0) {
        System.out.println(enemies.get(i).getName() + " succumbs to its wounds.");
        player.addExperience(enemies.get(i).getExperiencePoints());
        enemies.remove(i);
      }
    }
    if (enemies.isEmpty()) {
      System.out.println("Your enemies lay dead at your feet. They surely won't be the last.");
    }
  }

  /**
   * This method generates a specific amount of random enemies of a specific level and adds them to this encounter
   * @param number determines the number of enemies to be created
   * @param level determines the level of the enemies
   */
  public void generateEnemies(int number, int level) {
    for (int i = 0; i < number; i++) {
      Enemy enemy = generateRandomEnemy(i, level);
      enemies.add(enemy);
    }
  }

  public Enemy generateRandomEnemy(int number, int level) {
    Enemy enemy;
    int enemyType = random.nextInt(4);

    switch (enemyType) {
      case 0 -> enemy = Enemy.generateOrc(number, level);
      case 1 -> enemy = Enemy.generateGoblin(number, level);
      case 2 -> enemy = Enemy.generateSkeleton(number, level);
      case 3 -> enemy = Enemy.generateZombie(number, level);
      default -> enemy = Enemy.generateGoblin(number, level);
    }

    return enemy;
  }
}
