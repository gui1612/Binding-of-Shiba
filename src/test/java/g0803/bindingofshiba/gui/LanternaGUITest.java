package g0803.bindingofshiba.gui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.textures.ITexture;
import java.awt.*;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

public class LanternaGUITest {

    @Test
    public void closesScreen() throws IOException {
        Screen screen = Mockito.mock(Screen.class);

        GUI gui = new LanternaGUI(screen);
        gui.close();

        Mockito.verify(screen, Mockito.only()).close();
    }

    @Test
    public void refreshesScreen() throws IOException {
        Screen screen = Mockito.mock(Screen.class);

        GUI gui = new LanternaGUI(screen);
        gui.refresh();

        Mockito.verify(screen, Mockito.only()).refresh();
    }

    @Test
    public void clearsScreen() throws IOException {
        Screen screen = Mockito.mock(Screen.class);

        GUI gui = new LanternaGUI(screen);
        gui.clear();

        Mockito.verify(screen).clear();
    }

    @Test
    public void blit() {
        Color[] colors =
                new Color[] {
                    new Color(56, 87, 0, 0),
                    new Color(34, 56, 255, 89),
                    null,
                    new Color(255, 255, 255, 255),
                    new Color(66, 66, 66, 155),
                    new Color(45, 122, 253, 128)
                };
        ITexture texture = Mockito.mock(ITexture.class);
        Mockito.when(texture.getWidth()).thenReturn(2);
        Mockito.when(texture.getHeight()).thenReturn(3);

        Mockito.when(texture.getColorAt(0, 0)).thenReturn(colors[0]);
        Mockito.when(texture.getColorAt(1, 0)).thenReturn(colors[1]);
        Mockito.when(texture.getColorAt(0, 1)).thenReturn(colors[2]);
        Mockito.when(texture.getColorAt(1, 1)).thenReturn(colors[3]);
        Mockito.when(texture.getColorAt(0, 2)).thenReturn(colors[4]);
        Mockito.when(texture.getColorAt(1, 2)).thenReturn(colors[5]);

        Screen screen = Mockito.mock(Screen.class);
        TextGraphics textGraphics = Mockito.mock(TextGraphics.class);
        GUI gui = new LanternaGUI(screen);

        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphics);

        Vec2D offset = new Vec2D(1, 1);
        Mockito.when(texture.getAnchorOffset(10, 11)).thenReturn(offset);

        gui.blit(10, 11, texture);
        InOrder inOrder1 = Mockito.inOrder(textGraphics);

        inOrder1.verify(textGraphics).setBackgroundColor(new TextColor.RGB(255, 255, 255));
        inOrder1.verify(textGraphics).setCharacter(2, 2, ' ');

        InOrder inOrder2 = Mockito.inOrder(textGraphics);

        inOrder2.verify(textGraphics).setBackgroundColor(new TextColor.RGB(45, 122, 253));
        inOrder2.verify(textGraphics).setCharacter(2, 3, ' ');

        InOrder inOrder3 = Mockito.inOrder(textGraphics);

        inOrder3.verify(textGraphics).setBackgroundColor(new TextColor.RGB(66, 66, 66));
        inOrder3.verify(textGraphics).setCharacter(1, 3, ' ');
    }

    @Test
    public void fill() {
        TextGraphics textGraphics = Mockito.mock(TextGraphics.class);

        Screen screen = Mockito.mock(Screen.class);
        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphics);
        GUI gui = new LanternaGUI(screen);

        Color color = new Color(44, 254, 90);
        TextColor textColor = new TextColor.RGB(44, 254, 90);

        gui.fill(color);
        Mockito.verify(textGraphics).setBackgroundColor(textColor);
        Mockito.verify(textGraphics).fill(' ');
    }

    @Test
    public void fillWithTransparency() {
        Screen screen = Mockito.mock(Screen.class);
        GUI gui = new LanternaGUI(screen);

        Color color = new Color(244, 89, 211, 127);

        gui.fill(color);
        Mockito.verify(screen, Mockito.never()).newTextGraphics();
    }
}
