package g0803.bindingofshiba.bundles;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DefaultFontsProvider implements BundleProvider<Font> {

    private final Bundle<Font> bundle = new HashMapBundle<>();

    public DefaultFontsProvider() throws IOException, FontFormatException {
        this.loadFonts();
    }

    @Override
    public Bundle<Font> getBundle() {
        return bundle;
    }

    private void loadFonts() throws IOException, FontFormatException {
        Font squareFont = loadFont("/fonts/square.ttf", 10);
        Font textFont = loadFont("/fonts/cg-pixel-4x5.ttf", 5);

        bundle.register("square", squareFont);
        bundle.register("text", textFont);
    }

    private Font loadFont(String location, int size) throws IOException, FontFormatException {
        URL resource = DefaultFontsProvider.class.getResource(location);
        if (resource == null) throw new IOException("Could not find resource at " + location);

        InputStream fileStream = resource.openStream();
        Font font = Font.createFont(Font.TRUETYPE_FONT, fileStream);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        return font.deriveFont(Font.PLAIN, size);
    }
}
