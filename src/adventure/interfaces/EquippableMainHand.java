package adventure.interfaces;

import adventure.items.Equipment;
import adventure.player.PlayerCharacter;

public abstract interface EquippableMainHand extends UsableItem{

  public abstract boolean equipMainHand(PlayerCharacter player);
  public abstract String getNAME();
  public abstract int getDAMAGE();
  public abstract int getATTR_INDEX();
  public abstract int getMAGIC_LEVEL();
}
