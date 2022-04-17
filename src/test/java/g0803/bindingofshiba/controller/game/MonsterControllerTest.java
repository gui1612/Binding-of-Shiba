package g0803.bindingofshiba.controller.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.events.EventManager;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.events.game.*;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.model.game.elements.Monster;
import g0803.bindingofshiba.model.game.elements.Obstacle;
import g0803.bindingofshiba.model.game.elements.Player;
import g0803.bindingofshiba.model.game.elements.Projectile;
import g0803.bindingofshiba.model.game.room.Room;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MonsterControllerTest {

    @Test
    public void movesTowardsPlayerIfFarAway() {
        App app = Mockito.mock(App.class);
        EventManager manager = Mockito.mock(EventManager.class);

        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getPosition()).thenReturn(new Vec2D(-1, 2));

        Monster monster = Mockito.mock(Monster.class);
        Mockito.when(monster.getPosition()).thenReturn(new Vec2D(500, 200));
        Mockito.when(monster.getVelocity()).thenReturn(Vec2D.zero());

        Room room = Mockito.mock(Room.class);
        Mockito.when(room.getMonsters()).thenReturn(List.of(monster));

        Game game = new Game(player, room);

        MonsterController controller = new MonsterController(game, manager);
        controller.tick(app, 3);

        Mockito.verify(monster)
                .setAcceleration(
                        Mockito.argThat(
                                vec ->
                                        vec.normalize()
                                                .isSimilar(new Vec2D(-501, -198).normalize())));
    }

    @Test
    public void movesAwayFromPlayerIfNearby() {
        App app = Mockito.mock(App.class);
        EventManager manager = Mockito.mock(EventManager.class);

        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getPosition()).thenReturn(new Vec2D(-1, 2));

        Monster monster = Mockito.mock(Monster.class);
        Mockito.when(monster.getPosition()).thenReturn(new Vec2D(3, 10));
        Mockito.when(monster.getVelocity()).thenReturn(Vec2D.zero());

        Room room = Mockito.mock(Room.class);
        Mockito.when(room.getMonsters()).thenReturn(List.of(monster));

        Game game = new Game(player, room);

        MonsterController controller = new MonsterController(game, manager);
        controller.tick(app, 3);

        Mockito.verify(monster).move(3);
        Mockito.verify(monster)
                .setAcceleration(
                        Mockito.argThat(
                                vec -> vec.normalize().isSimilar(new Vec2D(4, 8).normalize())));
    }

    @Test
    public void collisionWithMonster() {
        App app = Mockito.mock(App.class);
        Monster monster1 = Mockito.mock(Monster.class);
        Monster monster2 = Mockito.mock(Monster.class);

        Game game = Mockito.mock(Game.class);
        EventManager manager = Mockito.mock(EventManager.class);

        MonsterController controller = new MonsterController(game, manager);
        Mockito.verify(manager).addObserver(controller);

        MonsterCollisionWithMonsterEvent event =
                new MonsterCollisionWithMonsterEvent(3, app, monster1, monster2);

        Mockito.when(monster1.getNextPosition(3)).thenReturn(new Vec2D(3, 4));
        Mockito.when(monster2.getNextPosition(3)).thenReturn(new Vec2D(6, 7));
        Mockito.when(monster1.getNextVelocity(3)).thenReturn(new Vec2D(3, 5));
        Mockito.when(monster2.getNextVelocity(3)).thenReturn(new Vec2D(8, 9));

        controller.onMonsterCollisionWithMonster(event);

        Mockito.verify(monster1)
                .setVelocity(Mockito.argThat(vec -> !vec.isSimilar(new Vec2D(3, 5))));
        Mockito.verify(monster2)
                .setVelocity(Mockito.argThat(vec -> !vec.isSimilar(new Vec2D(8, 9))));
    }

    @Test
    public void collisionWithPlayer() {
        App app = Mockito.mock(App.class);
        Monster monster = Mockito.mock(Monster.class);
        Player player = Mockito.mock(Player.class);

        Game game = Mockito.mock(Game.class);
        EventManager manager = Mockito.mock(EventManager.class);

        MonsterController controller = new MonsterController(game, manager);
        Mockito.verify(manager).addObserver(controller);

        PlayerCollisionWithMonsterEvent event =
                new PlayerCollisionWithMonsterEvent(2, app, player, monster);

        controller.onPlayerCollisionWithMonster(event);

        Mockito.verify(monster).setVelocity(Vec2D.zero());
        Mockito.verify(monster).setAcceleration(Vec2D.zero());

        Mockito.verifyNoInteractions(player);
    }

    @Test
    public void collisionWithWall() {
        App app = Mockito.mock(App.class);
        Monster monster = Mockito.mock(Monster.class);

        Game game = Mockito.mock(Game.class);
        IEventManager manager = Mockito.mock(IEventManager.class);

        MonsterController controller = new MonsterController(game, manager);
        Mockito.verify(manager).addObserver(controller);

        MonsterCollisionWithWallsEvent event = new MonsterCollisionWithWallsEvent(2, app, monster);

        controller.onMonsterCollisionWithWalls(event);

        Mockito.verify(monster).setVelocity(Vec2D.zero());
        Mockito.verify(monster).setAcceleration(Vec2D.zero());
    }

    @Test
    public void collisionWithObstacle() {
        App app = Mockito.mock(App.class);
        Monster monster = Mockito.mock(Monster.class);
        Obstacle obstacle = Mockito.mock(Obstacle.class);

        Game game = Mockito.mock(Game.class);
        EventManager manager = Mockito.mock(EventManager.class);

        MonsterController controller = new MonsterController(game, manager);
        Mockito.verify(manager).addObserver(controller);

        MonsterCollisionWithObstacleEvent event =
                new MonsterCollisionWithObstacleEvent(3, app, monster, obstacle);

        controller.onMonsterCollisionWithObstacle(event);

        Mockito.verify(monster).setVelocity(Vec2D.zero());
        Mockito.verify(monster).setAcceleration(Vec2D.zero());
    }

    @Test
    public void onProjectileCollisionWithMonster() {
        App app = Mockito.mock(App.class);

        IEventManager eventManager = Mockito.mock(IEventManager.class);
        Projectile projectile = Mockito.mock(Projectile.class);
        Monster monster = Mockito.mock(Monster.class);
        ProjectileCollisionWithMonsterEvent event =
                Mockito.mock(ProjectileCollisionWithMonsterEvent.class);

        Mockito.when(event.getApp()).thenReturn(app);
        Mockito.when(event.getTickTime()).thenReturn(3D);
        Mockito.when(event.getProjectile()).thenReturn(projectile);
        Mockito.when(event.getMonster()).thenReturn(monster);

        Mockito.when(projectile.getDamage()).thenReturn(3F);

        Mockito.when(monster.getHp()).thenReturn(20F, 13F);

        Room room = Mockito.mock(Room.class);
        Game game = Mockito.mock(Game.class);
        Mockito.when(game.getCurrentRoom()).thenReturn(room);

        MonsterController controller = new MonsterController(game, eventManager);
        controller.onProjectileCollisionWithMonster(event);

        Mockito.verify(monster).decreaseHpByAmount(3F);
        Mockito.verify(eventManager)
                .dispatchEvent(
                        Mockito.argThat(
                                arg ->
                                        arg instanceof MonsterDamagedEvent e
                                                && e.getTickTime() == 3
                                                && e.getMonster() == monster
                                                && e.getDamage() == 7D
                                                && e.getRoom() == room));
    }
}
