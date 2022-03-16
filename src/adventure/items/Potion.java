package adventure.items;

import adventure.interfaces.UsableItem;
import adventure.player.PlayerCharacter;

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

  public void showDescription() {
    System.out.println("This " + name + " replenishes " + (potency + 1) * 6 + " points of your " + name.split(" ")[1] + ".");
  }
}
