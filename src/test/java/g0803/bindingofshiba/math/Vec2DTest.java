package g0803.bindingofshiba.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Vec2DTest {

    public Vec2D getVec() {
        return new Vec2D(3, 4);
    }

    @Test
    void getLength() {
        Vec2D vec = getVec();

        double expected = 5;
        double actual = vec.getLength();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getLengthSquared() {
        Vec2D vec = getVec();

        double expected = 25;
        double actual = vec.getLengthSquared();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void add() {
        Vec2D vec = getVec();
        Vec2D other = new Vec2D(1, 2);

        Vec2D expected = new Vec2D(4, 6);
        Vec2D actual = vec.add(other);

        Assertions.assertTrue(actual.isSimilar(expected));
    }

    @Test
    void subtract() {
        Vec2D vec = getVec();
        Vec2D other = new Vec2D(3, 1);

        Vec2D expected = new Vec2D(0, 3);
        Vec2D actual = vec.subtract(other);

        Assertions.assertTrue(actual.isSimilar(expected));
    }

    @Test
    void scale() {
        Vec2D vec = getVec();

        Vec2D expected = new Vec2D(6, 8);
        Vec2D actual = vec.scale(2);

        Assertions.assertTrue(actual.isSimilar(expected));
    }

    @Test
    void scaleX() {
        Vec2D vec = getVec();

        Vec2D expected = new Vec2D(6, 4);
        Vec2D actual = vec.scaleX(2);

        Assertions.assertTrue(actual.isSimilar(expected));
    }

    @Test
    void scaleY() {
        Vec2D vec = getVec();

        Vec2D expected = new Vec2D(3, 2);
        Vec2D actual = vec.scaleY(0.5);

        Assertions.assertTrue(actual.isSimilar(expected));
    }

    @Test
    void scalarProduct() {
        Vec2D vec = getVec();
        Vec2D other = new Vec2D(-3, 0.25);

        double expected = -8;
        double actual = vec.scalarProduct(other);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void rotate() {
        Vec2D vec = new Vec2D(0.6, -0.8);

        double angle = Math.PI;
        Vec2D expected = new Vec2D(-0.6, 0.8);
        Vec2D actual = vec.rotate(angle);

        Assertions.assertTrue(actual.isSimilar(expected));
    }

    @Test
    void normalize() {
        Vec2D vec = getVec();

        Vec2D expected = new Vec2D(0.6, 0.8);
        Vec2D actual = vec.normalize();

        Assertions.assertTrue(actual.isSimilar(expected));
    }

    @Test
    void round() {
        Vec2D vec = new Vec2D(1.8, 0.00009);

        Vec2D expected = new Vec2D(2, 0);
        Vec2D actual = vec.round();

        Assertions.assertTrue(expected.isSimilar(actual));
    }

    @Test
    void isSimilar() {
        Vec2D vec1 = new Vec2D(1.0, 0.2);
        Vec2D vec2 = new Vec2D(0.999999999999997, 0.20000000000000004);

        Assertions.assertTrue(vec1.isSimilar(vec2));
    }

    @Test
    void isNotSimilar() {
        Vec2D vec1 = new Vec2D(1, 2);
        Vec2D vec2 = new Vec2D(1.5, 2);

        Assertions.assertFalse(vec1.isSimilar(vec2));
    }
}
