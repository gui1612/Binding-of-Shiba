package g0803.bindingofshiba.textures;

import g0803.bindingofshiba.math.Vec2D;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;

public class TextTextureBuilder implements ITextureBuilder {

    private final Font font;
    private String text = "";
    private Color color = Color.white;
    private Vec2D anchorPoint = null;

    public TextTextureBuilder(Font font) {
        this.font = font;
    }

    public TextTextureBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public TextTextureBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public TextTextureBuilder setAnchorPoint(double x, double y) {
        return this.setAnchorPoint(new Vec2D(x, y));
    }

    public TextTextureBuilder setAnchorPoint(Vec2D anchorPoint) {
        this.anchorPoint = anchorPoint;
        return this;
    }

    @Override
    public StaticTexture build() {
        FontRenderContext frc = new FontRenderContext(null, false, false);
        LineMetrics metrics = font.getLineMetrics(text, frc);

        int width = (int) font.getStringBounds(text, frc).getWidth();
        int height = (int) metrics.getHeight();
        int ascent = (int) metrics.getAscent();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics graphics = image.getGraphics();
        graphics.setColor(color);
        graphics.setFont(font);
        graphics.drawString(text, 0, ascent);

        ImageTextureBuilder builder = new ImageTextureBuilder(image).setAnchorPoint(0, height - 1);

        if (this.anchorPoint != null) builder.setAnchorPoint(this.anchorPoint);

        return builder.build();
    }
}
