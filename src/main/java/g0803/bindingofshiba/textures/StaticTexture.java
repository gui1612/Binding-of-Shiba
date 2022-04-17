package g0803.bindingofshiba.textures;

import g0803.bindingofshiba.math.BoundingBox;
import g0803.bindingofshiba.math.Vec2D;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StaticTexture implements ITexture {

    private final int width, height;
    private final Vec2D anchorPoint;
    private final Color[] pixels;

    public StaticTexture(int width, int height, Vec2D anchorPoint, Color[] pixels) {
        if (pixels.length != width * height)
            throw new IllegalArgumentException(
                    "Pixel array dimensions don't match expected dimensions");

        this.width = width;
        this.height = height;
        this.anchorPoint = anchorPoint;
        this.pixels = pixels;
    }

    public StaticTexture(StaticTexture texture, Vec2D anchorPoint) {
        this.width = texture.width;
        this.height = texture.height;
        this.anchorPoint = anchorPoint;
        this.pixels = texture.pixels.clone();
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public Vec2D getAnchorOffset(int x, int y) {
        return this.getAnchorOffset(new Vec2D(x, y));
    }

    @Override
    public Vec2D getAnchorOffset(Vec2D position) {
        return position.subtract(this.anchorPoint);
    }

    @Override
    public Vec2D getAnchorPoint() {
        return anchorPoint;
    }

    @Override
    public BoundingBox getTextureBoundingBox() {
        Vec2D textureOrigin = this.getAnchorOffset(Vec2D.zero());
        return new BoundingBox(textureOrigin, this.width, this.height);
    }

    @Override
    public Color getColorAt(int x, int y) {
        int pixel = x + y * width;
        return this.pixels[pixel];
    }

    public StaticTexture rotateLeft() {
        Color[] newPixels = rotatePixels(true);

        double angle = Math.PI / 2;
        Vec2D widthVector = new Vec2D(0, this.width - 1);
        Vec2D newAnchorPoint = this.anchorPoint.rotate(angle).add(widthVector);

        return new StaticTexture(this.height, this.width, newAnchorPoint, newPixels);
    }

    public StaticTexture rotateRight() {
        Color[] newPixels = rotatePixels(false);

        double angle = -Math.PI / 2;
        Vec2D heightVector = new Vec2D(this.height - 1, 0);
        Vec2D newAnchorPoint = this.anchorPoint.rotate(angle).add(heightVector);

        return new StaticTexture(this.height, this.width, newAnchorPoint, newPixels);
    }

    public StaticTexture flipVertically() {
        Color[] newPixels = flipPixels(false);

        double distanceToBottom = height - this.anchorPoint.getY();
        Vec2D newAnchorPoint = this.anchorPoint.setY(distanceToBottom);

        return new StaticTexture(this.width, this.height, newAnchorPoint, newPixels);
    }

    public StaticTexture flipHorizontally() {
        Color[] newPixels = flipPixels(true);

        double distanceToRight = width - this.anchorPoint.getX();
        Vec2D newAnchorPoint = this.anchorPoint.setX(distanceToRight);

        return new StaticTexture(this.width, this.height, newAnchorPoint, newPixels);
    }

    private Color[] flipPixels(boolean horizontal) {
        java.util.List<Color> newPixels = new ArrayList<>(this.pixels.length);

        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                Color color = this.getColorAt(x, y);
                newPixels.add(color);
            }
        }

        if (horizontal) Collections.reverse(newPixels);
        return newPixels.toArray(new Color[0]);
    }

    private Color[] rotatePixels(boolean left) {
        List<Color> newPixels = new ArrayList<>(this.pixels.length);

        for (int x = 0; x < width; x++) {
            for (int y = height - 1; y >= 0; y--) {
                Color color = this.getColorAt(x, y);
                newPixels.add(color);
            }
        }

        if (left) Collections.reverse(newPixels);
        return newPixels.toArray(new Color[0]);
    }
}
