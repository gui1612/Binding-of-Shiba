package g0803.bindingofshiba.gui;

import g0803.bindingofshiba.gui.keyboard.Keyboard;
import g0803.bindingofshiba.textures.ITexture;
import java.awt.*;
import java.io.IOException;

public interface GUI {

    Keyboard getKeyboard();

    void close() throws IOException;

    void clear();

    void refresh() throws IOException;

    void blit(int x, int y, ITexture texture);

    void fill(Color color);
}
