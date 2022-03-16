package adventure.items.weapons;

import adventure.interfaces.EquippableOffHand;
import adventure.menu.Menu;
import adventure.player.PlayerCharacter;

public class OnehandMelee extends Weapon implements EquippableOffHand {
  public static final String[] WARRIOR_TYPES = {
      "Longword",
      "Warhammer",
      "Axe"
  };

  public static final String[] RANGER_TYPES = {
      "Dagger",
      "Short Sword",
      "Rapier",
      "Scimitar"
  };

  public static final String[] OPTIONS = {
      "Main Hand",
      "Off Hand"
  };

  public OnehandMelee(int nameIndex, int attrIndex, int damage, int magicLevel) {
    super(Weapon.generateName(nameIndex, (attrIndex == 0 ? WARRIOR_TYPES : RANGER_TYPES), magicLevel), attrIndex, damage - attrIndex * 2, magicLevel, false);
  }

  @Override
  public boolean equipOffHand(PlayerCharacter playerCharacter) {
    if(((Weapon) playerCharacter.getMainHand()).isIS_TWOHANDED()) {
      playerCharacter.unequipMainHand();
    } else if (playerCharacter.getOffHand() != null) {
      playerCharacter.unequipOffHand();
    }
    System.out.println("You put " + this.NAME + " into your second hand.");
    playerCharacter.setOffHand(this);
    playerCharacter.getInventory().remove(this);
    this.isMainHand = false;
    return true;
  }

  @Override
  public boolean use(PlayerCharacter playerCharacter) {
    if (playerCharacter.getMainHand() instanceof OnehandMelee) {
      return chooseHand(playerCharacter);
    } else {
     return equipMainHand(playerCharacter);
    }
  }

  private boolean chooseHand(PlayerCharacter playerCharacter) {
    System.out.println("Where would you like to put it?");
    Menu.printOptions(OPTIONS, true);
    int action = Menu.inputInt();
    switch (action) {
      case 0 -> {
        System.out.println("You put the " + this.NAME + "back into your bag.");
        return false;
      }
      case 1 -> {
        return equipMainHand(playerCharacter);
      }
      case 2 -> {
        return equipOffHand(playerCharacter);
      }
      default -> { return false; }
    }
  }

  @Override
  public void showDescription() {
    super.showDescription();
  }

  @Override
  public void showStats() {
    System.out.printf("Damage: 1-%d +%d", this.DAMAGE, this.wielder.getAttributes()[this.ATTR_INDEX] / 2);
    System.out.println("\nAtk. Bonus: +" + this.wielder.getAttributes()[this.ATTR_INDEX]);
  }
}

