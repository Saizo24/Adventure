package adventure.items;

import adventure.interfaces.UsableItem;
import adventure.player.PlayerCharacter;

/**
 * This class contains all methods concerning potions. Potions are consumable items for the player,
 * regenerating either his life or his mana. They come in different potencies, which determine, how
 * many health or mana points they heal.
 *
 * The name depends on the type and potency of the potion and is the first
 */
public class Potion implements UsableItem {
  public static final String[] TYPES = {
      "Health",
      "Mana"
  };

  public static final String[] POTENCY = {
      "Minor ",
      "Small ",
      "Medium ",
      "Strong "
  };

  private String name;
  private int type;
  private int potency;

  public Potion(int type, int potency) {
      this.name = POTENCY[potency] + TYPES[type] + " Potion";
      this.type = type;
      this.potency = potency;
  }

  @Override
  public String getNAME() {
    return name;
  }

  @Override
  public int getMAGIC_LEVEL() {
    return potency;
  }

  @Override
  /**
   * This method heals the player's health or mana, depending on the type of potion used. It only heals
   * up to the player's maximum health. The potion is then removed from the inventory.
   */
  public boolean use(PlayerCharacter playerCharacter) {
    int missingHP = playerCharacter.getMaxHealthPoints() - playerCharacter.getHealthPoints();
    int heal = (Math.min(missingHP, (potency + 1) * 6));
    System.out.println("You drink the potion and heal your " + TYPES[type] + " by " + heal+ " points.");
    if (this.type == 0) {
      playerCharacter.regenerateHealth(heal);
    } else {
      playerCharacter.regenerateMana(heal);
    }
    playerCharacter.getInventory().remove(this);
    return true;
  }

  /**
   * Shows a detailed description of the potion. It will print the name and how much it heals.
   */
  public void showDescription() {
    System.out.println("This " + name + " replenishes " + (potency + 1) * 6 + " points of your " + name.split(" ")[1] + ".");
  }
}
