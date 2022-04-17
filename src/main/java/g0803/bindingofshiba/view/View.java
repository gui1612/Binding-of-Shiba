package g0803.bindingofshiba.view;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.gui.GUI;
import g0803.bindingofshiba.math.Vec2D;

public abstract class View<T> {

    private final T model;
    private final IEventManager eventManager;

    public View(T model, IEventManager eventManager) {
        this.model = model;
        this.eventManager = eventManager;
    }

    public T getModel() {
        return model;
    }

    public IEventManager getEventManager() {
        return eventManager;
    }

    public abstract void draw(App app, GUI gui, Vec2D offset);
}
