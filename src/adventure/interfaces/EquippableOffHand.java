package adventure.interfaces;

import adventure.items.Equipment;
import adventure.player.PlayerCharacter;

public abstract interface EquippableOffHand extends UsableItem {

  public abstract boolean equipOffHand(PlayerCharacter player);
  public abstract String getNAME();
  public abstract void showStats();
}
