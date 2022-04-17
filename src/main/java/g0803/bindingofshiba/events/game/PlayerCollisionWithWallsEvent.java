package g0803.bindingofshiba.events.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.events.Event;
import g0803.bindingofshiba.model.game.elements.Player;

public class PlayerCollisionWithWallsEvent extends Event {

    private final Player player;

    public PlayerCollisionWithWallsEvent(double dt, App app, Player player) {
        super(dt, app);

        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
