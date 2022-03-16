package adventure.items;

import adventure.interfaces.EquippableOffHand;
import adventure.interfaces.UsableItem;
import adventure.items.weapons.Bow;
import adventure.player.PlayerCharacter;

public class Quiver implements EquippableOffHand {

  private final String NAME = "Quiver";

  private int quiverSize = 30;
  private int arrowCount;

  private PlayerCharacter weilder = null;

  public Quiver(int arrowCount) {
    this.arrowCount = arrowCount;
  }

  @Override
  public int getMAGIC_LEVEL() {
    return 0;
  }

  @Override
  public String getNAME() {
    return NAME;
  }

  @Override
  public boolean equipOffHand(PlayerCharacter player, Equipment equipment) {
    if (equipment.getMainHand() instanceof Bow) {
      equipment.setOffHand(this);
      player.getInventory().remove(this);
      System.out.printf("You equip the quiver. It has %d arrows left.", arrowCount);
      return true;
    } else {
      System.out.println("You can't equip this without a bow.");
      return false;
    }
  }

  @Override
  public boolean use(PlayerCharacter playerCharacter) {
    return false;
  }

  @Override
  public void showDescription() {
    System.out.printf("This is a standard quiver and can hold up to %d arrows. There are %d arrows in it.\n", quiverSize, arrowCount);
  }

  @Override
  public void showStats() {
    System.out.printf("Arrow Count: %d Arrows\n", this.arrowCount);
  }
}
