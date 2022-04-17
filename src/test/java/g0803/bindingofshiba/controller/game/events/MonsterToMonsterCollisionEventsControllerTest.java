package g0803.bindingofshiba.controller.game.events;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.events.EventManager;
import g0803.bindingofshiba.events.game.MonsterCollisionWithMonsterEvent;
import g0803.bindingofshiba.math.BoundingBox;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.model.game.elements.Monster;
import g0803.bindingofshiba.model.game.elements.Player;
import g0803.bindingofshiba.model.game.room.Room;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MonsterToMonsterCollisionEventsControllerTest {

    @Test
    public void twoMonstersColliding() {
        App app = Mockito.mock(App.class);

        Bundle<BoundingBox> boundingBoxes = Mockito.mock(Bundle.class);
        Mockito.when(boundingBoxes.get("shiba")).thenReturn(new BoundingBox(-5, -4, 10, 8));
        Mockito.when(boundingBoxes.get("monster")).thenReturn(new BoundingBox(-3, -5, 6, 10));

        Mockito.when(app.getBoundingBoxes()).thenReturn(boundingBoxes);

        Player player = Mockito.mock(Player.class);
        Monster monster1 = Mockito.mock(Monster.class);
        Monster monster2 = Mockito.mock(Monster.class);

        Mockito.when(player.getNextPosition(4)).thenReturn(new Vec2D(13, 1));
        Mockito.when(monster1.getNextPosition(4)).thenReturn(new Vec2D(18, 14));
        Mockito.when(monster2.getNextPosition(4)).thenReturn(new Vec2D(20, 15));

        Room room = Mockito.mock(Room.class);
        Mockito.when(room.getMonsters()).thenReturn(List.of(monster1, monster2));

        EventManager manager = Mockito.mock(EventManager.class);
        Game game = new Game(player, room);
        MonsterToMonsterCollisionEventsController controller =
                new MonsterToMonsterCollisionEventsController(game, manager);

        controller.tick(app, 4);

        Mockito.verify(manager)
                .dispatchEvent(
                        Mockito.argThat(
                                event -> {
                                    if (!(event instanceof MonsterCollisionWithMonsterEvent e))
                                        return false;

                                    return ((e.getFirstMonster() == monster1
                                                            && e.getSecondMonster() == monster2)
                                                    || (e.getFirstMonster() == monster2
                                                            && e.getSecondMonster() == monster1))
                                            && e.getTickTime() == 4;
                                }));
    }

    @Test
    public void monstersNotCollidingWithMonsters() {
        App app = Mockito.mock(App.class);

        Bundle<BoundingBox> boundingBoxes = Mockito.mock(Bundle.class);
        Mockito.when(boundingBoxes.get("shiba")).thenReturn(new BoundingBox(-5, -4, 10, 8));
        Mockito.when(boundingBoxes.get("monster")).thenReturn(new BoundingBox(-3, -5, 6, 10));

        Mockito.when(app.getBoundingBoxes()).thenReturn(boundingBoxes);

        Player player = Mockito.mock(Player.class);
        Monster monster1 = Mockito.mock(Monster.class);
        Monster monster2 = Mockito.mock(Monster.class);

        Mockito.when(player.getNextPosition(2)).thenReturn(new Vec2D(13, 1));
        Mockito.when(monster1.getNextPosition(2)).thenReturn(new Vec2D(18, 14));
        Mockito.when(monster2.getNextPosition(2)).thenReturn(new Vec2D(5, 34));

        Room room = Mockito.mock(Room.class);
        Mockito.when(room.getMonsters()).thenReturn(List.of(monster1, monster2));

        EventManager manager = Mockito.mock(EventManager.class);
        Game game = new Game(player, room);
        MonsterToMonsterCollisionEventsController controller =
                new MonsterToMonsterCollisionEventsController(game, manager);

        controller.tick(app, 2);

        Mockito.verifyNoInteractions(manager);
    }
}
