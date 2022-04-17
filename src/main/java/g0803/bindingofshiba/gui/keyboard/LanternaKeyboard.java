package g0803.bindingofshiba.gui.keyboard;

import com.googlecode.lanterna.input.InputProvider;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import java.io.IOException;

public class LanternaKeyboard implements Keyboard {

    private final InputProvider inputProvider;
    private boolean isClosed = false;

    private Key lastKeyPressed = Key.NONE;
    private long lastKeyPressStart = System.currentTimeMillis();

    public LanternaKeyboard(InputProvider inputProvider) {
        this.inputProvider = inputProvider;
    }

    @Override
    public boolean isKeyPressed(Key key) {
        return this.lastKeyPressed == key;
    }

    @Override
    public Key getPressedKey() {
        return lastKeyPressed;
    }

    @Override
    public long getKeyPressDuration() {
        return System.currentTimeMillis() - this.lastKeyPressStart;
    }

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public void update() throws IOException {
        if (this.isClosed) return;

        KeyStroke keyStroke = this.getLastKeyStroke();
        if (keyStroke != null && keyStroke.getKeyType() == KeyType.EOF) this.isClosed = true;

        Key currentKey = getCurrentKeyFromKeyStroke(keyStroke);
        if (this.lastKeyPressed != currentKey) {
            this.lastKeyPressStart = System.currentTimeMillis();
            this.lastKeyPressed = currentKey;
        }
    }

    private KeyStroke getLastKeyStroke() throws IOException {
        KeyStroke lastKeyStroke = null;

        KeyStroke currentKeyStroke;
        while ((currentKeyStroke = this.inputProvider.pollInput()) != null) {
            lastKeyStroke = currentKeyStroke;
            if (lastKeyStroke.getKeyType() == KeyType.EOF) break;
        }

        return lastKeyStroke;
    }

    private Key getCurrentKeyFromKeyStroke(KeyStroke keyStroke) {
        if (keyStroke == null) return Key.NONE;

        KeyType keyType = keyStroke.getKeyType();

        return switch (keyType) {
            case ArrowUp -> Key.ARROW_UP;
            case ArrowDown -> Key.ARROW_DOWN;
            case ArrowLeft -> Key.ARROW_LEFT;
            case ArrowRight -> Key.ARROW_RIGHT;
            case Enter -> Key.ENTER;
            case Escape -> Key.ESCAPE;
            case Character -> this.getCurrentKeyFromCharacter(keyStroke.getCharacter());
            default -> Key.NONE;
        };
    }

    private Key getCurrentKeyFromCharacter(Character character) {
        return switch (Character.toUpperCase(character)) {
            case 'W' -> Key.W;
            case 'A' -> Key.A;
            case 'S' -> Key.S;
            case 'D' -> Key.D;
            case ' ' -> Key.SPACE;
            default -> Key.NONE;
        };
    }
}
