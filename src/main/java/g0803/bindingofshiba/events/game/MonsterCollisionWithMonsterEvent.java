package g0803.bindingofshiba.events.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.events.Event;
import g0803.bindingofshiba.model.game.elements.Monster;

public class MonsterCollisionWithMonsterEvent extends Event {

    private final Monster monster1, monster2;

    public MonsterCollisionWithMonsterEvent(
            double dt, App app, Monster monster1, Monster monster2) {
        super(dt, app);
        this.monster1 = monster1;
        this.monster2 = monster2;
    }

    public Monster getFirstMonster() {
        return monster1;
    }

    public Monster getSecondMonster() {
        return monster2;
    }
}
