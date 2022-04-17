package g0803.bindingofshiba.model.game.room;

import g0803.bindingofshiba.math.Vec2D;

public class Door {

    private Room originRoom;
    private Room destinationRoom;
    private DoorPosition originPosition;
    private DoorPosition destinationPosition;
    private boolean isUnlocked = false;

    public Vec2D getPositionByWall(Room room) {
        if (room != originRoom && room != destinationRoom) {
            throw new IllegalArgumentException("Room is not origin nor destination");
        }

        DoorPosition position = getDoorPosition(room);
        return switch (position) {
            case TOP -> new Vec2D(room.getWidth() / 2, 1);
            case BOTTOM -> new Vec2D(room.getWidth() / 2, room.getHeight() - 2);
            case RIGHT -> new Vec2D(room.getWidth() - 2, room.getHeight() / 2);
            case LEFT -> new Vec2D(1, room.getHeight() / 2);
        };
    }

    public Door(
            Room origin,
            DoorPosition originPosition,
            Room destination,
            DoorPosition destinationPosition) {
        this.originRoom = origin;
        this.originPosition = originPosition;
        this.destinationRoom = destination;
        this.destinationPosition = destinationPosition;
    }

    public Room getOriginRoom() {
        return this.originRoom;
    }

    public DoorPosition getOriginPosition() {
        return this.originPosition;
    }

    public Room getDestinationRoom() {
        return this.destinationRoom;
    }

    public DoorPosition getDestinationPosition() {
        return this.destinationPosition;
    }

    public DoorPosition getDoorPosition(Room room) {
        return room == originRoom ? originPosition : destinationPosition;
    }

    public Room getOtherRoom(Room room) {
        return room == destinationRoom ? originRoom : destinationRoom;
    }

    public boolean getUnlocked() {
        return this.isUnlocked;
    }

    public void unlock() {
        this.isUnlocked = true;
    }
}
