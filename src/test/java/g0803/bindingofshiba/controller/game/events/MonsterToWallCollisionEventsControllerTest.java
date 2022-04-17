package g0803.bindingofshiba.controller.game.events;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.events.EventManager;
import g0803.bindingofshiba.events.game.MonsterCollisionWithWallsEvent;
import g0803.bindingofshiba.math.BoundingBox;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.model.game.elements.Monster;
import g0803.bindingofshiba.model.game.elements.Player;
import g0803.bindingofshiba.model.game.room.Room;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MonsterToWallCollisionEventsControllerTest {

    @Test
    public void monsterCollidingWithWall() {
        App app = Mockito.mock(App.class);

        Bundle<BoundingBox> boundingBoxes = Mockito.mock(Bundle.class);
        Mockito.when(boundingBoxes.get("room")).thenReturn(new BoundingBox(-5, -4, 10, 8));
        Mockito.when(boundingBoxes.get("monster")).thenReturn(new BoundingBox(-3, -5, 6, 10));

        Mockito.when(app.getBoundingBoxes()).thenReturn(boundingBoxes);

        Monster monster = Mockito.mock(Monster.class);

        Mockito.when(monster.getNextPosition(3)).thenReturn(new Vec2D(13, 1));

        Room room = Mockito.mock(Room.class);

        Mockito.when(room.getMonsters()).thenReturn(List.of(monster));

        EventManager manager = Mockito.mock(EventManager.class);
        Game game = new Game(Mockito.mock(Player.class), room);
        MonsterToWallsCollisionEventsController controller =
                new MonsterToWallsCollisionEventsController(game, manager);

        controller.tick(app, 3);

        Mockito.verify(manager)
                .dispatchEvent(
                        Mockito.argThat(
                                event -> {
                                    if (!(event instanceof MonsterCollisionWithWallsEvent e))
                                        return false;

                                    return e.getMonster() == monster && e.getTickTime() == 3;
                                }));
    }

    @Test
    public void monsterNotCollidingWithWall() {
        App app = Mockito.mock(App.class);

        Bundle<BoundingBox> boundingBoxes = Mockito.mock(Bundle.class);
        Mockito.when(boundingBoxes.get("room")).thenReturn(new BoundingBox(-5, -4, 10, 8));
        Mockito.when(boundingBoxes.get("monster")).thenReturn(new BoundingBox(-3, -2, 6, 5));

        Mockito.when(app.getBoundingBoxes()).thenReturn(boundingBoxes);

        Monster monster = Mockito.mock(Monster.class);

        Mockito.when(monster.getNextPosition(4)).thenReturn(new Vec2D(-2, -1));

        Room room = Mockito.mock(Room.class);

        Mockito.when(room.getMonsters()).thenReturn(List.of(monster));

        EventManager manager = Mockito.mock(EventManager.class);
        Game game = new Game(Mockito.mock(Player.class), room);
        MonsterToWallsCollisionEventsController controller =
                new MonsterToWallsCollisionEventsController(game, manager);

        controller.tick(app, 4);

        Mockito.verifyNoInteractions(manager);
    }
}
