package adventure.items.weapons;

import adventure.player.PlayerCharacter;
import adventure.player.Warrior;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BowTest {

  PlayerCharacter testWarrior = new Warrior("TestWarrior", "Dummy", "Male");
  Bow testBow = new Bow(0, 10, 0);

  @Test
  void can_attack_without_Quiver() {
    testBow.equipMainHand(testWarrior);
  }
}