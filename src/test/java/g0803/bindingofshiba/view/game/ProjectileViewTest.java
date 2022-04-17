package g0803.bindingofshiba.view.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.gui.GUI;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.model.game.elements.Projectile;
import g0803.bindingofshiba.textures.ITexture;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ProjectileViewTest {

    @Test
    public void blitsProjectileTexture() {
        App app = Mockito.mock(App.class);
        GUI gui = Mockito.mock(GUI.class);
        IEventManager manager = Mockito.mock(IEventManager.class);

        Bundle<ITexture> textures = Mockito.mock(Bundle.class);
        ITexture texture = Mockito.mock(ITexture.class);

        Mockito.when(app.getTextures()).thenReturn(textures);
        Mockito.when(textures.get("heart")).thenReturn(texture);

        Projectile projectile = new Projectile(new Vec2D(4, 5), 5);
        ProjectileView view = new ProjectileView(projectile, manager);

        view.draw(app, gui, new Vec2D(7, 8));

        Mockito.verify(gui).blit(11, 13, texture);
    }
}
