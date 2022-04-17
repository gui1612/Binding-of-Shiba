package g0803.bindingofshiba.controller.end;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.controller.Controller;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.model.end.GameOver;
import g0803.bindingofshiba.model.menu.MainMenu;
import g0803.bindingofshiba.state.menu.MenuState;

public class GameOverController extends Controller<GameOver> {

    private double timeToSwitch = 5D;

    public GameOverController(GameOver model, IEventManager eventManager) {
        super(model, eventManager);
    }

    @Override
    public void tick(App app, double dt) {
        timeToSwitch -= dt;

        if (timeToSwitch <= 0) {
            app.setState(new MenuState(new MainMenu()));
        }
    }
}
