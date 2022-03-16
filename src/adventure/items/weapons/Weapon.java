package adventure.items.weapons;

import adventure.interfaces.UsableItem;
import adventure.interfaces.EquippableMainHand;
import adventure.player.PlayerCharacter;

import java.util.Random;

public class Weapon implements EquippableMainHand {

  private static Random random = new Random();

  protected final String NAME;
  protected final int DAMAGE;
  protected final int ATTR_INDEX;
  protected final int MAGIC_LEVEL;
  protected final boolean IS_TWOHANDED;
  protected boolean isMainHand = true;

  protected static final int ONE_HANDED_BASE_DAMAGE = 6;
  protected static final int TWO_HANDED_BASE_DAMAGE = 10;

  protected PlayerCharacter wielder = null;

  public Weapon(String name, int attrIndex, int damage, int magicLevel, boolean isTwoHanded) {
    this.NAME = name;
    this.IS_TWOHANDED = isTwoHanded;
    this.ATTR_INDEX = attrIndex;
    this.DAMAGE = damage;
    this.MAGIC_LEVEL = magicLevel;
  }

  @Override
  public String getNAME() {
    return NAME;
  }

  public int getATTR_INDEX() {
    return ATTR_INDEX;
  }

  public int getDAMAGE() {
    return DAMAGE;
  }

  public int getMAGIC_LEVEL() {
    return MAGIC_LEVEL;
  }

  public boolean isIS_TWOHANDED() {
    return IS_TWOHANDED;
  }

  public void setWielder(PlayerCharacter wielder) {
    this.wielder = wielder;
  }

  public static String generateName(int nameIndex, String[] nameList, int magicLevel) {
    return nameList[nameIndex] + (magicLevel > 0 ? "+" + magicLevel : "");
  }

  public static Weapon generateRandomWeapon(int classIndex) {
    Weapon newWeapon;
    switch (classIndex) {
      case 0 -> newWeapon = generateRandomWarriorWeapon(random.nextInt(2));
      case 1 -> newWeapon = generateRandomRangerWeapon(random.nextInt(4));
      default -> newWeapon = new OnehandMelee(0, classIndex, 10, 0);
    }

    return newWeapon;
  }

  private static Weapon generateRandomWarriorWeapon(int weaponType) {
    return weaponType == 0 ? generateRandomTwoHanded(0) : generateRandomOneHanded(0);
  }

  private static Weapon generateRandomRangerWeapon(int weaponType) {
    Weapon newWeapon;
    switch (weaponType) {
      case 0 -> newWeapon = generateRandomOneHanded(1);
      case 1 -> newWeapon = generateRandomTwoHanded(1);
      default -> newWeapon = generateRandomBow();
    }

    return newWeapon;
  }

  private static TwoHandMelee generateRandomTwoHanded(int classIndex) {
    return new TwoHandMelee(
        random.nextInt((classIndex == 0 ? OnehandMelee.WARRIOR_TYPES : OnehandMelee.RANGER_TYPES).length),
        0,
        random.nextInt(3) + TWO_HANDED_BASE_DAMAGE,
        random.nextInt(MAGIC_LEVEL_RANGE)
    );
  }

  private static OnehandMelee generateRandomOneHanded(int classIndex) {
    return new OnehandMelee(
        random.nextInt((classIndex == 0 ? OnehandMelee.WARRIOR_TYPES : OnehandMelee.RANGER_TYPES).length),
        0,
        random.nextInt(3) + ONE_HANDED_BASE_DAMAGE,
        random.nextInt(MAGIC_LEVEL_RANGE)
    );
  }

  private static Bow generateRandomBow() {
    return new Bow(random.nextInt(Bow.BOW_LIST.length), TWO_HANDED_BASE_DAMAGE, random.nextInt(UsableItem.MAGIC_LEVEL_RANGE));
  }

  @Override
  public boolean use(PlayerCharacter playerCharacter) {
    return false;
  }

  @Override
  public void showDescription() {
    System.out.printf("This %s is based on %s and deals 1-%d damage.", NAME, PlayerCharacter.ATTRIBUTE_NAMES[ATTR_INDEX], DAMAGE);
    System.out.println(this.MAGIC_LEVEL > 0 ? " It seems to be magical and has a " + UsableItem.MAGICAL_PREFIX[this.getMAGIC_LEVEL()-1] + " Aura." : "It doesn't seem to be magical.");
  }

  @Override
  public boolean equipMainHand(PlayerCharacter playerCharacter) {
    playerCharacter.unequipMainHand();
    System.out.println("You have equipped " + this.NAME + ".");
    playerCharacter.setMainHand(this);
    playerCharacter.getInventory().remove(this);
    this.wielder = playerCharacter;
    this.isMainHand = true;
    return true;
  }
}
