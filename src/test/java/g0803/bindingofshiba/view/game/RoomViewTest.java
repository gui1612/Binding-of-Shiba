package g0803.bindingofshiba.view.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.gui.GUI;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.model.game.elements.Monster;
import g0803.bindingofshiba.model.game.elements.Obstacle;
import g0803.bindingofshiba.model.game.elements.Projectile;
import g0803.bindingofshiba.model.game.room.Door;
import g0803.bindingofshiba.model.game.room.DoorPosition;
import g0803.bindingofshiba.model.game.room.Room;
import g0803.bindingofshiba.textures.ITexture;
import g0803.bindingofshiba.view.View;
import g0803.bindingofshiba.view.ViewFactory;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

public class RoomViewTest {

    @Test
    public void blitsDoorClosed() {
        Room r1 = new Room(143, 75, null, null);
        Room r2 = new Room(143, 75, null, null);

        Door door = new Door(r1, DoorPosition.TOP, r2, DoorPosition.BOTTOM);
        r1.addDoor(door);

        App app = Mockito.mock(App.class);
        GUI gui = Mockito.mock(GUI.class);

        Bundle<ITexture> textures = Mockito.mock(Bundle.class);
        ITexture texture = Mockito.mock(ITexture.class);

        Mockito.when(app.getTextures()).thenReturn(textures);
        Mockito.when(textures.get("door.closed.top")).thenReturn(texture);

        RoomView view = new RoomView(r1, Mockito.mock(IEventManager.class));

        view.draw(app, gui, new Vec2D(1, 2));

        Mockito.verify(gui, Mockito.times(1)).blit(72, 3, texture);
    }

    @Test
    public void blitsObstacles() {
        List<Obstacle> obstacles = Arrays.asList(new Obstacle(40, 40), new Obstacle(70, 80));
        Room r1 = new Room(143, 75, null, obstacles);

        App app = Mockito.mock(App.class);
        GUI gui = Mockito.mock(GUI.class);
        IEventManager eventManager = Mockito.mock(IEventManager.class);

        Bundle<ITexture> textures = Mockito.mock(Bundle.class);
        ITexture texture1 = Mockito.mock(ITexture.class);
        ITexture texture2 = Mockito.mock(ITexture.class);
        ITexture texture3 = Mockito.mock(ITexture.class);
        ITexture texture4 = Mockito.mock(ITexture.class);

        Mockito.when(app.getTextures()).thenReturn(textures);
        Mockito.when(textures.get("room.walls.closed.top")).thenReturn(texture1);
        Mockito.when(textures.get("room.walls.closed.bottom")).thenReturn(texture2);
        Mockito.when(textures.get("room.walls.closed.left")).thenReturn(texture3);
        Mockito.when(textures.get("room.walls.closed.right")).thenReturn(texture4);

        ViewFactory<Monster> monsterViewFactory = Mockito.mock(ViewFactory.class);
        ViewFactory<Obstacle> obstacleViewFactory = Mockito.mock(ViewFactory.class);
        ViewFactory<Projectile> projectileViewFactory = Mockito.mock(ViewFactory.class);

        View<Obstacle> obstacleView1 = Mockito.mock(View.class);
        View<Obstacle> obstacleView2 = Mockito.mock(View.class);
        Mockito.when(obstacleViewFactory.create(Mockito.any(), Mockito.eq(eventManager)))
                .thenReturn(obstacleView1, obstacleView2);

        RoomView roomView =
                new RoomView(
                        r1,
                        eventManager,
                        monsterViewFactory,
                        obstacleViewFactory,
                        projectileViewFactory);

        Vec2D offset = new Vec2D(0, 9);
        roomView.draw(app, gui, offset);

        InOrder inOrder = Mockito.inOrder(gui, obstacleView1, obstacleView2);

        inOrder.verify(gui).blit(0, 9, texture1);
        inOrder.verify(gui).blit(0, 9, texture2);
        inOrder.verify(gui).blit(0, 9, texture3);
        inOrder.verify(gui).blit(0, 9, texture4);
        inOrder.verify(obstacleView1).draw(app, gui, offset);
        inOrder.verify(obstacleView2).draw(app, gui, offset);
    }

    @Test
    public void blitsMonsters() {
        List<Monster> monsters =
                Arrays.asList(
                        new Monster(new Vec2D(70, 55), 95, 50),
                        new Monster(new Vec2D(69, 20), 60, 50));
        Room r1 = new Room(143, 75, monsters, null);

        App app = Mockito.mock(App.class);
        GUI gui = Mockito.mock(GUI.class);

        IEventManager eventManager = Mockito.mock(IEventManager.class);

        Bundle<ITexture> textures = Mockito.mock(Bundle.class);
        ITexture texture1 = Mockito.mock(ITexture.class);
        ITexture texture2 = Mockito.mock(ITexture.class);
        ITexture texture3 = Mockito.mock(ITexture.class);
        ITexture texture4 = Mockito.mock(ITexture.class);

        Mockito.when(app.getTextures()).thenReturn(textures);
        Mockito.when(textures.get("room.walls.closed.top")).thenReturn(texture1);
        Mockito.when(textures.get("room.walls.closed.bottom")).thenReturn(texture2);
        Mockito.when(textures.get("room.walls.closed.left")).thenReturn(texture3);
        Mockito.when(textures.get("room.walls.closed.right")).thenReturn(texture4);

        ViewFactory<Monster> monsterViewFactory = Mockito.mock(ViewFactory.class);
        ViewFactory<Obstacle> obstacleViewFactory = Mockito.mock(ViewFactory.class);
        ViewFactory<Projectile> projectileViewFactory = Mockito.mock(ViewFactory.class);

        View<Monster> monsterView1 = Mockito.mock(View.class);
        View<Monster> monsterView2 = Mockito.mock(View.class);
        Mockito.when(monsterViewFactory.create(Mockito.any(), Mockito.eq(eventManager)))
                .thenReturn(monsterView1, monsterView2);

        RoomView roomView =
                new RoomView(
                        r1,
                        eventManager,
                        monsterViewFactory,
                        obstacleViewFactory,
                        projectileViewFactory);

        Vec2D offset = new Vec2D(0, 9);
        roomView.draw(app, gui, offset);

        InOrder inOrder = Mockito.inOrder(gui, monsterView1, monsterView2);

        inOrder.verify(gui).blit(0, 9, texture1);
        inOrder.verify(gui).blit(0, 9, texture2);
        inOrder.verify(gui).blit(0, 9, texture3);
        inOrder.verify(gui).blit(0, 9, texture4);
        inOrder.verify(monsterView1).draw(app, gui, offset);
        inOrder.verify(monsterView2).draw(app, gui, offset);
    }
}
