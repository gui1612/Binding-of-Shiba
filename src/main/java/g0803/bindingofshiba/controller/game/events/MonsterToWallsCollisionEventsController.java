package g0803.bindingofshiba.controller.game.events;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.controller.Controller;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.events.game.MonsterCollisionWithWallsEvent;
import g0803.bindingofshiba.math.BoundingBox;
import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.model.game.elements.Monster;
import java.util.ArrayList;
import java.util.List;

public class MonsterToWallsCollisionEventsController extends Controller<Game> {

    public MonsterToWallsCollisionEventsController(Game model, IEventManager eventManager) {
        super(model, eventManager);
    }

    @Override
    public void tick(App app, double dt) {
        Bundle<BoundingBox> boundingBoxes = app.getBoundingBoxes();
        List<Monster> monsters = getModel().getCurrentRoom().getMonsters();
        ArrayList<MonsterCollisionWithWallsEvent> eventsToDispatch = new ArrayList<>();

        BoundingBox monsterBox = boundingBoxes.get("monster");
        BoundingBox roomBox = boundingBoxes.get("room");
        for (Monster monster : monsters) {
            BoundingBox monsterBoundingBox = monsterBox.translate(monster.getNextPosition(dt));

            if (!roomBox.contains(monsterBoundingBox))
                eventsToDispatch.add(new MonsterCollisionWithWallsEvent(dt, app, monster));
        }

        for (MonsterCollisionWithWallsEvent event : eventsToDispatch)
            getEventManager().dispatchEvent(event);
    }
}
