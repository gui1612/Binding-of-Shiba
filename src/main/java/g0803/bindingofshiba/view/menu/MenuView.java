package g0803.bindingofshiba.view.menu;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.Constants;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.gui.GUI;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.model.menu.Menu;
import g0803.bindingofshiba.model.menu.MenuOption;
import g0803.bindingofshiba.textures.ITexture;
import g0803.bindingofshiba.textures.TextTextureBuilder;
import g0803.bindingofshiba.view.View;
import java.awt.*;

public class MenuView extends View<Menu> {

    public MenuView(Menu model, IEventManager eventManager) {
        super(model, eventManager);
    }

    @Override
    public void draw(App app, GUI gui, Vec2D offset) {
        Bundle<Font> fonts = app.getFonts();
        Bundle<ITexture> textures = app.getTextures();

        gui.fill(Color.darkGray);

        ITexture title =
                new TextTextureBuilder(fonts.get("text"))
                        .setText(getModel().getTitle())
                        .setColor(Color.lightGray)
                        .build();

        gui.blit(
                Constants.SCREEN_WIDTH / 2 - title.getWidth() / 2,
                Constants.SCREEN_HEIGHT / 2 - 15,
                title);

        int startY = Constants.SCREEN_HEIGHT / 2 + 5;
        for (MenuOption option : getModel().getOptions()) {
            TextTextureBuilder text =
                    new TextTextureBuilder(fonts.get("text"))
                            .setText(option.getText())
                            .setColor(Color.lightGray);

            if (option == getModel().getSelectedOption()) text.setColor(Color.white);

            ITexture texture = text.build();

            gui.blit(Constants.SCREEN_WIDTH / 2 - texture.getWidth() / 2, startY, texture);
            startY += 10;
        }
    }
}
