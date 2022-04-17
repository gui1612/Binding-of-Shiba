package g0803.bindingofshiba.controller.game.events;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.controller.Controller;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.events.game.PlayerCollisionWithWallsEvent;
import g0803.bindingofshiba.math.BoundingBox;
import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.model.game.elements.Player;

public class PlayerToWallsCollisionEventsController extends Controller<Game> {

    public PlayerToWallsCollisionEventsController(Game model, IEventManager eventManager) {
        super(model, eventManager);
    }

    @Override
    public void tick(App app, double dt) {
        Bundle<BoundingBox> boundingBoxes = app.getBoundingBoxes();
        Player player = getModel().getPlayer();

        BoundingBox playerBox = boundingBoxes.get("shiba");
        BoundingBox roomBox = boundingBoxes.get("room");

        BoundingBox playerBoundingBox = playerBox.translate(player.getNextPosition(dt));
        if (!roomBox.contains(playerBoundingBox))
            getEventManager().dispatchEvent(new PlayerCollisionWithWallsEvent(dt, app, player));
    }
}
