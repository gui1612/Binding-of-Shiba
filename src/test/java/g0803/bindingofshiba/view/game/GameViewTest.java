package g0803.bindingofshiba.view.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.bundles.DefaultFontsProvider;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.events.game.PlayerEnterDoorEvent;
import g0803.bindingofshiba.gui.GUI;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.model.game.elements.Player;
import g0803.bindingofshiba.model.game.room.Door;
import g0803.bindingofshiba.model.game.room.DoorPosition;
import g0803.bindingofshiba.model.game.room.Room;
import g0803.bindingofshiba.textures.ITexture;
import g0803.bindingofshiba.view.View;
import g0803.bindingofshiba.view.ViewFactory;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

public class GameViewTest {

    private static Font font;

    @BeforeAll
    public static void setup() throws IOException, FontFormatException {
        URL resource = DefaultFontsProvider.class.getResource("/fonts/cg-pixel-4x5.ttf");
        InputStream fileStream = resource.openStream();
        Font font = Font.createFont(Font.TRUETYPE_FONT, fileStream);
        GameViewTest.font = font.deriveFont(Font.PLAIN, 5);
    }

    @Test
    public void draw() {
        App app = Mockito.mock(App.class);
        GUI gui = Mockito.mock(GUI.class);

        Bundle<ITexture> textures = Mockito.mock(Bundle.class);

        ITexture healthBarTexture = Mockito.mock(ITexture.class);
        Mockito.when(healthBarTexture.getWidth()).thenReturn(20);

        ITexture overlayTexture = Mockito.mock(ITexture.class);
        Mockito.when(textures.get("health.idle")).thenReturn(healthBarTexture);
        Mockito.when(textures.get("hud")).thenReturn(overlayTexture);

        Mockito.when(app.getTextures()).thenReturn(textures);

        Bundle<Font> fonts = Mockito.mock(Bundle.class);
        Mockito.when(fonts.get("text")).thenReturn(font);

        Mockito.when(app.getFonts()).thenReturn(fonts);

        View<Player> playerView = Mockito.mock(View.class);
        ViewFactory<Player> playerViewFactory = Mockito.mock(ViewFactory.class);

        ViewFactory<Room> roomViewFactory = Mockito.mock(ViewFactory.class);
        View<Room> roomView = Mockito.mock(View.class);

        IEventManager eventManager = Mockito.mock(IEventManager.class);

        Mockito.when(playerViewFactory.create(Mockito.any(), Mockito.eq(eventManager)))
                .thenReturn(playerView);
        Mockito.when(roomViewFactory.create(Mockito.any(), Mockito.eq(eventManager)))
                .thenReturn(roomView);

        Player player = Mockito.mock(Player.class);
        Room room1 = Mockito.mock(Room.class);

        Game game = Mockito.mock(Game.class);
        Mockito.when(game.getPlayer()).thenReturn(player);
        Mockito.when(game.getCurrentRoom()).thenReturn(room1);
        Mockito.when(player.getHp()).thenReturn(20);
        Mockito.when(player.getMaxHp()).thenReturn(40);

        Mockito.when(playerView.getModel()).thenReturn(player);
        Mockito.when(roomView.getModel()).thenReturn(room1);

        GameView view = new GameView(game, eventManager, playerViewFactory, roomViewFactory);
        Mockito.verify(playerViewFactory).create(player, eventManager);
        Mockito.verify(roomViewFactory).create(room1, eventManager);

        Vec2D offset = new Vec2D(1, 2);
        view.draw(app, gui, offset);

        InOrder inOrder = Mockito.inOrder(gui, playerView, roomView);

        inOrder.verify(gui).fill(Mockito.any());
        inOrder.verify(roomView).draw(app, gui, offset.add(new Vec2D(0, 9)));
        inOrder.verify(playerView).draw(app, gui, offset.add(new Vec2D(0, 9)));

        inOrder.verify(gui).blit(6, 2, healthBarTexture);
        inOrder.verify(gui).blit(1, 2, overlayTexture);
        inOrder.verify(gui).blit(Mockito.eq(10), Mockito.eq(8), Mockito.any());
    }

    @Test
    public void playerEnterDoor() {
        App app = Mockito.mock(App.class);
        GUI gui = Mockito.mock(GUI.class);
        IEventManager manager = Mockito.mock(IEventManager.class);
        Bundle<ITexture> textures = Mockito.mock(Bundle.class);
        Mockito.when(textures.get("hud")).thenReturn(Mockito.mock(ITexture.class));
        Mockito.when(textures.get("health.idle")).thenReturn(Mockito.mock(ITexture.class));

        Bundle<Font> fonts = Mockito.mock(Bundle.class);
        Mockito.when(fonts.get("text")).thenReturn(font);

        Mockito.when(app.getFonts()).thenReturn(fonts);
        Mockito.when(app.getTextures()).thenReturn(textures);

        Game game = Mockito.mock(Game.class);

        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getNumberOfKeys()).thenReturn(3);
        Mockito.when(player.getHp()).thenReturn(6);
        Mockito.when(player.getMaxHp()).thenReturn(9);

        Room room1 = Mockito.mock(Room.class);
        Room room2 = Mockito.mock(Room.class);
        Door door = new Door(room1, DoorPosition.BOTTOM, room2, DoorPosition.TOP);

        Mockito.when(game.getPlayer()).thenReturn(player);
        Mockito.when(game.getCurrentRoom()).thenReturn(room1);

        View<Room> roomView1 = Mockito.mock(View.class);
        View<Room> roomView2 = Mockito.mock(View.class);
        ViewFactory<Room> roomViewFactory = Mockito.mock(ViewFactory.class);

        Mockito.when(roomViewFactory.create(Mockito.any(), Mockito.eq(manager)))
                .thenReturn(roomView1, roomView2);

        View<Player> playerView = Mockito.mock(View.class);
        ViewFactory<Player> playerViewFactory = Mockito.mock(ViewFactory.class);

        Mockito.when(playerViewFactory.create(player, manager)).thenReturn(playerView);

        GameView view = new GameView(game, manager, playerViewFactory, roomViewFactory);

        Mockito.verify(roomViewFactory).create(room1, manager);

        view.draw(app, gui, Vec2D.left());
        Mockito.verify(roomView1).draw(Mockito.eq(app), Mockito.eq(gui), Mockito.any());

        PlayerEnterDoorEvent event = new PlayerEnterDoorEvent(3, app, player, door);
        view.onPlayerEnterDoor(event);

        Mockito.verify(roomViewFactory).create(room2, manager);

        view.draw(app, gui, Vec2D.right());
        Mockito.verify(roomView2).draw(Mockito.eq(app), Mockito.eq(gui), Mockito.any());
    }
}
