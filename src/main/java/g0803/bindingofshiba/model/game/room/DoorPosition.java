package g0803.bindingofshiba.model.game.room;

public enum DoorPosition {
    TOP("top"),
    BOTTOM("bottom"),
    LEFT("left"),
    RIGHT("right");

    private final String side;

    DoorPosition(String side) {
        this.side = side;
    }

    public String getOpenKey() {
        return "door.open." + side;
    }

    public String getClosedKey() {
        return "door.closed." + side;
    }
}
