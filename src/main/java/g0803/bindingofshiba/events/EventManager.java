package g0803.bindingofshiba.events;

import g0803.bindingofshiba.events.game.*;
import java.lang.ref.WeakReference;
import java.util.*;

public class EventManager implements IEventManager {

    private final Set<WeakReference<Observer>> refs = new HashSet<>();

    @Override
    public void addObserver(Observer observer) {
        refs.add(new WeakReference<>(observer));
    }

    @Override
    public void removeObserver(Observer observer) {
        refs.removeIf(ref -> ref.refersTo(observer));
    }

    @Override
    public Set<Observer> getObservers() {
        Set<Observer> observers = new HashSet<>();

        Iterator<WeakReference<Observer>> iterator = refs.iterator();
        while (iterator.hasNext()) {
            WeakReference<Observer> ref = iterator.next();

            if (ref.refersTo(null)) {
                iterator.remove();
                continue;
            }

            observers.add(ref.get());
        }

        return observers;
    }

    private void dispatchEvent(Event event, Observer listener) {
        if (event instanceof PlayerCollisionWithMonsterEvent e) {
            listener.onPlayerCollisionWithMonster(e);
        } else if (event instanceof MonsterCollisionWithMonsterEvent e) {
            listener.onMonsterCollisionWithMonster(e);
        } else if (event instanceof PlayerCollisionWithObstacleEvent e) {
            listener.onPlayerCollisionWithObstacle(e);
        } else if (event instanceof MonsterCollisionWithObstacleEvent e) {
            listener.onMonsterCollisionWithObstacle(e);
        } else if (event instanceof PlayerCollisionWithWallsEvent e) {
            listener.onPlayerCollisionWithWalls(e);
        } else if (event instanceof MonsterCollisionWithWallsEvent e) {
            listener.onMonsterCollisionWithWalls(e);
        } else if (event instanceof PlayerEnterDoorEvent e) {
            listener.onPlayerEnterDoor(e);
        } else if (event instanceof PlayerUnlockDoorEvent e) {
            listener.onPlayerUnlockDoor(e);
        } else if (event instanceof ProjectileCollisionWithMonsterEvent e) {
            listener.onProjectileCollisionWithMonster(e);
        } else if (event instanceof ProjectileDestroyedEvent e) {
            listener.onProjectileDestroyed(e);
        } else if (event instanceof ProjectileSpawnedEvent e) {
            listener.onProjectileSpawned(e);
        } else if (event instanceof MonsterDamagedEvent e) {
            listener.onMonsterDamaged(e);
        }
    }

    @Override
    public void dispatchEvent(Event event) {
        for (Observer observer : getObservers()) {
            dispatchEvent(event, observer);
        }
    }
}
