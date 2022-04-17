package g0803.bindingofshiba.bundles;

import g0803.bindingofshiba.math.BoundingBox;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DefaultBoundingBoxesProviderTest {

    @Test
    public void hasRequiredKeys() {
        Bundle<BoundingBox> bundle = new DefaultBoundingBoxesProvider().getBundle();

        Set<String> actual = bundle.keys();
        Set<String> expected =
                Set.of(
                        "monster",
                        "shiba",
                        "room",
                        "heart",
                        "rock",
                        "door.open.bottom",
                        "door.open.left",
                        "door.open.top",
                        "door.open.right",
                        "door.closed.bottom",
                        "door.closed.left",
                        "door.closed.top",
                        "door.closed.right");

        Assertions.assertEquals(expected, actual);

        for (String key : expected) Assertions.assertNotNull(bundle.get(key));
    }
}
