package g0803.bindingofshiba.controller.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.events.EventManager;
import g0803.bindingofshiba.events.game.PlayerEnterDoorEvent;
import g0803.bindingofshiba.events.game.PlayerUnlockDoorEvent;
import g0803.bindingofshiba.math.BoundingBox;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.model.game.elements.Player;
import g0803.bindingofshiba.model.game.room.Door;
import g0803.bindingofshiba.model.game.room.DoorPosition;
import g0803.bindingofshiba.model.game.room.Room;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RoomControllerTest {

    @Test
    public void tick() {
        App app = Mockito.mock(App.class);
        EventManager manager = Mockito.mock(EventManager.class);

        Bundle<BoundingBox> boundingBoxes = Mockito.mock(Bundle.class);
        Mockito.when(boundingBoxes.get("shiba")).thenReturn(new BoundingBox(-3, -5, 6, 10));
        Mockito.when(boundingBoxes.get("door.open.bottom"))
                .thenReturn(new BoundingBox(-2, -1, 3, 5));
        Mockito.when(app.getBoundingBoxes()).thenReturn(boundingBoxes);

        Player player = Mockito.mock(Player.class);
        Game game = Mockito.mock(Game.class);

        Room room1 = new Room(10, 10, List.of(), List.of());
        Room room2 = new Room(10, 10, List.of(), List.of());
        Door door = new Door(room1, DoorPosition.BOTTOM, room2, DoorPosition.TOP);

        room1.addDoor(door);
        room2.addDoor(door);
        door.unlock();

        Mockito.when(game.getCurrentRoom()).thenReturn(room1);
        Mockito.when(game.getPlayer()).thenReturn(player);
        Mockito.when(player.getNextPosition(3)).thenReturn(new Vec2D(1, 2));

        RoomController controller = new RoomController(game, manager);

        controller.tick(app, 3);

        Mockito.verify(manager)
                .dispatchEvent(
                        Mockito.argThat(
                                event ->
                                        event instanceof PlayerEnterDoorEvent e
                                                && e.getTickTime() == 3
                                                && e.getApp() == app
                                                && e.getPlayer() == player
                                                && e.getDoor() == door));

        Mockito.verify(game).setCurrentRoom(room2);
    }

    @Test
    public void tick2() {
        App app = Mockito.mock(App.class);
        EventManager manager = Mockito.mock(EventManager.class);

        Bundle<BoundingBox> boundingBoxes = Mockito.mock(Bundle.class);
        Mockito.when(boundingBoxes.get("shiba")).thenReturn(new BoundingBox(-3, -5, 6, 10));
        Mockito.when(boundingBoxes.get("door.closed.bottom"))
                .thenReturn(new BoundingBox(-2, -1, 3, 5));
        Mockito.when(app.getBoundingBoxes()).thenReturn(boundingBoxes);

        Player player = Mockito.mock(Player.class);
        Game game = Mockito.mock(Game.class);

        Room room1 = new Room(10, 10, List.of(), List.of());
        Room room2 = new Room(10, 10, List.of(), List.of());
        Door door = new Door(room1, DoorPosition.BOTTOM, room2, DoorPosition.TOP);

        room1.addDoor(door);
        room2.addDoor(door);

        Mockito.when(game.getCurrentRoom()).thenReturn(room1);
        Mockito.when(game.getPlayer()).thenReturn(player);
        Mockito.when(player.getNextPosition(3)).thenReturn(new Vec2D(1, 2));

        RoomController controller = new RoomController(game, manager);

        controller.tick(app, 3);

        Mockito.verify(manager)
                .dispatchEvent(
                        Mockito.argThat(
                                event ->
                                        event instanceof PlayerUnlockDoorEvent e
                                                && e.getTickTime() == 3
                                                && e.getApp() == app
                                                && e.getPlayer() == player
                                                && e.getDoor() == door));

        Assertions.assertTrue(door.getUnlocked());
    }
}
