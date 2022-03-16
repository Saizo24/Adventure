package adventure.items;

import adventure.interfaces.EquippableMainHand;
import adventure.interfaces.EquippableOffHand;
import adventure.interfaces.UsableItem;
import adventure.player.PlayerCharacter;

public class Equipment {
  private PlayerCharacter player;
  private EquippableMainHand mainHand = null;
  private EquippableOffHand offHand = null;
  private Armor armor = null;

  public Equipment(PlayerCharacter player) {
    this.player = player;
  }

  public PlayerCharacter getPlayer() {
    return player;
  }

  public EquippableMainHand getMainHand() {
    return mainHand;
  }

  public void setMainHand(EquippableMainHand equippableMainHand) {
    this.mainHand = equippableMainHand;
  }

  public EquippableOffHand getOffHand() {
    return offHand;
  }

  public void setOffHand(EquippableOffHand offHand) {
    this.offHand = offHand;
  }

  public Armor getArmor() {
    return armor;
  }

  public void setArmor(Armor armor) {
    this.armor = armor;
  }

  public void unequipMainHand() {
    if (mainHand != null) {
      System.out.println(mainHand.getNAME() + " is going back into your bag.");
      inventory.add((UsableItem) mainHand);
      mainHand = null;
    }
  }

  public void unequipOffHand() {
    if (offHand != null) {
      System.out.println(offHand.getNAME() + " is going back into your bag.");
      inventory.add((UsableItem) offHand);
      offHand = null;
    }
  }

  public void unequipArmor() {
    if (offHand != null) {
      System.out.println(armor.getNAME() + " is going back into your bag.");
      inventory.add(armor);
      armor = null;
    }
  }
}
