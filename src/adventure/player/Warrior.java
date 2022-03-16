package adventure.player;

import adventure.combat.Encounter;
import adventure.combat.Enemy;
import adventure.interfaces.IsPlayable;
import adventure.items.*;
import adventure.items.weapons.*;
import adventure.menu.Menu;

import java.util.ArrayList;
import java.util.Random;

public class Warrior extends PlayerCharacter implements IsPlayable {

  public final int START_STRENGTH = 8;
  public final int START_DEXTERITY = 3;
  public final int START_INTELLIGENCE = 0;
  public final int START_HEALTH = 30;

  private Random random = new Random();

  public Warrior(String NAME, String RACE, String GENDER) {

    super(NAME, RACE, GENDER, 0);
    setStartingAttributes(START_STRENGTH, START_DEXTERITY, START_INTELLIGENCE, START_HEALTH);
    setMaxArmorType(2);
    setStartingEquipment();

  }

  @Override
  public void setStartingEquipment() {
    OnehandMelee longsword = new OnehandMelee(0, CLASS_INDEX, 8, 0);
    OnehandMelee warhammer = new OnehandMelee(0, CLASS_INDEX, 8, 0);
    TwoHandMelee greatsword = new TwoHandMelee(0, CLASS_INDEX, 12, 0);
    Shield shield = new Shield("Shield", 2, 0);
    Armor warriorArmor = new Armor(getMaxArmorType(), 0, 0);
    Potion minorPotion = new Potion(0, 0);

    this.setMainHand(longsword);
    longsword.setWielder(this);
    this.setArmor(warriorArmor);
    this.setOffHand(shield);

    this.getInventory().add(greatsword);
    this.getInventory().add(minorPotion);
    this.getInventory().add(minorPotion);
    this.getInventory().add(warhammer);
  }

  @Override
  public void attack(ArrayList<Enemy> enemies) {
    if (getMainHand() != null) {
      weaponAttack((Weapon) getMainHand(), enemies);
      if (getOffHand() instanceof Weapon) {
        weaponAttack((Weapon) getOffHand(), enemies);
      }
    } else {
      super.attack(enemies);
    }
  }

  private void weaponAttack(Weapon weapon, ArrayList<Enemy> enemies) {
    Enemy enemy = chooseEnemy(enemies);
    int hitBonus = getAttributes()[weapon.getATTR_INDEX()] + weapon.getMAGIC_LEVEL();
    int diceRoll = random.nextInt(20) + 1;

    System.out.printf(Menu.MELEE_ATTACK_LIST[random.nextInt(Menu.MELEE_ATTACK_LIST.length)], this.getNAME(), "", enemy.getName(), "");
    Menu.playLoadingAnimation(500);
    if (calculateHit(enemy, diceRoll, hitBonus)) {
      rollDamage(weapon, diceRoll == 20, enemy);
    }
    System.out.printf("(Hit roll was %d + %d)\n", diceRoll, getAttributes()[getMainHand().getATTR_INDEX()]);
    Encounter.removeDeadEnemies(enemies, this);
    Menu.pressEnter();
  }

  private boolean calculateHit(Enemy enemy, int diceRoll, int hitBonus) {
    boolean isHit = false;
    if (diceRoll + hitBonus >= enemy.getArmorClass()) {
      isHit = true;
    } else if (diceRoll == 1) {
      System.out.printf(Menu.MISS_LIST[Menu.MISS_LIST.length - 1], "");
    } else {
      System.out.printf(Menu.MISS_LIST[random.nextInt(Menu.MISS_LIST.length - 1)], enemy.getName(), "s");
    }
    return isHit;
  }

  private void rollDamage(Weapon weapon, boolean isCritical, Enemy enemy) {
    int damageDice = weapon.getDAMAGE();
    int weaponStat = getAttributes()[weapon.getATTR_INDEX()] / (weapon == getOffHand() ? 2 : 1) + weapon.getMAGIC_LEVEL();
    int damageRoll = random.nextInt(damageDice) + 1;
    int totalDamage = damageRoll + weaponStat;
    totalDamage = isCritical ? totalDamage + damageDice : totalDamage;
    enemy.takeDamage(totalDamage);
    System.out.printf((isCritical ? Menu.HIT_LIST[Menu.HIT_LIST.length - 1] : Menu.HIT_LIST[random.nextInt(Menu.HIT_LIST.length - 1)]) + "(Damage: %d + %d%s)\n", "", totalDamage, damageRoll, weaponStat, isCritical ? " + " + damageDice : "");
  }

  @Override
  public void levelUp() {
    int expNeeded = 400 * (level - 1) + 200;
    if (experiencePoints > expNeeded && level < 20) {
      super.levelUp();
      setStrength(getStrength() + level % 2);
      setDexterity(getDexterity() + (level % 4 == 0 ? 1 : 0));
      setHealthPoints(getHealthPoints() + 10);
      setMaxHealthPoints(getMaxHealthPoints() + 10);

    }
  }
}
