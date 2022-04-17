package g0803.bindingofshiba.events.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.events.Event;
import g0803.bindingofshiba.model.game.elements.Monster;
import g0803.bindingofshiba.model.game.room.Room;

public class MonsterDamagedEvent extends Event {

    private final Monster monster;
    private final Room room;
    private final double damage;

    public MonsterDamagedEvent(double dt, App app, Monster monster, Room room, double damage) {
        super(dt, app);

        this.monster = monster;
        this.room = room;
        this.damage = damage;
    }

    public Monster getMonster() {
        return monster;
    }

    public double getDamage() {
        return damage;
    }

    public Room getRoom() {
        return room;
    }
}
