package g0803.bindingofshiba.events.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.events.Event;
import g0803.bindingofshiba.model.game.elements.Obstacle;
import g0803.bindingofshiba.model.game.elements.Player;

public class PlayerCollisionWithObstacleEvent extends Event {

    private final Player player;
    private final Obstacle obstacle;

    public PlayerCollisionWithObstacleEvent(double dt, App app, Player player, Obstacle obstacle) {
        super(dt, app);
        this.player = player;
        this.obstacle = obstacle;
    }

    public Player getPlayer() {
        return player;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }
}
