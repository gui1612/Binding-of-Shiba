package g0803.bindingofshiba.controller.game.events;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.controller.Controller;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.events.game.*;
import g0803.bindingofshiba.model.game.Game;
import java.util.Arrays;
import java.util.List;

public class CollisionEventsController extends Controller<Game> {

    private final List<? extends Controller<?>> controllers;

    public CollisionEventsController(Game model, IEventManager eventManager) {
        super(model, eventManager);

        this.controllers =
                Arrays.asList(
                        new MonsterToMonsterCollisionEventsController(model, eventManager),
                        new PlayerToMonsterCollisionEventsController(model, eventManager),
                        new MonsterToWallsCollisionEventsController(model, eventManager),
                        new PlayerToWallsCollisionEventsController(model, eventManager),
                        new MonsterToObstacleCollisionEventsController(model, eventManager),
                        new PlayerToObstacleCollisionEventsController(model, eventManager),
                        new ProjectileToMonsterCollisionEventsController(model, eventManager),
                        new ProjectileToStaticElementsCollisionEventsController(
                                model, eventManager));
    }

    public CollisionEventsController(
            Game model, IEventManager eventManager, List<? extends Controller<?>> controllers) {
        super(model, eventManager);

        this.controllers = controllers;
    }

    @Override
    public void tick(App app, double dt) {
        for (Controller<?> controller : controllers) controller.tick(app, dt);
    }
}
