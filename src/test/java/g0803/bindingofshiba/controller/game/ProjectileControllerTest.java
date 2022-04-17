package g0803.bindingofshiba.controller.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.events.EventManager;
import g0803.bindingofshiba.gui.keyboard.Keyboard;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.model.game.elements.Player;
import g0803.bindingofshiba.model.game.elements.Projectile;
import g0803.bindingofshiba.model.game.room.Room;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ProjectileControllerTest {

    @Test
    public void spawnProjectileUp() {
        App app = Mockito.mock(App.class);
        EventManager manager = Mockito.mock(EventManager.class);
        Keyboard keyboard = Mockito.mock(Keyboard.class);

        Mockito.when(keyboard.getPressedKey()).thenReturn(Keyboard.Key.ARROW_UP);
        Mockito.when(keyboard.isKeyPressed(Keyboard.Key.ARROW_UP)).thenReturn(true);
        Mockito.when(app.getKeyboard()).thenReturn(keyboard);

        Room room = Mockito.mock(Room.class);
        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getPosition()).thenReturn(new Vec2D(80, 60));
        Mockito.when(player.getVelocity()).thenReturn(Vec2D.zero());

        Game game = new Game(player, room);
        ProjectileController projectileController = new ProjectileController(game, manager);

        projectileController.tick(app, 2);

        Mockito.verify(room)
                .addProjectile(
                        Mockito.argThat(
                                projectile ->
                                        projectile
                                                .getVelocity()
                                                .normalize()
                                                .isSimilar(Vec2D.up())));
    }

    @Test
    public void moveProjectiles() {
        App app = Mockito.mock(App.class);
        EventManager manager = Mockito.mock(EventManager.class);
        Keyboard keyboard = Mockito.mock(Keyboard.class);

        Projectile projectile = Mockito.mock(Projectile.class);
        Room room = Mockito.mock(Room.class);

        Mockito.when(app.getKeyboard()).thenReturn(keyboard);
        Mockito.when(keyboard.getPressedKey()).thenReturn(Keyboard.Key.NONE);
        Mockito.when(room.getProjectiles()).thenReturn(Set.of(projectile));

        Game game = Mockito.mock(Game.class);
        Mockito.when(game.getCurrentRoom()).thenReturn(room);
        ProjectileController projectileController = new ProjectileController(game, manager);

        projectileController.tick(app, 3);

        Mockito.verify(projectile).move(3);
    }
}
