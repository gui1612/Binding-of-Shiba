package g0803.bindingofshiba.view.end;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.Constants;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.gui.GUI;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.model.end.GameOver;
import g0803.bindingofshiba.textures.ITexture;
import g0803.bindingofshiba.textures.TextTextureBuilder;
import g0803.bindingofshiba.view.View;
import java.awt.*;

public class GameOverView extends View<GameOver> {

    public GameOverView(GameOver model, IEventManager eventManager) {
        super(model, eventManager);
    }

    @Override
    public void draw(App app, GUI gui, Vec2D offset) {
        gui.fill(Color.darkGray);

        ITexture texture =
                new TextTextureBuilder(app.getFonts().get("text"))
                        .setText(getModel().isVictory() ? "You won!" : "You lost!")
                        .setColor(Color.white)
                        .build();

        gui.blit(
                Constants.SCREEN_WIDTH / 2 - texture.getWidth() / 2,
                Constants.SCREEN_HEIGHT / 2,
                texture);
    }
}
