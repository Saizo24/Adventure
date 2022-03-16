package adventure.player;

import adventure.interfaces.IsPlayable;
import adventure.items.Armor;
import adventure.items.Shield;
import adventure.items.weapons.OnehandMelee;

public class Ranger extends PlayerCharacter implements IsPlayable {
  public final int CLASS_INDEX = 1;
  public final int START_STRENGTH = 3;
  public final int START_DEXTERITY = 10;
  public final int START_INTELLIGENCE = 0;
  public final int START_HEALTH = 20;

  private int level = 1;

  public Ranger(String NAME, String RACE, String GENDER) {

    super(NAME, RACE, GENDER, 1);
    setStartingAttributes(START_STRENGTH, START_DEXTERITY, START_INTELLIGENCE, START_HEALTH);
    setStartingEquipment();
    setMaxArmorType(1);
  }

  public void setLevel(int level) {
    this.level = level;
  }


  @Override
  public void setStartingEquipment() {
    OnehandMelee Longsword = new OnehandMelee(1, 0, 10, 0);
    Shield shield = new Shield("Shield", 2, 0);
    Armor warriorArmor = new Armor(this.CLASS_INDEX, 0, 0);
    this.setMainHand(Longsword);
    this.setOffHand(shield);
    this.setArmor(warriorArmor);
  }
}
