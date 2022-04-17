package g0803.bindingofshiba.controller.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.events.EventManager;
import g0803.bindingofshiba.events.game.*;
import g0803.bindingofshiba.gui.keyboard.Keyboard;
import g0803.bindingofshiba.math.BoundingBox;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.model.game.elements.Monster;
import g0803.bindingofshiba.model.game.elements.Obstacle;
import g0803.bindingofshiba.model.game.elements.Player;
import g0803.bindingofshiba.model.game.room.Door;
import g0803.bindingofshiba.model.game.room.Room;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PlayerControllerTest {

    @Test
    public void movesAccordingToInputUp() {
        App app = Mockito.mock(App.class);
        EventManager manager = Mockito.mock(EventManager.class);
        Keyboard keyboard = Mockito.mock(Keyboard.class);

        Mockito.when(keyboard.getPressedKey()).thenReturn(Keyboard.Key.W);
        Mockito.when(keyboard.isKeyPressed(Keyboard.Key.W)).thenReturn(true);
        Mockito.when(app.getKeyboard()).thenReturn(keyboard);

        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getVelocity()).thenReturn(Vec2D.zero());

        Game game = new Game(player, Mockito.mock(Room.class));
        PlayerController playerController = new PlayerController(game, manager);

        playerController.tick(app, 2);

        Mockito.verify(player).move(2);
        Mockito.verify(player)
                .setAcceleration(Mockito.argThat(vec -> vec.normalize().isSimilar(Vec2D.up())));
    }

    @Test
    public void movesAccordingToInputDown() {
        App app = Mockito.mock(App.class);
        EventManager manager = Mockito.mock(EventManager.class);
        Keyboard keyboard = Mockito.mock(Keyboard.class);

        Mockito.when(keyboard.getPressedKey()).thenReturn(Keyboard.Key.S);
        Mockito.when(keyboard.isKeyPressed(Keyboard.Key.S)).thenReturn(true);
        Mockito.when(app.getKeyboard()).thenReturn(keyboard);

        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getVelocity()).thenReturn(Vec2D.zero());

        Game game = new Game(player, Mockito.mock(Room.class));
        PlayerController playerController = new PlayerController(game, manager);

        playerController.tick(app, 3);

        Mockito.verify(player).move(3);
        Mockito.verify(player)
                .setAcceleration(Mockito.argThat(vec -> vec.normalize().isSimilar(Vec2D.down())));
    }

    @Test
    public void movesAccordingToInputLeft() {
        App app = Mockito.mock(App.class);
        EventManager manager = Mockito.mock(EventManager.class);
        Keyboard keyboard = Mockito.mock(Keyboard.class);

        Mockito.when(keyboard.getPressedKey()).thenReturn(Keyboard.Key.A);
        Mockito.when(keyboard.isKeyPressed(Keyboard.Key.A)).thenReturn(true);
        Mockito.when(app.getKeyboard()).thenReturn(keyboard);

        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getVelocity()).thenReturn(Vec2D.zero());

        Game game = new Game(player, Mockito.mock(Room.class));
        PlayerController playerController = new PlayerController(game, manager);

        playerController.tick(app, 2);

        Mockito.verify(player).move(2);
        Mockito.verify(player)
                .setAcceleration(Mockito.argThat(vec -> vec.normalize().isSimilar(Vec2D.left())));
    }

    @Test
    public void movesAccordingToInputRight() {
        App app = Mockito.mock(App.class);
        EventManager manager = Mockito.mock(EventManager.class);
        Keyboard keyboard = Mockito.mock(Keyboard.class);

        Mockito.when(keyboard.getPressedKey()).thenReturn(Keyboard.Key.D);
        Mockito.when(keyboard.isKeyPressed(Keyboard.Key.D)).thenReturn(true);
        Mockito.when(app.getKeyboard()).thenReturn(keyboard);

        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getVelocity()).thenReturn(Vec2D.zero());

        Game game = new Game(player, Mockito.mock(Room.class));
        PlayerController playerController = new PlayerController(game, manager);

        playerController.tick(app, 2);

        Mockito.verify(player).move(2);
        Mockito.verify(player)
                .setAcceleration(Mockito.argThat(vec -> vec.normalize().isSimilar(Vec2D.right())));
    }

    @Test
    public void doesntMoveWithNoInput() {
        App app = Mockito.mock(App.class);
        EventManager manager = Mockito.mock(EventManager.class);
        Keyboard keyboard = Mockito.mock(Keyboard.class);

        Mockito.when(keyboard.getPressedKey()).thenReturn(Keyboard.Key.NONE);
        Mockito.when(keyboard.isKeyPressed(Keyboard.Key.NONE)).thenReturn(true);
        Mockito.when(app.getKeyboard()).thenReturn(keyboard);

        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getVelocity()).thenReturn(Vec2D.zero());

        Game game = new Game(player, Mockito.mock(Room.class));
        PlayerController playerController = new PlayerController(game, manager);

        playerController.tick(app, 2);

        Mockito.verify(player).move(2);
        Mockito.verify(player).setAcceleration(Mockito.argThat(vec -> vec.isSimilar(Vec2D.zero())));
    }

    @Test
    public void collisionWithMonster() {
        App app = Mockito.mock(App.class);

        Monster monster = Mockito.mock(Monster.class);
        Player player = Mockito.mock(Player.class);

        Game game = Mockito.mock(Game.class);
        EventManager manager = Mockito.mock(EventManager.class);

        PlayerController controller = new PlayerController(game, manager);
        Mockito.verify(manager).addObserver(controller);

        PlayerCollisionWithMonsterEvent event =
                new PlayerCollisionWithMonsterEvent(2, app, player, monster);

        controller.onPlayerCollisionWithMonster(event);

        Mockito.verify(player).setVelocity(Vec2D.zero());
        Mockito.verify(player).setAcceleration(Vec2D.zero());

        Mockito.verifyNoInteractions(monster);
    }

    @Test
    public void collisionWithWall() {
        App app = Mockito.mock(App.class);

        Monster monster = Mockito.mock(Monster.class);
        Player player = Mockito.mock(Player.class);

        Game game = Mockito.mock(Game.class);
        EventManager manager = Mockito.mock(EventManager.class);

        PlayerController controller = new PlayerController(game, manager);
        Mockito.verify(manager).addObserver(controller);

        PlayerCollisionWithWallsEvent event = new PlayerCollisionWithWallsEvent(3, app, player);

        controller.onPlayerCollisionWithWalls(event);

        Mockito.verify(player).setVelocity(Vec2D.zero());
        Mockito.verify(player).setAcceleration(Vec2D.zero());

        Mockito.verifyNoInteractions(monster);
    }

    @Test
    public void collisionWithObstacle() {
        App app = Mockito.mock(App.class);

        Monster monster = Mockito.mock(Monster.class);
        Player player = Mockito.mock(Player.class);

        Game game = Mockito.mock(Game.class);
        EventManager manager = Mockito.mock(EventManager.class);

        PlayerController controller = new PlayerController(game, manager);
        Mockito.verify(manager).addObserver(controller);

        PlayerCollisionWithObstacleEvent event =
                new PlayerCollisionWithObstacleEvent(1, app, player, Mockito.mock(Obstacle.class));

        controller.onPlayerCollisionWithObstacle(event);

        Mockito.verify(player).setVelocity(Vec2D.zero());
        Mockito.verify(player).setAcceleration(Vec2D.zero());

        Mockito.verifyNoInteractions(monster);
    }

    @Test
    public void enterDoor() {
        App app = Mockito.mock(App.class);
        Bundle<BoundingBox> boundingBoxes = Mockito.mock(Bundle.class);

        Mockito.when(app.getBoundingBoxes()).thenReturn(boundingBoxes);
        Mockito.when(boundingBoxes.get("shiba")).thenReturn(new BoundingBox(-1, 2, 5, 6));

        Player player = Mockito.mock(Player.class);
        Door door = Mockito.mock(Door.class);

        Game game = Mockito.mock(Game.class);
        EventManager manager = Mockito.mock(EventManager.class);

        PlayerController controller = new PlayerController(game, manager);
        Mockito.verify(manager).addObserver(controller);

        Room currentRoom = Mockito.mock(Room.class);
        Room otherRoom = Mockito.mock(Room.class);

        Mockito.when(game.getCurrentRoom()).thenReturn(currentRoom);
        Mockito.when(door.getOtherRoom(currentRoom)).thenReturn(otherRoom);
        Mockito.when(door.getPositionByWall(otherRoom)).thenReturn(new Vec2D(14, 2));
        Mockito.when(otherRoom.getWidth()).thenReturn(200);
        Mockito.when(otherRoom.getHeight()).thenReturn(100);

        PlayerEnterDoorEvent event = new PlayerEnterDoorEvent(2, app, player, door);
        controller.onPlayerEnterDoor(event);

        Mockito.verify(player).setVelocity(Vec2D.zero());
        Mockito.verify(player)
                .setPosition(
                        Mockito.argThat(
                                vec -> {
                                    double distanceToDoor =
                                            vec.subtract(new Vec2D(14, 2)).getLengthSquared();
                                    double distanceFromDoorToRoomCenter =
                                            new Vec2D(14, 2)
                                                    .subtract(new Vec2D(100, 50))
                                                    .getLengthSquared();
                                    double distanceToRoomCenter =
                                            vec.subtract(new Vec2D(100, 50)).getLengthSquared();
                                    return distanceToRoomCenter < distanceFromDoorToRoomCenter
                                            && distanceToDoor < 225;
                                }));
    }

    @Test
    public void unlockDoor() {
        App app = Mockito.mock(App.class);

        Player player = Mockito.mock(Player.class);
        Door door = Mockito.mock(Door.class);

        Game game = Mockito.mock(Game.class);
        EventManager manager = Mockito.mock(EventManager.class);

        PlayerController controller = new PlayerController(game, manager);
        Mockito.verify(manager).addObserver(controller);

        Mockito.when(player.getNumberOfKeys()).thenReturn(1);

        PlayerUnlockDoorEvent event = new PlayerUnlockDoorEvent(2, app, player, door);
        controller.onPlayerUnlockDoor(event);

        Mockito.verify(player, Mockito.times(1)).dropKey();
    }

    @Test
    public void unlockDoorWithNotEnoughKeys() {
        App app = Mockito.mock(App.class);

        Player player = Mockito.mock(Player.class);
        Door door = Mockito.mock(Door.class);

        Game game = Mockito.mock(Game.class);
        EventManager manager = Mockito.mock(EventManager.class);

        PlayerController controller = new PlayerController(game, manager);
        Mockito.verify(manager).addObserver(controller);

        Mockito.when(player.getNumberOfKeys()).thenReturn(0);

        PlayerUnlockDoorEvent event = new PlayerUnlockDoorEvent(2, app, player, door);
        controller.onPlayerUnlockDoor(event);

        Assertions.assertTrue(event.isCancelled());
    }
}
