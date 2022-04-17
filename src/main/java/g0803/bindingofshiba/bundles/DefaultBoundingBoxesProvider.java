package g0803.bindingofshiba.bundles;

import g0803.bindingofshiba.math.BoundingBox;

public class DefaultBoundingBoxesProvider implements BundleProvider<BoundingBox> {

    private final Bundle<BoundingBox> boundingBoxes = new HashMapBundle<>();

    public DefaultBoundingBoxesProvider() {
        this.registerBoundingBoxes();
    }

    private void registerBoundingBoxes() {
        boundingBoxes.register("monster", new BoundingBox(-4, -3, 10, 4));
        boundingBoxes.register("shiba", new BoundingBox(-3, -6, 7, 7));
        boundingBoxes.register("room", new BoundingBox(3, 3, 137, 60));
        boundingBoxes.register("heart", new BoundingBox(-2, -2, 5, 5));
        boundingBoxes.register("rock", new BoundingBox(-4, -2, 9, 3));

        BoundingBox horizontalDoorBoundingBox = new BoundingBox(-10, -2, 21, 5);
        BoundingBox verticalDoorBoundingBox = new BoundingBox(-2, -10, 5, 21);
        boundingBoxes.register("door.open.bottom", horizontalDoorBoundingBox);
        boundingBoxes.register("door.open.left", verticalDoorBoundingBox);
        boundingBoxes.register("door.open.top", horizontalDoorBoundingBox);
        boundingBoxes.register("door.open.right", verticalDoorBoundingBox);

        boundingBoxes.register("door.closed.bottom", horizontalDoorBoundingBox);
        boundingBoxes.register("door.closed.left", verticalDoorBoundingBox);
        boundingBoxes.register("door.closed.top", horizontalDoorBoundingBox);
        boundingBoxes.register("door.closed.right", verticalDoorBoundingBox);
    }

    @Override
    public Bundle<BoundingBox> getBundle() {
        return boundingBoxes;
    }
}
