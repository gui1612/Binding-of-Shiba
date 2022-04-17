package g0803.bindingofshiba;

import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.gui.keyboard.Keyboard;
import g0803.bindingofshiba.math.BoundingBox;
import g0803.bindingofshiba.state.State;
import g0803.bindingofshiba.textures.ITexture;
import java.awt.*;

public interface App {
    void setState(State<?> state);

    Keyboard getKeyboard();

    Bundle<Font> getFonts();

    Bundle<ITexture> getTextures();

    Bundle<BoundingBox> getBoundingBoxes();
}
