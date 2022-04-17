package g0803.bindingofshiba.controller.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.controller.Controller;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.events.Observer;
import g0803.bindingofshiba.events.game.MonsterDamagedEvent;
import g0803.bindingofshiba.events.game.PlayerEnterDoorEvent;
import g0803.bindingofshiba.events.game.PlayerUnlockDoorEvent;
import g0803.bindingofshiba.math.BoundingBox;
import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.model.game.elements.Player;
import g0803.bindingofshiba.model.game.room.Door;
import g0803.bindingofshiba.model.game.room.DoorPosition;
import g0803.bindingofshiba.model.game.room.Room;

public class RoomController extends Controller<Game> implements Observer {

    public RoomController(Game model, IEventManager eventManager) {
        super(model, eventManager);

        getEventManager().addObserver(this);
    }

    private void handlePlayerToDoorCollisions(App app, DoorPosition position, double dt) {
        Bundle<BoundingBox> boundingBoxes = app.getBoundingBoxes();
        Player player = getModel().getPlayer();
        Room room = getModel().getCurrentRoom();

        Door door = getModel().getCurrentRoom().getDoors().get(position);
        if (door == null) return;

        BoundingBox playerBox = boundingBoxes.get("shiba");
        BoundingBox doorBox =
                boundingBoxes.get(
                        door.getUnlocked() ? position.getOpenKey() : position.getClosedKey());

        BoundingBox playerBoundingBox = playerBox.translate(player.getNextPosition(dt));
        BoundingBox doorBoundingBox = doorBox.translate(door.getPositionByWall(room));

        if (!playerBoundingBox.collides(doorBoundingBox)) return;

        if (door.getUnlocked()) {
            PlayerEnterDoorEvent event = new PlayerEnterDoorEvent(dt, app, player, door);
            getEventManager().dispatchEvent(event);

            getModel().setCurrentRoom(door.getOtherRoom(room));
        } else {
            PlayerUnlockDoorEvent event = new PlayerUnlockDoorEvent(dt, app, player, door);
            getEventManager().dispatchEvent(event);

            if (event.isCancelled()) return;

            door.unlock();
        }
    }

    private void handlePlayerToDoorCollisions(App app, double dt) {
        for (DoorPosition position : DoorPosition.values()) {
            handlePlayerToDoorCollisions(app, position, dt);
        }
    }

    @Override
    public void tick(App app, double dt) {
        handlePlayerToDoorCollisions(app, dt);
    }

    @Override
    public void onMonsterDamaged(MonsterDamagedEvent event) {
        if (!event.getMonster().isAlive()) {
            getModel().getCurrentRoom().getMonsters().remove(event.getMonster());
        }
    }
}
