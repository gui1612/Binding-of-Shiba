package g0803.bindingofshiba.view.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.gui.GUI;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.model.game.elements.Projectile;
import g0803.bindingofshiba.textures.ITexture;
import g0803.bindingofshiba.view.View;

public class ProjectileView extends View<Projectile> {

    public ProjectileView(Projectile model, IEventManager eventManager) {
        super(model, eventManager);
    }

    @Override
    public void draw(App app, GUI gui, Vec2D offset) {
        Bundle<ITexture> textures = app.getTextures();
        Vec2D position = getModel().getPosition().add(offset).round();

        gui.blit((int) position.getX(), (int) position.getY(), textures.get("heart"));
    }
}
