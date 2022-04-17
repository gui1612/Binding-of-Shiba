package g0803.bindingofshiba.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoundingBoxTest {

    public BoundingBox getBoundingBox() {
        return new BoundingBox(5, 10);
    }

    @Test
    public void getTopLeftCorner() {
        BoundingBox box = getBoundingBox();

        Vec2D expected = new Vec2D(0, 0);
        Vec2D actual = box.getTopLeftCorner();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getTopRightCorner() {
        BoundingBox box = getBoundingBox();

        Vec2D expected = new Vec2D(5, 0);
        Vec2D actual = box.getTopRightCorner();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getBottomLeftCorner() {
        BoundingBox box = getBoundingBox();

        Vec2D expected = new Vec2D(0, 10);
        Vec2D actual = box.getBottomLeftCorner();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getBottomRightCorner() {
        BoundingBox box = getBoundingBox();

        Vec2D expected = new Vec2D(5, 10);
        Vec2D actual = box.getBottomRightCorner();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getDimensions() {
        BoundingBox box = getBoundingBox();

        Vec2D expected = new Vec2D(5, 10);
        Vec2D actual = box.getDimensions();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void translate() {
        BoundingBox box = getBoundingBox();

        BoundingBox expected = new BoundingBox(6.4, 5.6, 5, 10);
        BoundingBox actual1 = box.translate(6.4, 5.6);
        BoundingBox actual2 = box.translate(new Vec2D(6.4, 5.6));

        Assertions.assertEquals(expected, actual1);
        Assertions.assertEquals(expected, actual2);
    }

    @Test
    public void doesNotCollide() {
        BoundingBox box1 = getBoundingBox();
        BoundingBox box2 = new BoundingBox(10, 10, 6, 7);

        Assertions.assertFalse(box1.collides(box2));
        Assertions.assertFalse(box2.collides(box1));
    }

    @Test
    public void collides() {
        BoundingBox box1 = getBoundingBox();
        BoundingBox box2 = new BoundingBox(3, 4, 5, 6);

        Assertions.assertTrue(box1.collides(box2));
        Assertions.assertTrue(box2.collides(box1));
    }

    @Test
    public void collisionInside() {
        BoundingBox box1 = getBoundingBox();
        BoundingBox box2 = new BoundingBox(1, 1, 1, 1);

        Assertions.assertTrue(box1.collides(box2));
        Assertions.assertTrue(box2.collides(box1));
    }

    @Test
    public void collisionBottom() {
        BoundingBox box1 = getBoundingBox();
        BoundingBox box2 = new BoundingBox(0, 4, 6, 7);

        Assertions.assertTrue(box1.collides(box2));
        Assertions.assertTrue(box2.collides(box1));
    }

    @Test
    public void collidesTop() {
        BoundingBox box1 = new BoundingBox(0, 0, 5, 5);
        BoundingBox box2 = new BoundingBox(4, 4, 4, 4);

        Assertions.assertTrue(box1.collides(box2));
        Assertions.assertTrue(box2.collides(box1));
    }

    @Test
    public void collidesLeft() {
        BoundingBox box1 = new BoundingBox(3, 3, 4, 4);
        BoundingBox box2 = new BoundingBox(0, 3, 5, 1);

        Assertions.assertTrue(box1.collides(box2));
        Assertions.assertTrue(box2.collides(box1));
    }

    @Test
    public void touch() {
        BoundingBox box1 = getBoundingBox();
        BoundingBox box2 = new BoundingBox(5, 10, 3, 3);

        Assertions.assertTrue(box1.collides(box2));
        Assertions.assertTrue(box2.collides(box1));
    }

    @Test
    public void touchSide() {
        BoundingBox box1 = new BoundingBox(3, 3, 4, 4);
        BoundingBox box2 = new BoundingBox(3, 7, 4, 4);

        Assertions.assertTrue(box1.collides(box2));
        Assertions.assertTrue(box2.collides(box1));
    }

    @Test
    public void collidesWithSelf() {
        BoundingBox box1 = getBoundingBox();
        BoundingBox box2 = getBoundingBox();

        Assertions.assertTrue(box1.collides(box2));
        Assertions.assertTrue(box2.collides(box1));
    }

    @Test
    public void contains() {
        BoundingBox box1 = new BoundingBox(3, 2, 10, 7);
        BoundingBox box2 = new BoundingBox(5, 6, 1, 2);

        Assertions.assertTrue(box1.contains(box2));
        Assertions.assertFalse(box2.contains(box1));
    }
}
