package adventure.interfaces;

import adventure.player.PlayerCharacter;

public abstract interface EquippableOffHand extends UsableItem {

  public abstract boolean equipOffHand(PlayerCharacter playerCharacter);
  public abstract void showStats();
}
