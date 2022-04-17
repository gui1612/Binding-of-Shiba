package g0803.bindingofshiba.events;

import g0803.bindingofshiba.App;

public class CancellableEvent extends Event {

    private boolean cancelled = false;

    public CancellableEvent(double dt, App app) {
        super(dt, app);
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
