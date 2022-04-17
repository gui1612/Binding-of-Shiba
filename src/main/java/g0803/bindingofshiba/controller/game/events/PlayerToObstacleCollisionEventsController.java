package g0803.bindingofshiba.controller.game.events;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.controller.Controller;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.events.game.PlayerCollisionWithObstacleEvent;
import g0803.bindingofshiba.math.BoundingBox;
import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.model.game.elements.Obstacle;
import g0803.bindingofshiba.model.game.elements.Player;
import java.util.ArrayList;
import java.util.List;

public class PlayerToObstacleCollisionEventsController extends Controller<Game> {

    public PlayerToObstacleCollisionEventsController(Game model, IEventManager eventManager) {
        super(model, eventManager);
    }

    @Override
    public void tick(App app, double dt) {
        Bundle<BoundingBox> boundingBoxes = app.getBoundingBoxes();
        Player player = getModel().getPlayer();
        List<Obstacle> obstacles = getModel().getCurrentRoom().getObstacles();
        ArrayList<PlayerCollisionWithObstacleEvent> eventsToDispatch = new ArrayList<>();

        BoundingBox playerBox = boundingBoxes.get("shiba");
        BoundingBox playerBoundingBox = playerBox.translate(player.getNextPosition(dt));

        BoundingBox obstacleBox = boundingBoxes.get("rock");
        for (Obstacle obstacle : obstacles) {
            BoundingBox obstacleBoundingBox = obstacleBox.translate(obstacle.getPosition());

            if (playerBoundingBox.collides(obstacleBoundingBox))
                eventsToDispatch.add(
                        new PlayerCollisionWithObstacleEvent(dt, app, player, obstacle));
        }

        for (PlayerCollisionWithObstacleEvent event : eventsToDispatch)
            getEventManager().dispatchEvent(event);
    }
}
