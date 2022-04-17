package g0803.bindingofshiba.events.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.events.Event;
import g0803.bindingofshiba.model.game.elements.Monster;
import g0803.bindingofshiba.model.game.elements.Obstacle;

public class MonsterCollisionWithObstacleEvent extends Event {

    private final Monster monster;
    private final Obstacle obstacle;

    public MonsterCollisionWithObstacleEvent(
            double dt, App app, Monster monster, Obstacle obstacle) {
        super(dt, app);
        this.monster = monster;
        this.obstacle = obstacle;
    }

    public Monster getMonster() {
        return monster;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }
}
