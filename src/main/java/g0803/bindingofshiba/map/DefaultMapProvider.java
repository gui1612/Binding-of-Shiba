package g0803.bindingofshiba.map;

import g0803.bindingofshiba.Constants;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.model.game.elements.Monster;
import g0803.bindingofshiba.model.game.elements.Obstacle;
import g0803.bindingofshiba.model.game.room.Door;
import g0803.bindingofshiba.model.game.room.DoorPosition;
import g0803.bindingofshiba.model.game.room.LastRoom;
import g0803.bindingofshiba.model.game.room.Room;
import java.util.List;

public class DefaultMapProvider implements MapProvider {

    @Override
    public Room getFirstRoom() {
        Room trollRoom =
                new Room(
                        Constants.ROOM_WIDTH,
                        Constants.ROOM_HEIGHT,
                        null,
                        List.of(
                                new Obstacle(6, 7),
                                new Obstacle(50, 55),
                                new Obstacle(23, 44),
                                new Obstacle(80, 25)));

        Room fightRoom =
                new Room(
                        Constants.ROOM_WIDTH,
                        Constants.ROOM_HEIGHT,
                        List.of(
                                new Monster(new Vec2D(128, 13), 20, 5),
                                new Monster(new Vec2D(13, 13), 20, 5),
                                new Monster(new Vec2D(90, 55), 20, 5)),
                        List.of(
                                new Obstacle(8, 21),
                                new Obstacle(48, 13),
                                new Obstacle(123, 45),
                                new Obstacle(46, 32),
                                new Obstacle(34, 54),
                                new Obstacle(112, 11),
                                new Obstacle(72, 61)));

        Door fightToTroll = new Door(fightRoom, DoorPosition.TOP, trollRoom, DoorPosition.BOTTOM);
        fightRoom.addDoor(fightToTroll);
        trollRoom.addDoor(fightToTroll);

        Room allMonsters =
                new Room(
                        Constants.ROOM_WIDTH,
                        Constants.ROOM_HEIGHT,
                        List.of(
                                new Monster(new Vec2D(15, 15), 20, 5),
                                new Monster(new Vec2D(128, 15), 20, 5),
                                new Monster(new Vec2D(15, 56), 20, 5),
                                new Monster(new Vec2D(128, 56), 20, 5)),
                        List.of(
                                new Obstacle(
                                        new Vec2D(
                                                Constants.ROOM_WIDTH / 2D,
                                                Constants.ROOM_HEIGHT / 2D))));

        Door trollToAllMonsters =
                new Door(trollRoom, DoorPosition.RIGHT, allMonsters, DoorPosition.LEFT);
        trollRoom.addDoor(trollToAllMonsters);
        allMonsters.addDoor(trollToAllMonsters);

        Door allMonstersToTroll =
                new Door(allMonsters, DoorPosition.BOTTOM, trollRoom, DoorPosition.TOP);
        allMonsters.addDoor(allMonstersToTroll);
        trollRoom.addDoor(allMonstersToTroll);

        Door allMonstersToFight =
                new Door(allMonsters, DoorPosition.TOP, fightRoom, DoorPosition.RIGHT);
        allMonsters.addDoor(allMonstersToFight);
        fightRoom.addDoor(allMonstersToFight);

        LastRoom lastRoom = new LastRoom(Constants.ROOM_WIDTH, Constants.ROOM_HEIGHT);

        Door toEnd = new Door(allMonsters, DoorPosition.RIGHT, lastRoom, DoorPosition.LEFT);
        allMonsters.addDoor(toEnd);
        lastRoom.addDoor(toEnd);

        return fightRoom;
    }
}
