package adventure.items;

import adventure.items.Armor;
import adventure.player.PlayerCharacter;
import adventure.player.Warrior;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;



class ArmorTest {

  Armor lightArmor01 = new Armor(0, 0, 0);
  Armor mediumArmor01 = new Armor(1, 0, 0);
  Armor mediumArmor02 = new Armor(1,1,0);
  Armor heavyArmor01 = new Armor(2, 0, 0);
  Armor heavyArmor02 = new Armor(2, 1, 0);
  Armor heavyArmor03 = new Armor(2, 2, 0);
  Armor magicLightArmor01 = new Armor(0,0,1);

  @Test
  void armor_gets_correct_ArmorBonus() {
    assertEquals(2, lightArmor01.getARMOR_BONUS());
    assertEquals(4, mediumArmor01.getARMOR_BONUS());
    assertEquals(5, mediumArmor02.getARMOR_BONUS());
    assertEquals(6, heavyArmor01.getARMOR_BONUS());
    assertEquals(7, heavyArmor02.getARMOR_BONUS());
    assertEquals(8, heavyArmor03.getARMOR_BONUS());
    assertEquals(3, magicLightArmor01.getARMOR_BONUS());
  }

  @Test
  void armor_gets_correct_Name() {
    assertEquals("Cloth Armor", lightArmor01.getNAME());
    assertEquals("Cloth Armor+1", magicLightArmor01.getNAME());
  }

}