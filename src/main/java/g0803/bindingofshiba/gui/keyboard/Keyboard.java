package g0803.bindingofshiba.gui.keyboard;

import java.io.IOException;

public interface Keyboard {

    enum Key {
        W,
        A,
        S,
        D,
        ARROW_UP,
        ARROW_DOWN,
        ARROW_LEFT,
        ARROW_RIGHT,
        SPACE,
        ENTER,
        ESCAPE,
        NONE;
    }

    boolean isKeyPressed(Key key);

    Key getPressedKey();

    long getKeyPressDuration();

    boolean isClosed();

    void update() throws IOException;
}
