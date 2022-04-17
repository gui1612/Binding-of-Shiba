package g0803.bindingofshiba.controller.game.events;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.controller.Controller;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.events.game.MonsterCollisionWithObstacleEvent;
import g0803.bindingofshiba.math.BoundingBox;
import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.model.game.elements.Monster;
import g0803.bindingofshiba.model.game.elements.Obstacle;
import java.util.ArrayList;
import java.util.List;

public class MonsterToObstacleCollisionEventsController extends Controller<Game> {

    public MonsterToObstacleCollisionEventsController(Game model, IEventManager eventManager) {
        super(model, eventManager);
    }

    @Override
    public void tick(App app, double dt) {
        Bundle<BoundingBox> boundingBoxes = app.getBoundingBoxes();
        List<Monster> monsters = getModel().getCurrentRoom().getMonsters();
        List<Obstacle> obstacles = getModel().getCurrentRoom().getObstacles();
        ArrayList<MonsterCollisionWithObstacleEvent> eventsToDispatch = new ArrayList<>();

        BoundingBox monsterBox = boundingBoxes.get("monster");
        BoundingBox obstacleBox = boundingBoxes.get("rock");
        for (Monster monster : monsters) {
            BoundingBox monsterBoundingBox = monsterBox.translate(monster.getNextPosition(dt));

            for (Obstacle obstacle : obstacles) {
                BoundingBox obstacleBoundingBox = obstacleBox.translate(obstacle.getPosition());

                if (monsterBoundingBox.collides(obstacleBoundingBox))
                    eventsToDispatch.add(
                            new MonsterCollisionWithObstacleEvent(dt, app, monster, obstacle));
            }
        }

        for (MonsterCollisionWithObstacleEvent event : eventsToDispatch)
            getEventManager().dispatchEvent(event);
    }
}
