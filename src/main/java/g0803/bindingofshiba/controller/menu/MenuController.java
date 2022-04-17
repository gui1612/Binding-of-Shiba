package g0803.bindingofshiba.controller.menu;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.controller.Controller;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.gui.keyboard.Keyboard;
import g0803.bindingofshiba.model.menu.Menu;

public class MenuController extends Controller<Menu> {

    public MenuController(Menu model, IEventManager eventManager) {
        super(model, eventManager);
    }

    @Override
    public void tick(App app, double dt) {
        Keyboard keyboard = app.getKeyboard();

        if (keyboard.isKeyPressed(Keyboard.Key.ARROW_UP)) {
            getModel().previousOption();
        } else if (keyboard.isKeyPressed(Keyboard.Key.ARROW_DOWN)) {
            getModel().nextOption();
        } else if (keyboard.isKeyPressed(Keyboard.Key.SPACE)
                || keyboard.isKeyPressed(Keyboard.Key.ENTER)) {
            getModel().getSelectedOption().execute(app);
        }
    }
}
