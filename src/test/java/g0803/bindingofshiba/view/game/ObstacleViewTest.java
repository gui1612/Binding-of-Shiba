package g0803.bindingofshiba.view.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.gui.GUI;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.model.game.elements.Obstacle;
import g0803.bindingofshiba.textures.ITexture;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ObstacleViewTest {

    @Test
    public void blitsNormalTexture() {
        App app = Mockito.mock(App.class);
        GUI gui = Mockito.mock(GUI.class);

        Bundle<ITexture> textures = Mockito.mock(Bundle.class);
        ITexture texture = Mockito.mock(ITexture.class);

        Mockito.when(app.getTextures()).thenReturn(textures);
        Mockito.when(textures.get("rock")).thenReturn(texture);

        Obstacle obstacle = new Obstacle(new Vec2D(10, 4));
        ObstacleView view = new ObstacleView(obstacle, Mockito.mock(IEventManager.class));

        view.draw(app, gui, new Vec2D(-1, 2));

        Mockito.verify(gui).blit(9, 6, texture);
    }
}
