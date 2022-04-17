package g0803.bindingofshiba.controller.game.events;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.controller.Controller;
import g0803.bindingofshiba.events.EventManager;
import g0803.bindingofshiba.model.game.Game;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CollisionEventsControllerTest {

    @Test
    public void tick() {
        App app = Mockito.mock(App.class);
        EventManager manager = Mockito.mock(EventManager.class);

        Controller<?> controller1 = Mockito.mock(Controller.class);
        Controller<?> controller2 = Mockito.mock(Controller.class);
        Controller<?> controller3 = Mockito.mock(Controller.class);

        Game game = Mockito.mock(Game.class);

        CollisionEventsController controller =
                new CollisionEventsController(
                        game, manager, List.of(controller1, controller2, controller3));
        controller.tick(app, 3);

        Mockito.verify(controller1).tick(app, 3);
        Mockito.verify(controller2).tick(app, 3);
        Mockito.verify(controller3).tick(app, 3);
    }
}
