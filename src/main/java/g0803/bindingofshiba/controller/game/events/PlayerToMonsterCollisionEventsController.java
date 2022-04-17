package g0803.bindingofshiba.controller.game.events;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.controller.Controller;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.events.game.PlayerCollisionWithMonsterEvent;
import g0803.bindingofshiba.math.BoundingBox;
import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.model.game.elements.Monster;
import g0803.bindingofshiba.model.game.elements.Player;
import java.util.ArrayList;
import java.util.List;

public class PlayerToMonsterCollisionEventsController extends Controller<Game> {

    public PlayerToMonsterCollisionEventsController(Game model, IEventManager eventManager) {
        super(model, eventManager);
    }

    @Override
    public void tick(App app, double dt) {
        Bundle<BoundingBox> boundingBoxes = app.getBoundingBoxes();
        Player player = getModel().getPlayer();
        List<Monster> monsters = getModel().getCurrentRoom().getMonsters();
        ArrayList<PlayerCollisionWithMonsterEvent> eventsToDispatch = new ArrayList<>();

        BoundingBox playerBox = boundingBoxes.get("shiba");
        BoundingBox playerBoundingBox = playerBox.translate(player.getNextPosition(dt));

        BoundingBox monsterBox = boundingBoxes.get("monster");
        for (Monster monster : monsters) {
            BoundingBox monsterBoundingBox = monsterBox.translate(monster.getNextPosition(dt));

            if (playerBoundingBox.collides(monsterBoundingBox))
                eventsToDispatch.add(new PlayerCollisionWithMonsterEvent(dt, app, player, monster));
        }

        for (PlayerCollisionWithMonsterEvent event : eventsToDispatch)
            getEventManager().dispatchEvent(event);
    }
}
