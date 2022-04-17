package g0803.bindingofshiba.view.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.gui.GUI;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.model.game.elements.Player;
import g0803.bindingofshiba.textures.ITexture;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PlayerViewTest {

    @Test
    public void blitsCorrectTexture() {
        App app = Mockito.mock(App.class);
        GUI gui = Mockito.mock(GUI.class);

        Bundle<ITexture> textures = Mockito.mock(Bundle.class);
        ITexture texture = Mockito.mock(ITexture.class);

        Mockito.when(app.getTextures()).thenReturn(textures);
        Mockito.when(textures.get("shiba")).thenReturn(texture);

        Player player = new Player(new Vec2D(7, 3), 1, 20, 2);
        PlayerView view = new PlayerView(player, Mockito.mock(IEventManager.class));

        view.draw(app, gui, new Vec2D(4, -2));

        Mockito.verify(gui).blit(11, 1, texture);
    }
}
