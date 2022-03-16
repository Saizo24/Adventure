package adventure.items.weapons;


import adventure.interfaces.EquippableMainHand;
import adventure.player.PlayerCharacter;

public class TwoHandMelee extends Weapon implements EquippableMainHand {

  public static final String[] WARRIOR_TYPES = {
    "Greatsword",
    "Greataxe",
    "Polearm"
  };

  public static final String[] RANGER_TYPES = {
    "Spear",
    "Doublesword"
  };

  public TwoHandMelee(int nameIndex, int attrIndex, int damage, int magicLevel) {
    super(Weapon.generateName(nameIndex, (attrIndex == 0 ? WARRIOR_TYPES : RANGER_TYPES), magicLevel), attrIndex, damage, magicLevel, true);
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
}
