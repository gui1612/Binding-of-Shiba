package g0803.bindingofshiba.controller.game.events;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.controller.Controller;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.events.game.ProjectileDestroyedEvent;
import g0803.bindingofshiba.math.BoundingBox;
import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.model.game.elements.Obstacle;
import g0803.bindingofshiba.model.game.elements.Projectile;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProjectileToStaticElementsCollisionEventsController extends Controller<Game> {

    public ProjectileToStaticElementsCollisionEventsController(
            Game model, IEventManager eventManager) {
        super(model, eventManager);
    }

    private void handleCollisionsWithRoom(App app, double dt) {
        Bundle<BoundingBox> boundingBoxes = app.getBoundingBoxes();
        Set<Projectile> projectiles = getModel().getCurrentRoom().getProjectiles();
        ArrayList<ProjectileDestroyedEvent> eventsToDispatch = new ArrayList<>();

        BoundingBox projectileBox = boundingBoxes.get("heart");
        BoundingBox roomBox = boundingBoxes.get("room");

        for (Projectile projectile : projectiles) {
            BoundingBox projectileBoundingBox =
                    projectileBox.translate(projectile.getNextPosition(dt));

            if (!roomBox.contains(projectileBoundingBox))
                eventsToDispatch.add(
                        new ProjectileDestroyedEvent(
                                dt, app, projectile, getModel().getCurrentRoom()));
        }

        for (ProjectileDestroyedEvent event : eventsToDispatch)
            getEventManager().dispatchEvent(event);
    }

    private void handleCollisionsWithObstacles(App app, double dt) {
        Bundle<BoundingBox> boundingBoxes = app.getBoundingBoxes();

        Set<Projectile> projectiles = getModel().getCurrentRoom().getProjectiles();
        List<Obstacle> obstacles = getModel().getCurrentRoom().getObstacles();

        ArrayList<ProjectileDestroyedEvent> eventsToDispatch = new ArrayList<>();

        BoundingBox projectileBox = boundingBoxes.get("heart");
        BoundingBox obstacleBox = boundingBoxes.get("rock");

        for (Projectile projectile : projectiles) {
            BoundingBox projectileBoundingBox =
                    projectileBox.translate(projectile.getNextPosition(dt));

            for (Obstacle obstacle : obstacles) {
                BoundingBox obstacleBoundingBox = obstacleBox.translate(obstacle.getPosition());

                if (obstacleBoundingBox.collides(projectileBoundingBox))
                    eventsToDispatch.add(
                            new ProjectileDestroyedEvent(
                                    dt, app, projectile, getModel().getCurrentRoom()));
            }
        }

        for (ProjectileDestroyedEvent event : eventsToDispatch)
            getEventManager().dispatchEvent(event);
    }

    @Override
    public void tick(App app, double dt) {
        handleCollisionsWithRoom(app, dt);
        handleCollisionsWithObstacles(app, dt);
    }
}
