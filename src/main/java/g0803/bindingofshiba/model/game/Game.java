package g0803.bindingofshiba.model.game;

import g0803.bindingofshiba.Constants;
import g0803.bindingofshiba.map.DefaultMapProvider;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.model.game.elements.Player;
import g0803.bindingofshiba.model.game.room.Room;

public class Game {

    private final Player player;
    private Room currentRoom;

    public Game() {
        this.player =
                new Player(
                        new Vec2D(Constants.ROOM_WIDTH / 2D, Constants.ROOM_HEIGHT / 2D), 0, 40, 5);
        this.currentRoom = new DefaultMapProvider().getFirstRoom();
        //                new Room(
        //                        Constants.ROOM_WIDTH,
        //                        Constants.ROOM_HEIGHT,
        //                        Arrays.asList(
        //                                new Monster(new Vec2D(15, 40), 3, 1),
        //                                new Monster(new Vec2D(10, 50), 5, 2)),
        //                        List.of(new Obstacle(30, 15)));
        //
        //        Room room2 = new Room(Constants.ROOM_WIDTH, Constants.ROOM_HEIGHT, List.of(),
        // List.of());
        //        Door door = new Door(currentRoom, DoorPosition.TOP, room2, DoorPosition.BOTTOM);
        //        currentRoom.addDoor(door);
        //        room2.addDoor(door);
    }

    public Game(Player player, Room room) {
        this.player = player;
        this.currentRoom = room;
    }

    public Player getPlayer() {
        return player;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
}
