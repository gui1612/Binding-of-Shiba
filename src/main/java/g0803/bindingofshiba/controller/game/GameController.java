package g0803.bindingofshiba.controller.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.controller.Controller;
import g0803.bindingofshiba.controller.game.events.CollisionEventsController;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.events.Observer;
import g0803.bindingofshiba.events.game.PlayerEnterDoorEvent;
import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.model.game.room.Door;
import g0803.bindingofshiba.model.game.room.LastRoom;
import g0803.bindingofshiba.model.game.room.Room;
import g0803.bindingofshiba.state.end.GameOverState;
import java.util.Arrays;
import java.util.List;

public class GameController extends Controller<Game> implements Observer {

    private final List<? extends Controller<?>> controllers;

    public GameController(Game model, IEventManager eventManager) {
        super(model, eventManager);

        this.controllers =
                Arrays.asList(
                        new CollisionEventsController(getModel(), getEventManager()),
                        new PlayerController(getModel(), getEventManager()),
                        new MonsterController(getModel(), getEventManager()),
                        new ProjectileController(getModel(), getEventManager()),
                        new RoomController(getModel(), getEventManager()));

        getEventManager().addObserver(this);
    }

    public GameController(
            Game model, IEventManager eventManager, List<? extends Controller<?>> controllers) {
        super(model, eventManager);

        this.controllers = controllers;
    }

    @Override
    public void tick(App app, double dt) {
        for (Controller<?> controller : controllers) controller.tick(app, dt);
    }

    @Override
    public void onPlayerEnterDoor(PlayerEnterDoorEvent event) {
        Room currentRoom = getModel().getCurrentRoom();
        Door door = event.getDoor();

        Room destination = door.getOtherRoom(currentRoom);
        if (destination instanceof LastRoom) event.getApp().setState(new GameOverState(true));
    }
}
