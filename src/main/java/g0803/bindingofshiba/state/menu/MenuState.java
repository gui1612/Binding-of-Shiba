package g0803.bindingofshiba.state.menu;

import g0803.bindingofshiba.controller.menu.MenuController;
import g0803.bindingofshiba.events.EventManager;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.model.menu.Menu;
import g0803.bindingofshiba.state.State;
import g0803.bindingofshiba.view.menu.MenuView;

public class MenuState extends State<Menu> {

    public MenuState(Menu model) {
        this(model, new EventManager());
    }

    public MenuState(Menu model, IEventManager eventManager) {
        super(model, new MenuController(model, eventManager), new MenuView(model, eventManager));
    }
}
