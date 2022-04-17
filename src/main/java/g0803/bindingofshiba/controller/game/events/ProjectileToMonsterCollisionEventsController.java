package g0803.bindingofshiba.controller.game.events;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.controller.Controller;
import g0803.bindingofshiba.events.Event;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.events.game.ProjectileCollisionWithMonsterEvent;
import g0803.bindingofshiba.events.game.ProjectileDestroyedEvent;
import g0803.bindingofshiba.math.BoundingBox;
import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.model.game.elements.Monster;
import g0803.bindingofshiba.model.game.elements.Projectile;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProjectileToMonsterCollisionEventsController extends Controller<Game> {

    public ProjectileToMonsterCollisionEventsController(Game model, IEventManager eventManager) {
        super(model, eventManager);
    }

    @Override
    public void tick(App app, double dt) {
        Bundle<BoundingBox> boundingBoxes = app.getBoundingBoxes();
        Set<Projectile> projectiles = getModel().getCurrentRoom().getProjectiles();
        List<Monster> monsters = getModel().getCurrentRoom().getMonsters();

        ArrayList<Event> eventsToDispatch = new ArrayList<>();

        BoundingBox monsterBox = boundingBoxes.get("monster");
        BoundingBox projectileBox = boundingBoxes.get("heart");

        for (Projectile projectile : projectiles) {
            BoundingBox projectileBoundingBox =
                    projectileBox.translate(projectile.getNextPosition(dt));

            for (Monster monster : monsters) {
                BoundingBox monsterBoundingBox = monsterBox.translate(monster.getNextPosition(dt));

                if (monsterBoundingBox.collides(projectileBoundingBox)) {
                    eventsToDispatch.add(
                            new ProjectileCollisionWithMonsterEvent(dt, app, projectile, monster));
                    eventsToDispatch.add(
                            new ProjectileDestroyedEvent(
                                    dt, app, projectile, getModel().getCurrentRoom()));
                }
            }
        }

        for (Event event : eventsToDispatch) getEventManager().dispatchEvent(event);
    }
}
