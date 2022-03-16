package adventure.player;

import adventure.interfaces.IsPlayable;
import adventure.items.Armor;
import adventure.items.Shield;
import adventure.items.weapons.OnehandMelee;

public class Wizard extends PlayerCharacter implements IsPlayable {

  public final int CLASS_INDEX = 2;
  public final int START_STRENGTH = 0;
  public final int START_DEXTERITY = 3;
  public final int START_INTELLIGENCE = 10;
  public final int START_HEALTH = 15;


  private int mana = 30;
  private int shieldPoints = 0;
  private int level = 1;

  public Wizard(String NAME, String RACE, String GENDER) {

    super(NAME, RACE, GENDER, 2);
    setStartingAttributes(START_STRENGTH, START_DEXTERITY, START_INTELLIGENCE, START_HEALTH);
    setStartingEquipment();
    setMaxArmorType(0);
  }

  public void setLevel(int level) {
    this.level = level;
  }

  @Override
  public void setStartingEquipment() {
    OnehandMelee Longsword = new OnehandMelee(1, CLASS_INDEX, 10, 0);
    Shield shield = new Shield("Shield", 2, 0);
    Armor warriorArmor = new Armor(CLASS_INDEX, 0, 0);
    this.setMainHand(Longsword);
    this.setOffHand(shield);
    this.setArmor(warriorArmor);
  }

  @Override
  public void regenerateMana(int amount) {
    this.mana += amount;
  }

}
