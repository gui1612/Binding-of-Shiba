package g0803.bindingofshiba.model.game.elements;

import g0803.bindingofshiba.math.Vec2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class PlayerTest {

    public Player getPlayer() {
        return new Player(new Vec2D(5, 5), 1, 50, 20);
    }

    @Test
    public void decreaseHp() {
        Player player = getPlayer();
        player.decreaseHpByAmount(20);
        Assertions.assertEquals(30, player.getHp());
        player.decreaseHpByAmount(10);
        Assertions.assertEquals(20, player.getHp());

        Assertions.assertTrue(player.isAlive());

        player.decreaseHpByAmount(20);
        Assertions.assertEquals(0, player.getHp());

        Assertions.assertFalse(player.isAlive());
    }

    @Test
    public void decreaseHpByAmountLargerThanHealth() {
        Player player = getPlayer();
        player.decreaseHpByAmount(100);
        Assertions.assertEquals(0, player.getHp());
        Assertions.assertFalse(player.isAlive());
    }

    @Test
    public void decreaseHpByNegativeNumber() {
        Player player = getPlayer();
        Assertions.assertThrows(
                IllegalArgumentException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        player.decreaseHpByAmount(-2);
                    }
                });
    }

    @Test
    public void killPlayer() {
        Player player = getPlayer();
        player.decreaseHpByAmount(50);
        Assertions.assertFalse(player.isAlive());
    }

    @Test
    public void pickKey() {
        Player player = getPlayer();
        player.pickKey();
        player.pickKey();
        player.pickKey();
        Assertions.assertEquals(4, player.getNumberOfKeys());
    }

    @Test
    void dropKey() {
        Player player = getPlayer();
        player.dropKey();
        Assertions.assertEquals(0, player.getNumberOfKeys());

        Exception exception = Assertions.assertThrows(IllegalStateException.class, player::dropKey);

        String expectedMessage = "There are no keys to remove";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}
