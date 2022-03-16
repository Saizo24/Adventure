package adventure.combat;

import adventure.menu.Menu;
import adventure.player.PlayerCharacter;

import java.util.ArrayList;
import java.util.Random;

public class Enemy {

  private final String name;
  private final int armorClass;
  private int healthpoints;
  private int strength;
  private int damage;
  private int attacks;

  private int initiative = 0;
  private int experiencePoints = 0;

  private static Random random = new Random();

  public Enemy(String name, int armorClass, int healthpoints, int strength, int damage, int attacks) {
    this.name = name;
    this.armorClass = armorClass;
    this.healthpoints = healthpoints;
    this.strength = strength;
    this.damage = damage;
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

  public void attack(PlayerCharacter playerCharacter) {;
    for (int i = 0; i < attacks; i++) {
      int diceRoll = random.nextInt(20) + 1;
      System.out.printf(Menu.MELEE_ATTACK_LIST[random.nextInt(Menu.MELEE_ATTACK_LIST.length)], this.name,"s", "you", (i > 0 ? "again" : ""));
      Menu.playLoadingAnimation(500);
      calculateHit(playerCharacter, diceRoll);
    }
    Menu.pressEnter();
  }

  private void calculateHit(PlayerCharacter playerCharacter, int diceRoll) {

    if (diceRoll + strength >= playerCharacter.calculateArmorClass()) {
      int damageRoll = rollDamage(diceRoll == 20);
      playerCharacter.regenerateHealth(-damageRoll);
    } else if (diceRoll == 1) {
      System.out.printf(Menu.MISS_LIST[Menu.MISS_LIST.length - 1], "s");
    } else {
      System.out.printf(Menu.MISS_LIST[random.nextInt(Menu.MISS_LIST.length - 1)], "you", "");
    }
    System.out.printf("\n(Hit Roll was %d + %d)\n", diceRoll, strength);
  }

  private int rollDamage(boolean isCritical) {
    int damageRoll = random.nextInt(damage) + 1;
    int totalDamage = damageRoll + strength + (isCritical ? damage : 0);
    System.out.printf((isCritical ? Menu.HIT_LIST[Menu.HIT_LIST.length - 1] : Menu.HIT_LIST[random.nextInt(Menu.HIT_LIST.length - 1)]) + "(Damage: %d + %d%s)", "s", totalDamage, damageRoll, strength, isCritical ? " + " + damage : "");
    return totalDamage;
  }

  public void takeDamage(int damage) {
    healthpoints -= damage;
  }

  public static Enemy generateOrc(int number, int level) {
    Enemy orc = new Enemy("Orc Nr." + number, 14 + level/2, 20 + 5 * level, 4 + level/2, 8, 2);
    orc.setExperiencePoints(200);
    return orc;
  }

  public static Enemy generateGoblin(int number, int level) {
    Enemy goblin = new Enemy("Goblin Nr." + number, 12 + level/2, 10 + 2 * level, 2 + level/4, 6, 1);
    goblin.setExperiencePoints(100);
    return goblin;
  }

  public static Enemy generateSkeleton(int number, int level) {
    Enemy skeleton = new Enemy("Skeleton Nr." + number, 16 + level, 15 + 5 * level, 3 + level/2, 8, 1);
    skeleton.setExperiencePoints(150);
    return skeleton;
  }

  public static Enemy generateZombie(int number, int level) {
    Enemy zombie = new Enemy("Zombie Nr." + number, 12, 35 + 10 * level, 3 + level/2, 6, 1);
    zombie.setExperiencePoints(150);
    return zombie;
  }

  public static ArrayList<Enemy> generateOrcPack(int level) {
    ArrayList<Enemy> orcPack = new ArrayList<>();
    int enemyCount = random.nextInt(2) + 2;
    for (int i = 0; i < enemyCount; i++) {
      Enemy orc = generateOrc(i, level);
      orcPack.add(orc);
    }
    return orcPack;
  }

  public static ArrayList<Enemy> generateGoblinPack(int level) {
    ArrayList<Enemy> goblinPack = new ArrayList<>();
    int enemyCount = random.nextInt(3) + 4;
    for (int i = 0; 1 < enemyCount; i++) {
      Enemy goblin = generateGoblin(i, level);
      goblinPack.add(goblin);
    }
    return goblinPack;
  }

  public static ArrayList<Enemy> generateUndeadPack(int level) {
    ArrayList<Enemy> undeadPack = new ArrayList<>();
    int enemyCount = random.nextInt(2);
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
