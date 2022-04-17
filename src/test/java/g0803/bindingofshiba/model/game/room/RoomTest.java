package g0803.bindingofshiba.model.game.room;

import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.model.game.elements.Monster;
import g0803.bindingofshiba.model.game.elements.Obstacle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class RoomTest {

    @Test
    public void addDoor() {
        List<Monster> monstersRoom1 =
                Arrays.asList(
                        new Monster(new Vec2D(40, 7), 60, 30),
                        new Monster(new Vec2D(2, 6), 50, 40));
        List<Monster> monstersRoom2 = Arrays.asList(new Monster(new Vec2D(5, 5), 80, 3));
        List<Obstacle> obstacles = Arrays.asList(new Obstacle(4, 6), new Obstacle(10, 7));

        Room r1 = new Room(75, 50, monstersRoom1, null);
        Room r2 = new Room(60, 90, monstersRoom2, null);
        Room r3 = new Room(40, 70, null, obstacles);

        Assertions.assertEquals(new HashMap<>(), r1.getDoors());
        Assertions.assertEquals(monstersRoom1, r1.getMonsters());

        Door door1 = new Door(r1, DoorPosition.TOP, r2, DoorPosition.BOTTOM);
        Door door2 = new Door(r1, DoorPosition.BOTTOM, r3, DoorPosition.TOP);
        Door door3 = new Door(r2, DoorPosition.LEFT, r3, DoorPosition.RIGHT);

        r1.addDoor(door1);
        r1.addDoor(door2);

        Assertions.assertEquals(2, r1.getDoors().size());

        Assertions.assertThrows(
                IllegalArgumentException.class,
                new Executable() {
                    @Override
                    public void execute() throws Throwable {
                        r1.addDoor(door3);
                    }
                });

        Assertions.assertEquals(r1, r1.getDoors().get(DoorPosition.TOP).getOriginRoom());
        Assertions.assertEquals(r2, r1.getDoors().get(DoorPosition.TOP).getDestinationRoom());
    }
}
