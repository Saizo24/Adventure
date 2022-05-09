package adventure.items;

import adventure.interfaces.EquippableOffHand;
import adventure.interfaces.UsableItem;
import adventure.items.weapons.Bow;
import adventure.player.PlayerCharacter;

/**
 * This class decsribes the ammunition of bows. The player can't attack with a bow unless his off hand
 * is equipped with a quiver. The player can't equip quivers without equipping a bow first.
 *
 * The player can refill his currently equipped quiver with arrows from a quiver in the player's inventory.
 * As soon as a quiver is empty, it will be removed from the player's equipment and/or inventory.
 *
 * The standard size of the quiver is 30.
 */
public class Quiver implements EquippableOffHand {

  private final String NAME = "Quiver";

  private int quiverSize = 30;
  private int arrowCount;

  private PlayerCharacter weilder = null;

  public Quiver(int arrowCount) {
    this.arrowCount = Math.min(arrowCount, this.quiverSize);
  }

  public int getArrowCount() {
    return arrowCount;
  }

  public void addArrows(int amount) {
    arrowCount += amount;
  }

  public void removeArrows(int amount) {
    arrowCount -= amount;
    if (arrowCount <= 0) {
      this.weilder.setOffHand(null);
    }
  }

  private void refillQuiver(Quiver quiver) {
    int refill = Math.min(quiverSize - arrowCount, quiver.getArrowCount());
    System.out.printf("You refilled your quiver with %d arrow%s.\n", refill, refill == 1 ? "" : "s");
    this.addArrows(refill);
    quiver.removeArrows(refill);
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
  public boolean equipOffHand(PlayerCharacter playerCharacter) {
    if (playerCharacter.getOffHand() instanceof Quiver) {
      refillQuiver((Quiver) playerCharacter.getOffHand());
      return true;
    }
    if (playerCharacter.getMainHand() instanceof Bow && !(playerCharacter.getOffHand() instanceof Quiver)) {
      this.weilder = playerCharacter;
      playerCharacter.unequipOffHand();
      playerCharacter.setOffHand(this);
      playerCharacter.getInventory().remove(this);
      System.out.printf("You equip the quiver. It has %d arrows left.\n", arrowCount);
      return true;
    }
    return false;
  }

  @Override
  public boolean use(PlayerCharacter playerCharacter) {
    boolean hasUsed;

    if (!(playerCharacter.getMainHand() instanceof Bow)) {
      System.out.println("You can't equip this without a bow.");
      return false;
    }
    hasUsed = equipOffHand(playerCharacter);
    if (this.arrowCount <= 0 && playerCharacter.getInventory().contains(this)) {
      playerCharacter.getInventory().remove(this);
    }
    return hasUsed;
  }

  @Override
  public void showDescription() {
    System.out.printf("This is a standard quiver and can hold up to %d arrows. There are %d arrows in it.\n", quiverSize, arrowCount);
  }

  @Override
  public void showStats() {
    System.out.printf("Arrow Count: %d Arrow%s\n", this.arrowCount, this.arrowCount == 1 ? "" : "s");
  }
}
