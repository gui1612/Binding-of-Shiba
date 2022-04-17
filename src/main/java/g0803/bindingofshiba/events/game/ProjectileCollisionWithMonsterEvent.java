package g0803.bindingofshiba.events.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.events.Event;
import g0803.bindingofshiba.model.game.elements.Monster;
import g0803.bindingofshiba.model.game.elements.Projectile;

public class ProjectileCollisionWithMonsterEvent extends Event {

    private final Projectile projectile;
    private final Monster monster;

    public ProjectileCollisionWithMonsterEvent(
            double dt, App app, Projectile projectile, Monster monster) {
        super(dt, app);

        this.projectile = projectile;
        this.monster = monster;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public Monster getMonster() {
        return monster;
    }
}
