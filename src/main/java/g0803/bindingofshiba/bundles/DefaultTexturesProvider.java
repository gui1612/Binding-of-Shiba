package g0803.bindingofshiba.bundles;

import g0803.bindingofshiba.textures.ITexture;
import g0803.bindingofshiba.textures.ImageTextureBuilder;
import g0803.bindingofshiba.textures.StaticTexture;
import java.io.IOException;

public class DefaultTexturesProvider implements BundleProvider<ITexture> {

    private final Bundle<ITexture> bundle = new HashMapBundle<>();

    public DefaultTexturesProvider() throws IOException {
        this.loadTextures();
    }

    @Override
    public Bundle<ITexture> getBundle() {
        return bundle;
    }

    private void loadTextures() throws IOException {
        this.loadShibaTexture();
        this.loadRockTexture();
        this.loadHudTexture();
        this.loadHeartTexture();
        this.loadOpenRoomHorizontalWallsTextures();
        this.loadOpenRoomVerticalWallsTextures();
        this.loadClosedRoomHorizontalWallsTextures();
        this.loadClosedRoomVerticalWallsTextures();
        this.loadMonsterTextures();
        this.loadMonsterAuraTextures();
        this.loadKeyTexture();
        this.loadHealthTextures();
        this.loadOpenDoorTextures();
        this.loadClosedDoorTextures();
    }

    private void loadOpenDoorTextures() throws IOException {
        StaticTexture bottomTexture =
                new ImageTextureBuilder(3, 3)
                        .loadResourceData("/textures/door/open.png")
                        .setAnchorPoint(10, 1)
                        .build();

        StaticTexture leftTexture = bottomTexture.rotateRight();
        StaticTexture topTexture = leftTexture.rotateRight();
        StaticTexture rightTexture = topTexture.rotateRight();

        bundle.register("door.open.bottom", bottomTexture);
        bundle.register("door.open.left", leftTexture);
        bundle.register("door.open.top", topTexture);
        bundle.register("door.open.right", rightTexture);
    }

    private void loadClosedDoorTextures() throws IOException {
        StaticTexture horizontalTexture =
                new ImageTextureBuilder(21, 3)
                        .loadResourceData("/textures/door/closed.png")
                        .setAnchorPoint(10, 1)
                        .build();

        StaticTexture verticalTexture = horizontalTexture.rotateRight();

        bundle.register("door.closed.bottom", horizontalTexture);
        bundle.register("door.closed.left", verticalTexture);
        bundle.register("door.closed.top", horizontalTexture);
        bundle.register("door.closed.right", verticalTexture);
    }

    private void loadHealthTextures() throws IOException {
        StaticTexture idleTexture =
                new ImageTextureBuilder(60, 3)
                        .loadResourceData("/textures/health/idle.png")
                        .setAnchorPoint(-79, -3)
                        .build();

        StaticTexture changingTexture =
                new ImageTextureBuilder(60, 3)
                        .loadResourceData("/textures/health/changing.png")
                        .setAnchorPoint(-79, -3)
                        .build();

        bundle.register("health.idle", idleTexture);
        bundle.register("health.changing", changingTexture);
    }

    private void loadKeyTexture() throws IOException {
        StaticTexture keyTexture =
                new ImageTextureBuilder(7, 3)
                        .loadResourceData("/textures/key/horizontal.png")
                        .setAnchorPoint(3, 1)
                        .build();

        bundle.register("key", keyTexture);
    }

    private void loadMonsterTextures() throws IOException {
        StaticTexture normalTexture =
                new ImageTextureBuilder(10, 7)
                        .loadResourceData("/textures/monster/normal.png")
                        .setAnchorPoint(4, 6)
                        .build();

        StaticTexture damagedTexture =
                new ImageTextureBuilder(10, 7)
                        .loadResourceData("/textures/monster/damaged.png")
                        .setAnchorPoint(4, 6)
                        .build();

        bundle.register("monster.normal", normalTexture);
        bundle.register("monster.damaged", damagedTexture);
    }

    private void loadMonsterAuraTextures() throws IOException {
        StaticTexture idleTexture =
                new ImageTextureBuilder(24, 21)
                        .loadResourceData("/textures/monster/aura/idle.png")
                        .setAnchorPoint(11, 11)
                        .build();

        StaticTexture attackTexture =
                new ImageTextureBuilder(24, 21)
                        .loadResourceData("/textures/monster/aura/attack.png")
                        .setAnchorPoint(11, 11)
                        .build();

        bundle.register("monster.aura.idle", idleTexture);
        bundle.register("monster.aura.attack", attackTexture);
    }

    private void loadOpenRoomHorizontalWallsTextures() throws IOException {
        ImageTextureBuilder builder =
                new ImageTextureBuilder(143, 3)
                        .loadResourceData("/textures/room/walls/open/horizontal.png");

        StaticTexture topWallTexture = builder.setAnchorPoint(0, 0).build();
        StaticTexture bottomWallTexture = builder.setAnchorPoint(0, -63).build();

        bundle.register("room.walls.open.top", topWallTexture);
        bundle.register("room.walls.open.bottom", bottomWallTexture);
    }

    private void loadOpenRoomVerticalWallsTextures() throws IOException {
        ImageTextureBuilder builder =
                new ImageTextureBuilder(3, 66)
                        .loadResourceData("/textures/room/walls/open/vertical.png");

        StaticTexture leftWallTexture = builder.setAnchorPoint(0, 0).build();
        StaticTexture rightWallTexture = builder.setAnchorPoint(-140, 0).build();

        bundle.register("room.walls.open.left", leftWallTexture);
        bundle.register("room.walls.open.right", rightWallTexture);
    }

    private void loadClosedRoomVerticalWallsTextures() throws IOException {
        ImageTextureBuilder builder =
                new ImageTextureBuilder(3, 66)
                        .loadResourceData("/textures/room/walls/closed/vertical.png");

        StaticTexture leftWallTexture = builder.setAnchorPoint(0, 0).build();
        StaticTexture rightWallTexture = builder.setAnchorPoint(-140, 0).build();

        bundle.register("room.walls.closed.left", leftWallTexture);
        bundle.register("room.walls.closed.right", rightWallTexture);
    }

    private void loadClosedRoomHorizontalWallsTextures() throws IOException {
        ImageTextureBuilder builder =
                new ImageTextureBuilder(143, 3)
                        .loadResourceData("/textures/room/walls/closed/horizontal.png");

        StaticTexture topWallTexture = builder.setAnchorPoint(0, 0).build();
        StaticTexture bottomWallTexture = builder.setAnchorPoint(0, -63).build();

        bundle.register("room.walls.closed.top", topWallTexture);
        bundle.register("room.walls.closed.bottom", bottomWallTexture);
    }

    private void loadHeartTexture() throws IOException {
        StaticTexture heartTexture =
                new ImageTextureBuilder(5, 5)
                        .loadResourceData("/textures/heart.png")
                        .setAnchorPoint(2, 2)
                        .build();

        bundle.register("heart", heartTexture);
    }

    private void loadHudTexture() throws IOException {
        StaticTexture hudTexture =
                new ImageTextureBuilder(143, 9)
                        .loadResourceData("/textures/hud.png")
                        .setAnchorPoint(0, 0)
                        .build();

        bundle.register("hud", hudTexture);
    }

    private void loadRockTexture() throws IOException {
        StaticTexture rockTexture =
                new ImageTextureBuilder(9, 3)
                        .loadResourceData("/textures/rock.png")
                        .setAnchorPoint(4, 2)
                        .build();

        bundle.register("rock", rockTexture);
    }

    private void loadShibaTexture() throws IOException {
        StaticTexture shibaTexture =
                new ImageTextureBuilder(9, 10)
                        .loadResourceData("/textures/shiba.png")
                        .setAnchorPoint(5, 9)
                        .build();

        bundle.register("shiba", shibaTexture);
    }
}
