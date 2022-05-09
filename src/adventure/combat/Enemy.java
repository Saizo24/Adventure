package adventure.combat;

import adventure.menu.Menu;
import adventure.player.PlayerCharacter;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class decribes an enemy, the main antagonistic characters in this game. You can generate generic enemies of
 * the type goblin, orc, skeleton or zombie. You can also generate a complete custom enemy.
 *
 * Enemies have a similiar stat block to the players, only simpler. After defeat, they will grant the player a specific
 * amount of experience.
 */
public class Enemy {

  private final String name;
  private final int armorClass;
  private int healthpoints;
  private int strength;
  private int damageDice;
  private int attacks;

  private int initiative = 0;
  private int experiencePoints = 0;

  private static Random random = new Random();

  public Enemy(String name, int armorClass, int healthpoints, int strength, int damageDice, int attacks) {
    this.name = name;
    this.armorClass = armorClass;
    this.healthpoints = healthpoints;
    this.strength = strength;
    this.damageDice = damageDice;
    this.attacks = attacks;
  }

  public String getName() {
    return name;
  }

  public int getArmorClass() {
    return armorClass;
  }

  public int getHealthpoints() {
    return healthpoints;
  }

  public int getExperiencePoints() {
    return experiencePoints;
  }

  public void setExperiencePoints(int experiencePoints) {
    this.experiencePoints = experiencePoints;
  }

  /**
   * With this method, the enemy attacks a specific player. An attack start with an attack roll. If the attack roll
   * is equal or higher than the armor class of the defender, the attack is successful. An attack roll consists of
   * a rolling a 20-sided dice and adding the strength stat to it.
   * @param playerCharacter to be attacked player target
   */
  public void attack(PlayerCharacter playerCharacter) {;
    for (int i = 0; i < attacks; i++) {
      int diceRoll = random.nextInt(20) + 1;

      System.out.printf(Menu.MELEE_ATTACK_LIST[random.nextInt(Menu.MELEE_ATTACK_LIST.length)], this.name,"s", "you", (i > 0 ? "again" : ""));
      Menu.playLoadingAnimation(500);
      calculateHit(playerCharacter, diceRoll);
    }
    Menu.pressEnter();
  }

  /**
   * This method checks the attack roll is equal or higher than the armor class of the defender. The outcome will
   * be printed. If the dice roll is a 20, it is considered a critical hit and will automatically be a successful
   * attack.
   * @param playerCharacter to be attacked player target
   * @param diceRoll result of a 20-sided dice roll
   */
  private void calculateHit(PlayerCharacter playerCharacter, int diceRoll) {

    if (diceRoll + strength >= playerCharacter.calculateArmorClass() || diceRoll == 20) {
      // regular or critical hit
      int damageRoll = rollDamage(diceRoll == 20);
      playerCharacter.regenerateHealth(-damageRoll);
    } else if (diceRoll == 1) {
      // Critical miss
      System.out.printf(Menu.MISS_LIST[Menu.MISS_LIST.length - 1], "s");
    } else {
      // regular miss
      System.out.printf(Menu.MISS_LIST[random.nextInt(Menu.MISS_LIST.length - 1)], "you", "");
    }
    System.out.printf("\n(Hit Roll was %d + %d)\n", diceRoll, strength);
  }

  /**
   * This method rolls the damage done of a successful attack. It rolls a dice the size of the damage stat and adds
   * the strength stat to it. If the attack was critical, the damage stat will be added to the total damage.
   * @param isCritical
   * @return the total damage inflicted
   */
  private int rollDamage(boolean isCritical) {
    int damageRoll = random.nextInt(damageDice) + 1;
    int totalDamage = damageRoll + strength + (isCritical ? damageDice : 0);
    System.out.printf((isCritical ? Menu.HIT_LIST[Menu.HIT_LIST.length - 1] : Menu.HIT_LIST[random.nextInt(Menu.HIT_LIST.length - 1)]) + "(Damage: %d + %d%s)", "s", totalDamage, damageRoll, strength, isCritical ? " + " + damageDice : "");
    return totalDamage;
  }

  /**
   * This method reduces the health points of the enemy by a specific amount
   * @param damage amount of damage taken
   */
  public void takeDamage(int damage) {
    healthpoints -= damage;
  }

  /**
   * Generates a generic enemy of the type orc. He's a strong damage dealer with two attacks per round.
   * @param number determines the number of the orc
   * @param level determines the level of the orc
   * @return an enemy with the stat block of a generic orc
   */
  public static Enemy generateOrc(int number, int level) {
    Enemy orc = new Enemy("Orc Nr." + number, 14 + level/2, 20 + 5 * level, 4 + level/2, 8, 2);
    orc.setExperiencePoints(200);
    return orc;
  }

  /**
   * Generates a generic enemy of the type goblin. He's weak and only dangerous when he's part of a bigger pack.
   * @param number determines the number of the orc
   * @param level determines the level of the orc
   * @return an enemy with the stat block of a generic goblin
   */
  public static Enemy generateGoblin(int number, int level) {
    Enemy goblin = new Enemy("Goblin Nr." + number, 12 + level/2, 10 + 2 * level, 2 + level/4, 6, 1);
    goblin.setExperiencePoints(100);
    return goblin;
  }

  /**
   * Generates a generic enemy of the type skeleton. He's of medium difficulty with higher armor class than others
   * @param number determines the number of the skeleton
   * @param level determines the level of the orc
   * @return an enemy with the stat block of a generic skeleton
   */
  public static Enemy generateSkeleton(int number, int level) {
    Enemy skeleton = new Enemy("Skeleton Nr." + number, 16 + level, 15 + 5 * level, 3 + level/2, 8, 1);
    skeleton.setExperiencePoints(150);
    return skeleton;
  }

  /**
   * Generates a generic enemy of the type zombie. He's of medium difficulty with high health point.
   * @param number determines the number of zombie
   * @param level determines the level of the zombie
   * @return an enemy with the stat block of a generic zombie
   */
  public static Enemy generateZombie(int number, int level) {
    Enemy zombie = new Enemy("Zombie Nr." + number, 12, 35 + 10 * level, 3 + level/2, 6, 1);
    zombie.setExperiencePoints(150);
    return zombie;
  }

  /**
   * Generates a pack of orcs. They are generated in packs of two to three orcs.
   * @param level determines the level of the orcs
   * @return an arraylist of enemies with the stat block of orcs
   */
  public static ArrayList<Enemy> generateOrcPack(int level) {
    ArrayList<Enemy> orcPack = new ArrayList<>();
    int enemyCount = random.nextInt(2) + 2;
    for (int i = 0; i < enemyCount; i++) {
      Enemy orc = generateOrc(i, level);
      orcPack.add(orc);
    }
    return orcPack;
  }

  /**
   * Generates a pack of goblins. They are generated in packs of four to six goblins
   * @param level determines the level of the goblins
   * @return an arraylist of enemies with the stat block of goblins
   */
  public static ArrayList<Enemy> generateGoblinPack(int level) {
    ArrayList<Enemy> goblinPack = new ArrayList<>();
    int enemyCount = random.nextInt(3) + 4;
    for (int i = 0; 1 < enemyCount; i++) {
      Enemy goblin = generateGoblin(i, level);
      goblinPack.add(goblin);
    }
    return goblinPack;
  }

  /**
   * Generates a pack of undead. They are generated in packs of two to three undead. They can
   * be of type skeleton or zombie.
   * @param level determines the level of the undead
   * @return an arraylist of enemies with the stat block of skeletons or zombies
   */
  public static ArrayList<Enemy> generateUndeadPack(int level) {
    ArrayList<Enemy> undeadPack = new ArrayList<>();
    int enemyCount = random.nextInt(2) + 2;
    for (int i = 0; i < enemyCount; i++) {
      Enemy undead;
      if (random.nextInt(100) % 2 == 0) {
        undead = generateSkeleton(i, level);
      } else {
        undead = generateZombie(i, level);
      }
      undeadPack.add(undead);
    }
    return undeadPack;
  }
}
