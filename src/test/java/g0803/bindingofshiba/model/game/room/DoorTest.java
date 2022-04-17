package g0803.bindingofshiba.model.game.room;

import g0803.bindingofshiba.math.Vec2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DoorTest {

    @Test
    public void getPositionByWall() {
        Room room1 = new Room(10, 20, null, null);
        Room room2 = new Room(30, 10, null, null);
        Door door = new Door(room1, DoorPosition.TOP, room2, DoorPosition.RIGHT);

        room1.addDoor(door);
        room2.addDoor(door);

        Assertions.assertTrue(door.getPositionByWall(room1).isSimilar(new Vec2D(5, 1)));
        Assertions.assertTrue(door.getPositionByWall(room2).isSimilar(new Vec2D(28, 5)));
    }

    @Test
    public void getPositionByWall2() {
        Room room1 = new Room(10, 20, null, null);
        Room room2 = new Room(30, 10, null, null);
        Door door = new Door(room1, DoorPosition.LEFT, room2, DoorPosition.BOTTOM);

        room1.addDoor(door);
        room2.addDoor(door);

        Assertions.assertTrue(door.getPositionByWall(room1).isSimilar(new Vec2D(1, 10)));
        Assertions.assertTrue(door.getPositionByWall(room2).isSimilar(new Vec2D(15, 8)));
    }

    @Test
    public void getOtherRoom() {
        Room room1 = new Room(10, 20, null, null);
        Room room2 = new Room(30, 10, null, null);
        Door door = new Door(room1, DoorPosition.LEFT, room2, DoorPosition.BOTTOM);

        room1.addDoor(door);
        room2.addDoor(door);

        Assertions.assertEquals(room2, door.getOtherRoom(room1));
        Assertions.assertEquals(room1, door.getOtherRoom(room2));
    }
}
