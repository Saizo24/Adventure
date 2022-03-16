package adventure.player;

import adventure.combat.Enemy;
import adventure.interfaces.EquippableMainHand;
import adventure.interfaces.EquippableOffHand;
import adventure.interfaces.UsableItem;
import adventure.items.*;
import adventure.menu.Menu;

import java.util.ArrayList;

public class PlayerCharacter {

  public static final String[] ATTRIBUTE_NAMES = {
      "Strength",
      "Dexterity",
      "Intelligence"
  };

  private final String NAME;
  private final String RACE;
  private final String GENDER;
  public final int CLASS_INDEX;
  protected int experiencePoints = 0;
  protected int level = 1;

  protected Equipment equipment = new Equipment(this);
  protected ArrayList<UsableItem> inventory = new ArrayList<>();
  protected int goldCoins = 0;

  protected int[] attributes = new int[3];

  protected int age;

  protected int maxArmorType;
  protected int healthPoints = 0;
  protected int maxHealthPoints = 0;

  public PlayerCharacter(String NAME, String RACE, String GENDER, int CLASS_INDEX) {
    this.NAME = NAME;
    this.RACE = RACE;
    this.GENDER = GENDER;
    this.CLASS_INDEX = CLASS_INDEX;
  }

  public String getNAME() {
    return NAME;
  }

  public String getRACE() {
    return RACE;
  }

  public String getGENDER() {
    return GENDER;
  }

  public int getCLASS_INDEX() {
    return CLASS_INDEX;
  }

  public int getExperiencePoints() {
    return experiencePoints;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public Equipment getEquipment() {
    return equipment;
  }

  public ArrayList getInventory() {
    return this.inventory;
  }

  public int[] getAttributes() {
    return attributes;
  }

  public int getStrength() {
    return attributes[0];
  }

  public void setStrength(int strength) {
    this.attributes[0] = strength;
  }

  public int getDexterity() {
    return attributes[1];
  }

  public void setDexterity(int dexterity) {
    this.attributes[1] = dexterity;
  }

  public int getIntelligence() {
    return attributes[2];
  }

  public void setIntelligence(int intelligence) {
    this.attributes[2] = intelligence;
  }

  public int getMaxArmorType() {
    return maxArmorType;
  }

  public void setMaxArmorType(int maxArmorType) {
    this.maxArmorType = maxArmorType;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public int getHealthPoints() {
    return healthPoints;
  }

  public void setHealthPoints(int healthPoints) {
    this.healthPoints = healthPoints;
  }

  public int getMaxHealthPoints() {
    return maxHealthPoints;
  }

  public void setMaxHealthPoints(int maxHealthPoints) {
    this.maxHealthPoints = maxHealthPoints;
  }


  /**
   * Class Methods ----------------------------------------------------------------------------------------------------
   *
   */

  public void attack(ArrayList<Enemy> enemies) {
    System.out.println("You swing with your fists at your enemy and do " + (1 + this.getStrength()) / 2 + " damage");
    chooseEnemy(enemies).takeDamage(1 + this.getStrength());
  }

  public void regenerateHealth(int amount) {
    healthPoints += amount;
  }

  public void regenerateMana(int amount) {

  }

  protected Enemy chooseEnemy(ArrayList<Enemy> enemies) {
    int target = 0;
    if (enemies.size() == 1) {
      return enemies.get(target);
    } else {
      do {
        System.out.println("Choose a target:");
        Menu.printEnemies(enemies);
        target = Menu.inputInt();
        try {
          return enemies.get(target);
        } catch (IndexOutOfBoundsException e) {
          System.out.println(Menu.INPUT_INVALID);
        }
      } while (target < 0);
    }
    return null;
  }

  public void setStartingAttributes(int str, int dex, int in, int hp) {
    setStrength(str);
    setDexterity(dex);
    setIntelligence(in);
    setHealthPoints(hp);
    setMaxHealthPoints(hp);
  }

  public boolean openInventory() {
    System.out.println("Gold Coins: " + goldCoins);
    showInventory();
    if (!inventory.isEmpty()) {
      System.out.println("Which item do you want to use?");
      int index = Menu.inputInt();
      if (index == 0) {
        return false;
      } else if (index <= inventory.size() && index > 0) {
        return useItem(index);
      } else {
        System.out.println(Menu.INPUT_INVALID);
        return false;
      }
    } else {
      System.out.println("Your bag is empty.");
      return false;
    }
  }

  private boolean useItem(int index) {
    UsableItem item = inventory.get(index - 1);
    do {
      System.out.println("What would you like to do with that item?");
      Menu.printOptions(UsableItem.ITEM_OPTIONS, true);
      int action = Menu.inputInt();
      switch (action) {
        case 0 -> { return false; }
        case 1 -> {
          item.use(this);
          return true;
        }
        case 2 -> {
          item.showDescription();
          Menu.pressEnter();
          return false;
        }
        default -> System.out.println(Menu.INPUT_INVALID);
      }
    } while (true);
  }

  private void showInventory() {

    for (int i = 0; i < inventory.size(); i++) {
      System.out.println((i + 1) + " - " + inventory.get(i).getNAME());
    }
    System.out.println("0 - Close Inventory");
  }

  public int calculateArmorClass() {
    int armorClass = 10 + attributes[1] / 2 + (equipment.getArmor() != null ? equipment.getArmor().getARMOR_BONUS() : 0) + (equipment.getOffHand() instanceof Shield ? ((Shield) equipment.getOffHand()).getARMOR_BONUS() : 0);
    return armorClass;
  }

  public void addGoldCoins(int amount) {
    goldCoins += amount;
  }

  public void addExperience(int amount) {
    this.experiencePoints += amount;
  }

  public void levelUp() {
    level++;
    System.out.println("You have leveled up. You're now Level " + level + ". Your stats have been increased.");
  }
}
