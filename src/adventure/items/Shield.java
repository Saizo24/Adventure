package adventure.items;

import adventure.interfaces.EquippableOffHand;
import adventure.interfaces.UsableItem;
import adventure.items.weapons.TwoHandMelee;
import adventure.player.PlayerCharacter;

import java.util.Random;

public class Shield implements EquippableOffHand {

  private final String NAME;
  private final int ARMOR_BONUS;
  private final int MAGIC_LEVEL;

  private PlayerCharacter weilder = null;

  public Shield(String NAME, int ARMOR_BONUS, int MAGIC_LEVEL) {
    this.NAME = NAME;
    this.ARMOR_BONUS = ARMOR_BONUS;
    this.MAGIC_LEVEL = MAGIC_LEVEL;
  }

  public Shield(Random random, String NAME) {
    this.ARMOR_BONUS = random.nextInt(3) + 2;
    this.MAGIC_LEVEL = random.nextInt(UsableItem.MAGIC_LEVEL_RANGE);
    this.NAME = NAME + (MAGIC_LEVEL > 0 ? "+" + MAGIC_LEVEL : "");
  }

  @Override
  public String getNAME() {
    return NAME;
  }

  public int getARMOR_BONUS() {
    return ARMOR_BONUS + MAGIC_LEVEL;
  }

  public int getMAGIC_LEVEL() {
    return MAGIC_LEVEL;
  }

  @Override
  public boolean equipOffHand(PlayerCharacter playerCharacter) {
    playerCharacter.setOffHand(this);
    playerCharacter.getInventory().remove(this);
    this.weilder = playerCharacter;
    System.out.println("You strap " + getNAME() + " to your left forearm." );
    return true;
  }

  @Override
  public boolean use(PlayerCharacter playerCharacter) {
    if (playerCharacter.getMainHand() instanceof TwoHandMelee) {
      playerCharacter.unequipMainHand();
    } else if (playerCharacter.getOffHand() != null) {
      playerCharacter.unequipOffHand();
    }
    equipOffHand(playerCharacter);
    return true;
  }

  @Override
  public void showDescription() {
    System.out.printf("This is a %s. It gives you a bonus of +%d to your armor class.", NAME, this.ARMOR_BONUS);
    System.out.println(this.getMAGIC_LEVEL() > 0 ? " It seems to be magical and has a " + UsableItem.MAGICAL_PREFIX[this.getMAGIC_LEVEL()-1] + " Aura." : "It doesn't seem to be magical.");
  }

  public void showStats() {
    System.out.printf("Armor Bonus: +%d\n", this.ARMOR_BONUS);
  }
}
