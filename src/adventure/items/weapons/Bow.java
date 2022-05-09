package adventure.items.weapons;

import adventure.items.Quiver;
import adventure.player.PlayerCharacter;

/**
 * This class describes a weapon the type Bow. The bow is a special kind of weapon, as it requires a quiver
 * to attack.
 */
public class Bow extends Weapon {
  public static final String[] BOW_LIST = {
    "Shortbow",
    "Longbow",
    "Composite Bow",
    "War Bow"
  };

  public Bow (int nameIndex, int baseDamage, int magicLevel) {
    super(Weapon.generateName(nameIndex, BOW_LIST, magicLevel), 1, baseDamage + nameIndex * 2, magicLevel, true);
  }

  @Override
  public boolean use(PlayerCharacter playerCharacter) {
    return equipMainHand(playerCharacter);
  }

  @Override
  public boolean equipMainHand(PlayerCharacter playerCharacter) {
    playerCharacter.unequipOffHand();
    return super.equipMainHand(playerCharacter);
  }

  private boolean checkForArrows(PlayerCharacter playerCharacter) {
    return playerCharacter.getOffHand() instanceof Quiver;
  }
}
