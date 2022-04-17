package g0803.bindingofshiba.events;

import java.util.Set;

public interface IEventManager {

    Set<Observer> getObservers();

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void dispatchEvent(Event event);
}
