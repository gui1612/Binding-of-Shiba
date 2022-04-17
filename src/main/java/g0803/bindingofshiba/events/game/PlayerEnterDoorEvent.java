package g0803.bindingofshiba.events.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.events.Event;
import g0803.bindingofshiba.model.game.elements.Player;
import g0803.bindingofshiba.model.game.room.Door;

public class PlayerEnterDoorEvent extends Event {

    private final Player player;
    private final Door door;

    public PlayerEnterDoorEvent(double dt, App app, Player player, Door door) {
        super(dt, app);

        this.player = player;
        this.door = door;
    }

    public Player getPlayer() {
        return player;
    }

    public Door getDoor() {
        return door;
    }
}
