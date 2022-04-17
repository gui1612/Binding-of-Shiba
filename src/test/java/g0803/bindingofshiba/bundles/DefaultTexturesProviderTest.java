package g0803.bindingofshiba.bundles;

import g0803.bindingofshiba.textures.ITexture;
import java.awt.*;
import java.io.IOException;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DefaultTexturesProviderTest {

    private static DefaultTexturesProvider provider;

    @BeforeAll
    public static void setUp() throws IOException {
        provider = new DefaultTexturesProvider();
    }

    @Test
    public void hasRequiredKeys() {
        Bundle<ITexture> bundle = provider.getBundle();

        Set<String> actual = bundle.keys();
        Set<String> expected =
                Set.of(
                        "shiba",
                        "rock",
                        "hud",
                        "heart",
                        "room.walls.open.left",
                        "room.walls.open.right",
                        "room.walls.open.bottom",
                        "room.walls.open.top",
                        "room.walls.closed.left",
                        "room.walls.closed.right",
                        "room.walls.closed.bottom",
                        "room.walls.closed.top",
                        "monster.aura.idle",
                        "monster.aura.attack",
                        "monster.damaged",
                        "monster.normal",
                        "key",
                        "health.changing",
                        "health.idle",
                        "door.closed.right",
                        "door.closed.top",
                        "door.closed.left",
                        "door.closed.bottom",
                        "door.open.right",
                        "door.open.top",
                        "door.open.left",
                        "door.open.bottom");

        Assertions.assertEquals(expected, actual);

        for (String key : expected) Assertions.assertNotNull(bundle.get(key));
    }
}
