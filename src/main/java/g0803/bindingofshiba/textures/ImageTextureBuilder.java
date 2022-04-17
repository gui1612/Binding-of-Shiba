package g0803.bindingofshiba.textures;

import g0803.bindingofshiba.math.Vec2D;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class ImageTextureBuilder implements ITextureBuilder {

    private final int width, height;
    private final Color[] pixels;
    private Vec2D anchorPoint = new Vec2D(0, 0);

    public ImageTextureBuilder(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new Color[width * height];
    }

    public ImageTextureBuilder(BufferedImage image) {
        this(image.getWidth(), image.getHeight());
        this.loadImageData(image);
    }

    private int getIndexFromCoordinates(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            throw new IllegalArgumentException("The provided coordinates are invalid");

        return x + y * width;
    }

    public ImageTextureBuilder setAnchorPoint(double x, double y) {
        return this.setAnchorPoint(new Vec2D(x, y));
    }

    public ImageTextureBuilder setAnchorPoint(Vec2D anchorPoint) {
        this.anchorPoint = anchorPoint;
        return this;
    }

    public ImageTextureBuilder loadImageData(BufferedImage image) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        if (width != imageWidth || height != imageHeight)
            throw new IllegalArgumentException(
                    "Image dimensions must match builder's expected dimensions");

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int index = getIndexFromCoordinates(x, y);
                int rgb = image.getRGB(x, y);

                this.pixels[index] = new Color(rgb, true);
            }
        }

        return this;
    }

    public ImageTextureBuilder loadResourceData(String location) throws IOException {
        URL resourceLocation = ImageTextureBuilder.class.getResource(location);
        if (resourceLocation == null)
            throw new IOException("Could not find resource at " + location);

        BufferedImage image = ImageIO.read(resourceLocation);
        return this.loadImageData(image);
    }

    public ImageTextureBuilder setPixelAt(int x, int y, Color c) {
        int index = getIndexFromCoordinates(x, y);
        this.pixels[index] = c;
        return this;
    }

    @Override
    public StaticTexture build() {
        return new StaticTexture(width, height, anchorPoint, pixels);
    }
}
