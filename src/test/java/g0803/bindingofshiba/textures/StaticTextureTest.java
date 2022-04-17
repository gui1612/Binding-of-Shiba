package g0803.bindingofshiba.textures;

import g0803.bindingofshiba.math.BoundingBox;
import g0803.bindingofshiba.math.Vec2D;
import java.awt.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StaticTextureTest {

    public Color[] getColors() {
        return new Color[] {
            new Color(4, 5, 6),
            new Color(33, 33, 33),
            new Color(255, 0, 0),
            new Color(255, 255, 255),
            new Color(0, 255, 0),
            new Color(0, 0, 255),
            new Color(6, 6, 40),
            new Color(0, 0, 0)
        };
    }

    @Test
    public void copiesPixels() {
        Vec2D originalAnchorPoint = new Vec2D(1, 0);
        Vec2D copyAnchorPoint = new Vec2D(2, 3);

        StaticTexture original = new StaticTexture(2, 4, originalAnchorPoint, getColors());
        StaticTexture copy = new StaticTexture(original, copyAnchorPoint);

        Assertions.assertEquals(original.getWidth(), copy.getWidth());
        Assertions.assertEquals(original.getHeight(), copy.getHeight());

        Assertions.assertEquals(originalAnchorPoint, original.getAnchorPoint());
        Assertions.assertEquals(copyAnchorPoint, copy.getAnchorPoint());

        for (int x = 0; x < original.getWidth(); x++) {
            for (int y = 0; y < original.getHeight(); y++) {
                Assertions.assertEquals(original.getColorAt(x, y), copy.getColorAt(x, y));
            }
        }
    }

    @Test
    public void getAnchorOffset() {
        StaticTexture staticTexture = new StaticTexture(4, 5, new Vec2D(6, 6), new Color[20]);
        Assertions.assertEquals(new Vec2D(-1, 0), staticTexture.getAnchorOffset(5, 6));
    }

    @Test
    public void getTextureBoundingBox() {
        StaticTexture staticTexture = new StaticTexture(6, 7, new Vec2D(8, 9), new Color[6 * 7]);
        Assertions.assertEquals(
                new BoundingBox(new Vec2D(-8, -9), 6, 7), staticTexture.getTextureBoundingBox());
    }

    @Test
    public void getColorAt() {
        StaticTexture staticTexture = new StaticTexture(2, 4, new Vec2D(10, 4), getColors());
        Assertions.assertEquals(new Color(255, 0, 0), staticTexture.getColorAt(0, 1));
    }

    @Test
    public void flipHorizontally() {
        StaticTexture staticTexture = new StaticTexture(2, 4, new Vec2D(7, 8), getColors());
        staticTexture = staticTexture.flipHorizontally();
        Assertions.assertEquals(new Color(255, 255, 255), staticTexture.getColorAt(0, 1));
        Assertions.assertEquals(new Color(0, 0, 255), staticTexture.getColorAt(0, 2));

        Assertions.assertEquals(4, staticTexture.getHeight());
        Assertions.assertEquals(2, staticTexture.getWidth());

        Assertions.assertEquals(new Vec2D(-5, 8), staticTexture.getAnchorPoint());
    }

    @Test
    public void flipVertically() {
        StaticTexture staticTexture = new StaticTexture(2, 4, new Vec2D(5, 6), getColors());
        staticTexture = staticTexture.flipVertically();
        Assertions.assertEquals(new Color(0, 0, 0), staticTexture.getColorAt(1, 0));
        Assertions.assertEquals(new Color(0, 0, 255), staticTexture.getColorAt(1, 1));

        Assertions.assertEquals(4, staticTexture.getHeight());
        Assertions.assertEquals(2, staticTexture.getWidth());

        Assertions.assertEquals(new Vec2D(5, -2), staticTexture.getAnchorPoint());
    }

    @Test
    public void rotateLeft() {
        StaticTexture staticTexture = new StaticTexture(2, 4, new Vec2D(1, 2), getColors());

        staticTexture = staticTexture.rotateLeft();
        Assertions.assertEquals(new Color(255, 255, 255), staticTexture.getColorAt(1, 0));
        Assertions.assertEquals(new Color(0, 255, 0), staticTexture.getColorAt(2, 1));
        Assertions.assertEquals(2, staticTexture.getHeight());
        Assertions.assertEquals(4, staticTexture.getWidth());
        Assertions.assertTrue(staticTexture.getAnchorPoint().isSimilar(new Vec2D(2, 0)));

        staticTexture = staticTexture.rotateLeft();
        Assertions.assertTrue(staticTexture.getAnchorPoint().isSimilar(new Vec2D(0, 1)));
    }

    @Test
    public void rotateRight() {
        StaticTexture staticTexture = new StaticTexture(4, 2, new Vec2D(2, 1), getColors());

        staticTexture = staticTexture.rotateRight();
        Assertions.assertEquals(new Color(0, 0, 255), staticTexture.getColorAt(0, 1));
        Assertions.assertEquals(new Color(6, 6, 40), staticTexture.getColorAt(0, 2));
        Assertions.assertEquals(2, staticTexture.getWidth());
        Assertions.assertEquals(4, staticTexture.getHeight());
        Assertions.assertTrue(staticTexture.getAnchorPoint().isSimilar(new Vec2D(0, 2)));

        staticTexture = staticTexture.rotateRight();
        Assertions.assertTrue(staticTexture.getAnchorPoint().isSimilar(new Vec2D(1, 0)));
    }
}
