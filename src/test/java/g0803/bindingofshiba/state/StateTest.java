package g0803.bindingofshiba.state;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.controller.Controller;
import g0803.bindingofshiba.gui.GUI;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.view.View;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class StateTest {

    @Test
    @SuppressWarnings("rawtypes")
    public void step() throws IOException {
        View<String> view = Mockito.mock(View.class);
        Controller<String> controller = Mockito.mock(Controller.class);

        String model = "Hello";
        Mockito.when(controller.getModel()).thenReturn(model);
        Mockito.when(view.getModel()).thenReturn(model);

        App app = Mockito.mock(App.class);
        GUI gui = Mockito.mock(GUI.class);

        State<String> state = new State(model, controller, view);
        state.step(app, gui);

        Mockito.verify(gui, Mockito.times(1)).clear();
        Mockito.verify(gui, Mockito.times(1)).refresh();
        Mockito.verify(view, Mockito.times(1)).draw(app, gui, Vec2D.zero());
        Mockito.verify(controller, Mockito.times(1)).tick(Mockito.eq(app), Mockito.anyDouble());
    }
}
