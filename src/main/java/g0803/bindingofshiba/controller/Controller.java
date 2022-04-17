package g0803.bindingofshiba.controller;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.events.IEventManager;

public abstract class Controller<T> {

    private final T model;
    private final IEventManager eventManager;

    public Controller(T model, IEventManager eventManager) {
        this.model = model;
        this.eventManager = eventManager;
    }

    public T getModel() {
        return model;
    }

    public IEventManager getEventManager() {
        return eventManager;
    }

    public abstract void tick(App app, double dt);
}
