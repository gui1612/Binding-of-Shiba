package g0803.bindingofshiba.events;

import g0803.bindingofshiba.App;

public class Event {
    private final double dt;
    private final App app;

    public Event(double dt, App app) {
        this.dt = dt;
        this.app = app;
    }

    public App getApp() {
        return app;
    }

    public double getTickTime() {
        return dt;
    }
}
