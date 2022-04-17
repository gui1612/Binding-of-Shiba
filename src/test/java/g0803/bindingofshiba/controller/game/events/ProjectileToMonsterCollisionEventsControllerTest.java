package g0803.bindingofshiba.controller.game.events;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.events.EventManager;
import g0803.bindingofshiba.events.game.ProjectileCollisionWithMonsterEvent;
import g0803.bindingofshiba.math.BoundingBox;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.model.game.elements.Monster;
import g0803.bindingofshiba.model.game.elements.Player;
import g0803.bindingofshiba.model.game.elements.Projectile;
import g0803.bindingofshiba.model.game.room.Room;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ProjectileToMonsterCollisionEventsControllerTest {

    @Test
    public void projectileCollidingWithMonster() {
        App app = Mockito.mock(App.class);

        Bundle<BoundingBox> boundingBoxes = Mockito.mock(Bundle.class);
        Mockito.when(boundingBoxes.get("heart")).thenReturn(new BoundingBox(-5, -4, 10, 8));
        Mockito.when(boundingBoxes.get("monster")).thenReturn(new BoundingBox(-3, -5, 6, 10));

        Mockito.when(app.getBoundingBoxes()).thenReturn(boundingBoxes);

        Projectile projectile = Mockito.mock(Projectile.class);
        Monster monster = Mockito.mock(Monster.class);
        Mockito.when(projectile.getNextPosition(3)).thenReturn(new Vec2D(13, 1));
        Mockito.when(monster.getNextPosition(3)).thenReturn(new Vec2D(20, 3));

        EventManager manager = Mockito.mock(EventManager.class);

        Room room = Mockito.mock(Room.class);
        Mockito.when(room.getMonsters()).thenReturn(List.of(monster));
        Mockito.when(room.getProjectiles()).thenReturn(Set.of(projectile));

        Game game = new Game(Mockito.mock(Player.class), room);
        ProjectileToMonsterCollisionEventsController controller =
                new ProjectileToMonsterCollisionEventsController(game, manager);

        controller.tick(app, 3);

        Mockito.verify(manager)
                .dispatchEvent(
                        Mockito.argThat(
                                event -> {
                                    if (!(event instanceof ProjectileCollisionWithMonsterEvent e))
                                        return false;

                                    return e.getProjectile() == projectile
                                            && e.getMonster() == monster
                                            && e.getTickTime() == 3;
                                }));
    }

    @Test
    public void projectileNotCollidingWithMonster() {
        App app = Mockito.mock(App.class);

        Bundle<BoundingBox> boundingBoxes = Mockito.mock(Bundle.class);
        Mockito.when(boundingBoxes.get("heart")).thenReturn(new BoundingBox(-5, -4, 10, 8));
        Mockito.when(boundingBoxes.get("monster")).thenReturn(new BoundingBox(-3, -5, 6, 10));

        Mockito.when(app.getBoundingBoxes()).thenReturn(boundingBoxes);

        Projectile projectile = Mockito.mock(Projectile.class);
        Monster monster = Mockito.mock(Monster.class);
        Mockito.when(projectile.getNextPosition(3)).thenReturn(new Vec2D(13, 1));
        Mockito.when(monster.getNextPosition(3)).thenReturn(new Vec2D(30, 23));

        EventManager manager = Mockito.mock(EventManager.class);

        Room room = Mockito.mock(Room.class);
        Mockito.when(room.getMonsters()).thenReturn(List.of(monster));
        Mockito.when(room.getProjectiles()).thenReturn(Set.of(projectile));

        Game game = new Game(Mockito.mock(Player.class), room);
        ProjectileToMonsterCollisionEventsController controller =
                new ProjectileToMonsterCollisionEventsController(game, manager);

        controller.tick(app, 3);

        Mockito.verifyNoInteractions(manager);
    }
}
