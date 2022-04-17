package g0803.bindingofshiba.view;

import g0803.bindingofshiba.events.IEventManager;

public interface ViewFactory<T> {

    View<T> create(T model, IEventManager eventManager);
}
