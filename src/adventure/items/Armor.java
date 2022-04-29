package adventure.items;

import adventure.interfaces.UsableItem;
import adventure.player.PlayerCharacter;

import java.util.Random;

/**
 * This class contains all method for creating main armor for the player. Armor are split
 * in three categories: light, medium and heavy armor. A player can only equip one armor at
 * any time and only of the category matching his class.
 *
 * Armor can be magical and add their magic level to the armor class.
 *
 * Armor can be randomly generated or custom made.
 */
public class Armor implements UsableItem {

  public static final String[][] ARMOR_TYPES = {
      {"Cloth"},                              // Light armor
      {"Leather", "Chain"},                   // Medium armor
      {"Light Plate", "Scale", "Plate"}       // Heavy armor
  };

  private final String NAME;
  private final int ARMOR_BONUS;
  private final int ARMOR_INDEX;
  private final int MAGIC_LEVEL;

  /**
   * This constructor generates a specific armor. Name and armor bonus depends on what kind of
   * armor it is.
   * @param armorType determines the type of armor
   * @param index determines what kind of armor it is
   * @param MAGIC_LEVEL determines the magic level of the armor
   */
  public Armor(int armorType, int index, int MAGIC_LEVEL) {
    this.NAME = ARMOR_TYPES[armorType][index] + " Armor";
    this.ARMOR_BONUS = armorType * 2 + index + 2;
    this.ARMOR_INDEX = armorType;
    this.MAGIC_LEVEL = MAGIC_LEVEL;
  }

  /**
   * This constructor generates a random armor of specific type.
   * @param random random element of generating the armor
   * @param armorType determines the type of armor
   */
  public Armor(Random random, int armorType) {
    int index = random.nextInt(ARMOR_TYPES[armorType].length);
    this.ARMOR_INDEX = armorType;
    this.ARMOR_BONUS = armorType * 2 + index + 2;
    this.MAGIC_LEVEL = random.nextInt(UsableItem.MAGIC_LEVEL_RANGE);
    this.NAME = ARMOR_TYPES[armorType][index] + " Armor" + (MAGIC_LEVEL > 0 ? "+" + MAGIC_LEVEL : "");
  }

  public String getNAME() {
    return NAME;
  }

  public int getARMOR_BONUS() {
    return ARMOR_BONUS + MAGIC_LEVEL;
  }

  @Override
  public int getMAGIC_LEVEL() {
    return MAGIC_LEVEL;
  }

  /**
   * This method lets the player equip the armor. If checks whether the player is allowed to wield this
   * armor, depending on his class.
   * @param playerCharacter the player, the armor should be equipped on
   * @return true, if the armor has been successfully equipped
   */
  public boolean equip(PlayerCharacter playerCharacter) {
    if (ARMOR_INDEX <= playerCharacter.getMaxArmorType()) {
      System.out.println("You put on " + getNAME());
      playerCharacter.setArmor(this);
      playerCharacter.getInventory().remove(this);
      return true;
    } else {
      System.out.println("This armor is too heavy for you.");
      return false;
    }
  }

  @Override
  /**
   * This method unequips the armor currently worn by the player and equips this as the new armor
   */
  public boolean use(PlayerCharacter playerCharacter) {
    if (playerCharacter.getArmor() != null) {
      playerCharacter.unequipArmor();
    }
    equip(playerCharacter);
    return true;
  }

  @Override
  /**
   * This method prints a detailed description of this armor. Description contains type of armor, how high the armor
   * bonus is and how magical it is
   */
  public void showDescription() {
    String[] weightClass = {
        "Light",
        "Medium",
        "Heavy"
    };
    System.out.printf("It's a %s Armor. It gives you a bonus of +%d to your armor class.", weightClass[this.ARMOR_INDEX], this.getARMOR_BONUS());
    System.out.println(this.getMAGIC_LEVEL() > 0 ? " It seems to be magical and has a " + UsableItem.MAGICAL_PREFIX[this.getMAGIC_LEVEL()-1] + " Aura." : "It doesn't seem to be magical.");
  }

  /**
   * This method is used for the status printout of the character and prints the armor bonus
   */
  public void showStats() {
    System.out.printf("Armor Bonus: +%d\n", this.getARMOR_BONUS());
  }
}
