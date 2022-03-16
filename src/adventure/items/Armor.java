package adventure.items;

import adventure.interfaces.UsableItem;
import adventure.player.PlayerCharacter;

import java.util.Random;

public class Armor implements UsableItem {

  public static final String[][] ARMOR_TYPES = {
      {"Cloth"},
      {"Leather", "Chain"},
      {"Light Plate", "Scale", "Plate"}
  };

  private final String NAME;
  private final int ARMOR_BONUS;
  private final int ARMOR_INDEX;
  private final int MAGIC_LEVEL;

  public Armor(int maxArmorType, int index, int MAGIC_LEVEL) {
    this.NAME = ARMOR_TYPES[maxArmorType][index] + " Armor";
    this.ARMOR_BONUS = maxArmorType * 2 + index + 2;
    this.ARMOR_INDEX = maxArmorType;
    this.MAGIC_LEVEL = MAGIC_LEVEL;
  }

  public Armor(Random random, int maxArmorType) {
    int index = random.nextInt(ARMOR_TYPES[maxArmorType].length);
    this.ARMOR_INDEX = maxArmorType;
    this.ARMOR_BONUS = maxArmorType * 2 + index + 2;
    this.MAGIC_LEVEL = random.nextInt(UsableItem.MAGIC_LEVEL_RANGE);
    this.NAME = ARMOR_TYPES[maxArmorType][index] + " Armor" + (MAGIC_LEVEL > 0 ? "+" + MAGIC_LEVEL : "");
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
  public boolean use(PlayerCharacter playerCharacter) {
    if (playerCharacter.getArmor() != null) {
      playerCharacter.unequipArmor();
    }
    equip(playerCharacter);
    return true;
  }

  @Override
  public void showDescription() {
    String[] weightClass = {
        "Light",
        "Medium",
        "Heavy"
    };
    System.out.printf("It's a %s Armor. It gives you a bonus of +%d to your armor class.", weightClass[this.ARMOR_INDEX], this.getARMOR_BONUS());
    System.out.println(this.getMAGIC_LEVEL() > 0 ? " It seems to be magical and has a " + UsableItem.MAGICAL_PREFIX[this.getMAGIC_LEVEL()-1] + " Aura." : "It doesn't seem to be magical.");
  }

  public void showStats() {
    System.out.printf("Armor Bonus: +%d\n", this.getARMOR_BONUS());
  }
}
