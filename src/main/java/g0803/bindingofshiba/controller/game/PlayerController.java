package g0803.bindingofshiba.controller.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.controller.Controller;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.events.Observer;
import g0803.bindingofshiba.events.game.*;
import g0803.bindingofshiba.gui.keyboard.Keyboard;
import g0803.bindingofshiba.math.BoundingBox;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.model.game.elements.Player;
import g0803.bindingofshiba.model.game.room.Door;
import g0803.bindingofshiba.model.game.room.Room;
import g0803.bindingofshiba.state.end.GameOverState;

public class PlayerController extends Controller<Game> implements Observer {

    public PlayerController(Game model, IEventManager eventManager) {
        super(model, eventManager);
        eventManager.addObserver(this);
    }

    private Vec2D getNextPlayerAcceleration(Keyboard keyboard, Vec2D currentVelocity) {
        Vec2D direction =
                switch (keyboard.getPressedKey()) {
                    case W -> Vec2D.up();
                    case S -> Vec2D.down();
                    case A -> Vec2D.left();
                    case D -> Vec2D.right();
                    default -> Vec2D.zero();
                };

        Vec2D force = direction.scale(200);
        Vec2D drag = currentVelocity.scale(4);

        return force.subtract(drag);
    }

    @Override
    public void tick(App app, double dt) {
        getModel().getPlayer().move(dt);
        getModel()
                .getPlayer()
                .setAcceleration(
                        getNextPlayerAcceleration(
                                app.getKeyboard(), getModel().getPlayer().getVelocity()));
    }

    @Override
    public void onPlayerCollisionWithMonster(PlayerCollisionWithMonsterEvent event) {
        Player player = event.getPlayer();
        player.setAcceleration(Vec2D.zero());
        player.setVelocity(Vec2D.zero());

        player.decreaseHpByAmount(5);
        if (!player.isAlive()) {
            event.getApp().setState(new GameOverState(false));
        }
    }

    @Override
    public void onPlayerCollisionWithObstacle(PlayerCollisionWithObstacleEvent event) {
        Player player = event.getPlayer();
        player.setAcceleration(Vec2D.zero());
        player.setVelocity(Vec2D.zero());
    }

    @Override
    public void onPlayerCollisionWithWalls(PlayerCollisionWithWallsEvent event) {
        Player player = event.getPlayer();
        player.setAcceleration(Vec2D.zero());
        player.setVelocity(Vec2D.zero());
    }

    @Override
    public void onPlayerEnterDoor(PlayerEnterDoorEvent event) {
        Room destination = event.getDoor().getOtherRoom(getModel().getCurrentRoom());
        Door door = event.getDoor();

        Vec2D position = door.getPositionByWall(destination);
        Vec2D roomCenter = new Vec2D(destination.getWidth() / 2D, destination.getHeight() / 2D);

        BoundingBox playerBoundingBox = event.getApp().getBoundingBoxes().get("shiba");
        Vec2D playerMidpoint =
                playerBoundingBox
                        .getTopLeftCorner()
                        .add(playerBoundingBox.getBottomRightCorner())
                        .scale(0.5);

        Vec2D playerPos =
                roomCenter
                        .subtract(position)
                        .normalize()
                        .scale(10)
                        .add(position)
                        .subtract(playerMidpoint);
        event.getPlayer().setPosition(playerPos);
        event.getPlayer().setVelocity(Vec2D.zero());
    }

    @Override
    public void onPlayerUnlockDoor(PlayerUnlockDoorEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();

        if (player.getNumberOfKeys() <= 0) {
            event.setCancelled(true);
            return;
        }

        player.dropKey();
    }

    @Override
    public void onMonsterDamaged(MonsterDamagedEvent event) {
        if (!event.getMonster().isAlive()) getModel().getPlayer().pickKey();
    }
}
