package g0803.bindingofshiba.gui.keyboard;

import com.googlecode.lanterna.input.InputProvider;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LanternaKeyboardTest {

    private final KeyStroke EOF = new KeyStroke(KeyType.EOF, false, false);

    private KeyStroke newKeyStroke(Keyboard.Key key) {
        return switch (key) {
            case W -> new KeyStroke('W', false, false);
            case A -> new KeyStroke('A', false, false);
            case S -> new KeyStroke('S', false, false);
            case D -> new KeyStroke('D', false, false);
            case ARROW_UP -> new KeyStroke(KeyType.ArrowUp, false, false);
            case ARROW_DOWN -> new KeyStroke(KeyType.ArrowDown, false, false);
            case ARROW_LEFT -> new KeyStroke(KeyType.ArrowLeft, false, false);
            case ARROW_RIGHT -> new KeyStroke(KeyType.ArrowRight, false, false);
            case SPACE -> new KeyStroke(' ', false, false);
            case ENTER -> new KeyStroke(KeyType.Enter, false, false);
            case ESCAPE -> new KeyStroke(KeyType.Escape, false, false);
            case NONE -> new KeyStroke(KeyType.F9, false, false);
        };
    }

    private void assertKeyPressed(Keyboard keyboard, Keyboard.Key key) {
        Assertions.assertTrue(keyboard.isKeyPressed(key));

        for (Keyboard.Key value : Keyboard.Key.values()) {
            if (value != key) Assertions.assertFalse(keyboard.isKeyPressed(value));
        }
    }

    private KeyStroke[] getAllSelectedKeyStrokes() {
        Keyboard.Key[] keys = Keyboard.Key.values();
        KeyStroke[] keyStrokes = new KeyStroke[2 * keys.length];

        for (int i = 0; i < keys.length; i++) {
            Keyboard.Key key = keys[i];

            keyStrokes[2 * i] = newKeyStroke(key);
            keyStrokes[2 * i + 1] = null;
        }

        return keyStrokes;
    }

    @Test
    public void storesKeyPress() throws IOException {
        InputProvider inputProvider = Mockito.mock(InputProvider.class);

        Mockito.when(inputProvider.pollInput())
                .thenReturn(newKeyStroke(Keyboard.Key.A), (KeyStroke) null);

        Mockito.verify(inputProvider, Mockito.never()).readInput();

        LanternaKeyboard keyboard = new LanternaKeyboard(inputProvider);
        keyboard.update();

        assertKeyPressed(keyboard, Keyboard.Key.A);
    }

    @Test
    public void storesLastKeyPress() throws IOException {
        InputProvider inputProvider = Mockito.mock(InputProvider.class);

        Mockito.when(inputProvider.pollInput())
                .thenReturn(
                        newKeyStroke(Keyboard.Key.A),
                        newKeyStroke(Keyboard.Key.D),
                        newKeyStroke(Keyboard.Key.A),
                        newKeyStroke(Keyboard.Key.S),
                        null);

        Mockito.verify(inputProvider, Mockito.never()).readInput();

        LanternaKeyboard keyboard = new LanternaKeyboard(inputProvider);
        keyboard.update();

        assertKeyPressed(keyboard, Keyboard.Key.S);
    }

    @Test
    public void readsUntilNull() throws IOException {
        InputProvider inputProvider = Mockito.mock(InputProvider.class);

        Mockito.when(inputProvider.pollInput())
                .thenReturn(
                        newKeyStroke(Keyboard.Key.A),
                        null,
                        null,
                        newKeyStroke(Keyboard.Key.D),
                        newKeyStroke(Keyboard.Key.S),
                        null);

        Mockito.verify(inputProvider, Mockito.never()).readInput();

        LanternaKeyboard keyboard = new LanternaKeyboard(inputProvider);

        keyboard.update();
        assertKeyPressed(keyboard, Keyboard.Key.A);

        keyboard.update();
        assertKeyPressed(keyboard, Keyboard.Key.NONE);

        keyboard.update();
        assertKeyPressed(keyboard, Keyboard.Key.S);
    }

    @Test
    public void ignoresUnrecognizedKeyStrokes() throws IOException {
        InputProvider inputProvider = Mockito.mock(InputProvider.class);

        Mockito.when(inputProvider.pollInput())
                .thenReturn(new KeyStroke('F', false, false), (KeyStroke) null);

        Mockito.verify(inputProvider, Mockito.never()).readInput();

        LanternaKeyboard keyboard = new LanternaKeyboard(inputProvider);

        keyboard.update();
        assertKeyPressed(keyboard, Keyboard.Key.NONE);
    }

    @Test
    public void acceptsSelectedCharacters() throws IOException {
        InputProvider inputProvider = Mockito.mock(InputProvider.class);

        KeyStroke[] keyStrokes = getAllSelectedKeyStrokes();

        Mockito.when(inputProvider.pollInput()).thenReturn(null, keyStrokes);
        Mockito.verify(inputProvider, Mockito.never()).readInput();

        LanternaKeyboard keyboard = new LanternaKeyboard(inputProvider);
        keyboard.update(); // discard first null keystroke

        for (Keyboard.Key key : Keyboard.Key.values()) {
            keyboard.update();
            assertKeyPressed(keyboard, key);
        }
    }

    @Test
    public void closesAtEOF() throws IOException {
        InputProvider inputProvider = Mockito.mock(InputProvider.class);

        Mockito.when(inputProvider.pollInput()).thenReturn(EOF);

        Mockito.verify(inputProvider, Mockito.never()).readInput();

        LanternaKeyboard keyboard = new LanternaKeyboard(inputProvider);

        keyboard.update();

        assertKeyPressed(keyboard, Keyboard.Key.NONE);
        Assertions.assertTrue(keyboard.isClosed());
    }

    @Test
    public void doesNotCloseIfNotEOF() throws IOException {
        InputProvider inputProvider = Mockito.mock(InputProvider.class);

        Mockito.when(inputProvider.pollInput())
                .thenReturn(newKeyStroke(Keyboard.Key.A), (KeyStroke) null);

        Mockito.verify(inputProvider, Mockito.never()).readInput();

        LanternaKeyboard keyboard = new LanternaKeyboard(inputProvider);

        keyboard.update();
        Assertions.assertFalse(keyboard.isClosed());
    }

    @Test
    public void doesNotReadIfClosed() throws IOException {
        InputProvider inputProvider = Mockito.mock(InputProvider.class);

        Mockito.when(inputProvider.pollInput()).thenReturn(EOF);

        Mockito.verify(inputProvider, Mockito.never()).readInput();

        LanternaKeyboard keyboard = new LanternaKeyboard(inputProvider);

        keyboard.update();
        Assertions.assertTrue(keyboard.isClosed());

        Mockito.clearInvocations(inputProvider);
        Mockito.verify(inputProvider, Mockito.never()).pollInput();
        keyboard.update();
    }

    @Test
    public void getKeyPressDuration() throws IOException, InterruptedException {
        InputProvider inputProvider = Mockito.mock(InputProvider.class);

        Mockito.when(inputProvider.pollInput())
                .thenReturn(newKeyStroke(Keyboard.Key.A), null, newKeyStroke(Keyboard.Key.A), null);

        Mockito.verify(inputProvider, Mockito.never()).readInput();

        LanternaKeyboard keyboard = new LanternaKeyboard(inputProvider);

        long updateStart = System.currentTimeMillis();
        keyboard.update();
        long updateEnd = System.currentTimeMillis();

        Thread.sleep(1000);

        long durationStart = System.currentTimeMillis();
        long duration = keyboard.getKeyPressDuration();
        long durationEnd = System.currentTimeMillis();

        long upperBound = durationEnd - updateStart;
        long lowerBound = durationStart - updateEnd;

        Assertions.assertTrue(duration >= lowerBound && duration <= upperBound);
    }
}
