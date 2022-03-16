package adventure.interfaces;

import adventure.player.PlayerCharacter;


public abstract interface UsableItem {
  public static final String[] MAGICAL_PREFIX = {
      "Faint",
      "Shining",
      "Brilliant"
  };

  public static final String[] ITEM_OPTIONS = {
      "Use",
      "Examine"
  };

  public static final int MAGIC_LEVEL_RANGE = 4;

  public abstract String getNAME();
  public abstract int getMAGIC_LEVEL();
  public abstract boolean use(PlayerCharacter playerCharacter);
  public abstract void showDescription();
}
