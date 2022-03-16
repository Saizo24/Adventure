package adventure.combat;

import adventure.menu.CombatMenu;
import adventure.menu.Menu;
import adventure.menu.TextReader;
import adventure.player.PlayerCharacter;
import adventure.scenery.Room;

import java.util.ArrayList;
import java.util.Random;

public class Encounter {

  private static final String[] ENCOUTER_DESCRIPTION_LIST = TextReader.loadDescription("EncounterDescriptions.txt");

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

  private void showEncounterMessage() {
    System.out.println(ENCOUTER_DESCRIPTION_LIST[random.nextInt(ENCOUTER_DESCRIPTION_LIST.length)]);
    System.out.println("You encounter following enemies:");
    Menu.printEnemies(enemies);
    System.out.println("Prepare for Battle!");
    Menu.pressEnter();
  }

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
