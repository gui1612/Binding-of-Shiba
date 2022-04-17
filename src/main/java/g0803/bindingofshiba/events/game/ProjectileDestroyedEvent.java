package g0803.bindingofshiba.events.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.events.Event;
import g0803.bindingofshiba.model.game.elements.Projectile;
import g0803.bindingofshiba.model.game.room.Room;

public class ProjectileDestroyedEvent extends Event {

    private final Projectile projectile;
    private final Room room;

    public ProjectileDestroyedEvent(double dt, App app, Projectile projectile, Room room) {
        super(dt, app);

        this.projectile = projectile;
        this.room = room;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public Room getRoom() {
        return room;
    }
}
