package g0803.bindingofshiba.model.game.elements;

import g0803.bindingofshiba.math.Vec2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class MonsterTest {

    public Monster getMonster() {
        return new Monster(new Vec2D(5, 5), 50, 20);
    }

    @Test
    public void decreaseHp() {
        Monster monster = getMonster();
        monster.decreaseHpByAmount(20);
        Assertions.assertEquals(30, monster.getHp());
        monster.decreaseHpByAmount(10);
        Assertions.assertEquals(20, monster.getHp());
        monster.decreaseHpByAmount(20);
        Assertions.assertEquals(0, monster.getHp());
    }

    @Test
    public void decreaseHpByAmountLargerThanHealth() {
        Monster monster = getMonster();
        monster.decreaseHpByAmount(100);
        Assertions.assertEquals(0, monster.getHp());
    }

    @Test
    public void decreaseHpByNegativeNumber() {
        Monster monster = getMonster();
        Assertions.assertThrows(
                IllegalArgumentException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        monster.decreaseHpByAmount(-2);
                    }
                });
    }

    @Test
    public void killMonster() {
        Monster monster = getMonster();
        monster.decreaseHpByAmount(50);
        Assertions.assertFalse(monster.isAlive());
    }

    @Test
    public void damageMonsterSmallAmount() {
        Monster monster = getMonster();
        monster.decreaseHpByAmount(1);
        Assertions.assertEquals(49, monster.getHp());
    }

    @Test
    public void damageMonsterLargeAmount() {
        Monster monster = getMonster();
        monster.decreaseHpByAmount(49);
        Assertions.assertEquals(1, monster.getHp());
    }

    @Test
    public void damagingMonsterKeepAlive() {
        Monster monster = getMonster();
        monster.decreaseHpByAmount(49);
        Assertions.assertTrue(monster.isAlive());
    }
}
