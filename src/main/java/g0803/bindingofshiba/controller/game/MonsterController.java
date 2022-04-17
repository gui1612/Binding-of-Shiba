package g0803.bindingofshiba.controller.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.Constants;
import g0803.bindingofshiba.controller.Controller;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.events.Observer;
import g0803.bindingofshiba.events.game.*;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.model.game.elements.Monster;

public class MonsterController extends Controller<Game> implements Observer {

    public MonsterController(Game model, IEventManager eventManager) {
        super(model, eventManager);
        eventManager.addObserver(this);
    }

    private Vec2D getNextMonsterAcceleration(Monster monster) {
        Vec2D playerPosition = getModel().getPlayer().getPosition();
        Vec2D seekVector = playerPosition.subtract(monster.getPosition());
        Vec2D direction = seekVector.normalize();

        if (seekVector.getLengthSquared() < 225) direction = direction.scale(-1);

        Vec2D force = direction.scale(4);
        Vec2D drag = monster.getVelocity().scale(0.25);

        return force.subtract(drag);
    }

    @Override
    public void tick(App app, double dt) {
        for (Monster monster : getModel().getCurrentRoom().getMonsters()) {
            monster.move(dt);
            monster.setAcceleration(getNextMonsterAcceleration(monster));
        }
    }

    @Override
    public void onMonsterCollisionWithMonster(MonsterCollisionWithMonsterEvent event) {
        Monster monster = event.getFirstMonster();
        Monster other = event.getSecondMonster();
        double dt = event.getTickTime();

        Vec2D monsterVelocity = monster.getNextVelocity(dt);
        Vec2D otherVelocity = other.getNextVelocity(dt);

        Vec2D tangent = other.getNextPosition(dt).subtract(monster.getNextPosition(dt)).normalize();
        Vec2D normal = tangent.rotate(Math.PI / 2);

        double monsterTangent = monsterVelocity.scalarProduct(tangent);
        double monsterNormal = monsterVelocity.scalarProduct(normal);
        double otherTangent = otherVelocity.scalarProduct(tangent);
        double otherNormal = otherVelocity.scalarProduct(normal);

        Vec2D monsterResultingNormal = normal.scale(monsterNormal);
        Vec2D monsterResultingTangent =
                tangent.scale(otherTangent * Constants.MONSTER_COLLISION_ELASTICITY);
        Vec2D otherResultingNormal = normal.scale(otherNormal);
        Vec2D otherResultingTangent =
                tangent.scale(monsterTangent * Constants.MONSTER_COLLISION_ELASTICITY);

        monster.setVelocity(monsterResultingNormal.add(monsterResultingTangent));
        other.setVelocity(otherResultingNormal.add(otherResultingTangent));
    }

    @Override
    public void onPlayerCollisionWithMonster(PlayerCollisionWithMonsterEvent event) {
        Monster monster = event.getMonster();
        monster.setAcceleration(Vec2D.zero());
        monster.setVelocity(Vec2D.zero());
    }

    @Override
    public void onMonsterCollisionWithObstacle(MonsterCollisionWithObstacleEvent event) {
        Monster monster = event.getMonster();
        monster.setAcceleration(Vec2D.zero());
        monster.setVelocity(Vec2D.zero());
    }

    @Override
    public void onMonsterCollisionWithWalls(MonsterCollisionWithWallsEvent event) {
        Monster monster = event.getMonster();
        monster.setAcceleration(Vec2D.zero());
        monster.setVelocity(Vec2D.zero());
    }

    @Override
    public void onProjectileCollisionWithMonster(ProjectileCollisionWithMonsterEvent event) {
        Monster monster = event.getMonster();

        double startingHp = monster.getHp();
        monster.decreaseHpByAmount(event.getProjectile().getDamage());
        double endingHp = monster.getHp();

        if (startingHp != endingHp) {
            MonsterDamagedEvent newEvent =
                    new MonsterDamagedEvent(
                            event.getTickTime(),
                            event.getApp(),
                            monster,
                            getModel().getCurrentRoom(),
                            startingHp - endingHp);

            getEventManager().dispatchEvent(newEvent);
        }
    }
}
