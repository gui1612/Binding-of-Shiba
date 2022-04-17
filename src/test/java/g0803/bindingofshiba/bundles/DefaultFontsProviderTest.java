package g0803.bindingofshiba.bundles;

import java.awt.*;
import java.io.IOException;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DefaultFontsProviderTest {

    private static DefaultFontsProvider provider;

    @BeforeAll
    public static void setUp() throws IOException, FontFormatException {
        provider = new DefaultFontsProvider();
    }

    @Test
    public void hasRequiredKeys() {
        Bundle<Font> bundle = provider.getBundle();

        Set<String> expected = Set.of("square", "text");
        Set<String> actual = bundle.keys();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void hasDefaultFonts() {
        Bundle<Font> bundle = provider.getBundle();

        Font squareFont = bundle.get("square");
        Assertions.assertEquals("Square", squareFont.getFontName());

        Font textFont = bundle.get("text");
        Assertions.assertEquals("CG pixel 4x5 Regular", textFont.getFontName());
    }
}
