package g0803.bindingofshiba.model.game.elements;

import g0803.bindingofshiba.math.Vec2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MoveableElementTest {

    @Test
    public void moveWithConstantVelocity() {
        MoveableElement element = new MoveableElement(new Vec2D(1, 2));

        element.setVelocity(new Vec2D(10, 3));
        element.move(0.5);

        Assertions.assertTrue(Vec2D.zero().isSimilar(element.getAcceleration()));
        Assertions.assertTrue(new Vec2D(10, 3).isSimilar(element.getVelocity()));
        Assertions.assertTrue(new Vec2D(6, 3.5).isSimilar(element.getPosition()));

        element.move(0.5);

        Assertions.assertTrue(Vec2D.zero().isSimilar(element.getAcceleration()));
        Assertions.assertTrue(new Vec2D(10, 3).isSimilar(element.getVelocity()));
        Assertions.assertTrue(new Vec2D(11, 5).isSimilar(element.getPosition()));
    }

    @Test
    public void moveWithConstantAcceleration() {
        MoveableElement element = new MoveableElement(new Vec2D(-1, 3));

        element.setAcceleration(new Vec2D(2, 0.5));
        element.move(0.5);

        Assertions.assertTrue(new Vec2D(2, 0.5).isSimilar(element.getAcceleration()));
        Assertions.assertTrue(new Vec2D(1, 0.25).isSimilar(element.getVelocity()));
        Assertions.assertTrue(new Vec2D(-0.5, 3.125).isSimilar(element.getPosition()));

        element.move(0.5);

        Assertions.assertTrue(new Vec2D(2, 0.5).isSimilar(element.getAcceleration()));
        Assertions.assertTrue(new Vec2D(2, 0.5).isSimilar(element.getVelocity()));
        Assertions.assertTrue(new Vec2D(0.5, 3.375).isSimilar(element.getPosition()));
    }

    @Test
    public void moveWithChangingAccelerationAndVelocity() {
        MoveableElement element = new MoveableElement(new Vec2D(4, 1));

        element.setAcceleration(new Vec2D(6, 9));
        element.setVelocity(new Vec2D(-1, 4));

        element.move(2);

        Assertions.assertTrue(new Vec2D(6, 9).isSimilar(element.getAcceleration()));
        Assertions.assertTrue(new Vec2D(11, 22).isSimilar(element.getVelocity()));
        Assertions.assertTrue(new Vec2D(26, 45).isSimilar(element.getPosition()));

        element.setAcceleration(new Vec2D(-1, -5));
        element.setVelocity(new Vec2D(-10, -3));

        element.move(2);

        Assertions.assertTrue(new Vec2D(-1, -5).isSimilar(element.getAcceleration()));
        Assertions.assertTrue(new Vec2D(-12, -13).isSimilar(element.getVelocity()));
        Assertions.assertTrue(new Vec2D(2, 19).isSimilar(element.getPosition()));
    }

    @Test
    public void getNextVelocity() {
        MoveableElement element = new MoveableElement(new Vec2D(1, 3));

        element.setAcceleration(new Vec2D(3, 4));
        element.setVelocity(new Vec2D(-2, 3));

        Assertions.assertTrue(new Vec2D(7, 15).isSimilar(element.getNextVelocity(3)));
    }

    @Test
    public void getNextPosition() {
        MoveableElement element = new MoveableElement(new Vec2D(1, 3));

        element.setAcceleration(new Vec2D(3, 4));
        element.setVelocity(new Vec2D(-2, 3));

        Assertions.assertTrue(new Vec2D(22, 48).isSimilar(element.getNextPosition(3)));
    }
}
