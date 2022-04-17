package g0803.bindingofshiba.events;

import g0803.bindingofshiba.events.game.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class EventManagerTest {

    @Test
    public void dispatchEventWithMultipleObservers() {
        IEventManager manager = new EventManager();

        Observer observer1 = Mockito.mock(Observer.class);
        Observer observer2 = Mockito.mock(Observer.class);

        manager.addObserver(observer1);
        manager.addObserver(observer2);

        MonsterCollisionWithMonsterEvent event1 =
                Mockito.mock(MonsterCollisionWithMonsterEvent.class);
        PlayerCollisionWithMonsterEvent event2 =
                Mockito.mock(PlayerCollisionWithMonsterEvent.class);

        manager.dispatchEvent(event1);
        manager.dispatchEvent(event2);

        Mockito.verify(observer1).onMonsterCollisionWithMonster(event1);
        Mockito.verify(observer1).onPlayerCollisionWithMonster(event2);
        Mockito.verify(observer2).onMonsterCollisionWithMonster(event1);
        Mockito.verify(observer2).onPlayerCollisionWithMonster(event2);
    }

    @Test
    public void removeObserver() {
        IEventManager manager = new EventManager();

        Observer observer = Mockito.mock(Observer.class);

        manager.addObserver(observer);
        manager.removeObserver(observer);

        MonsterCollisionWithMonsterEvent event1 =
                Mockito.mock(MonsterCollisionWithMonsterEvent.class);
        PlayerCollisionWithMonsterEvent event2 =
                Mockito.mock(PlayerCollisionWithMonsterEvent.class);

        manager.dispatchEvent(event1);
        manager.dispatchEvent(event2);

        Mockito.verifyNoInteractions(observer);
    }

    @Test
    public void dispatchesEvents() {
        IEventManager manager = new EventManager();

        Observer observer = Mockito.mock(Observer.class);
        manager.addObserver(observer);

        MonsterCollisionWithMonsterEvent event1 =
                Mockito.mock(MonsterCollisionWithMonsterEvent.class);
        MonsterCollisionWithObstacleEvent event2 =
                Mockito.mock(MonsterCollisionWithObstacleEvent.class);
        MonsterCollisionWithWallsEvent event3 = Mockito.mock(MonsterCollisionWithWallsEvent.class);
        PlayerCollisionWithMonsterEvent event4 =
                Mockito.mock(PlayerCollisionWithMonsterEvent.class);
        PlayerCollisionWithObstacleEvent event5 =
                Mockito.mock(PlayerCollisionWithObstacleEvent.class);
        PlayerCollisionWithWallsEvent event6 = Mockito.mock(PlayerCollisionWithWallsEvent.class);
        PlayerEnterDoorEvent event7 = Mockito.mock(PlayerEnterDoorEvent.class);
        PlayerUnlockDoorEvent event8 = Mockito.mock(PlayerUnlockDoorEvent.class);
        ProjectileCollisionWithMonsterEvent event9 =
                Mockito.mock(ProjectileCollisionWithMonsterEvent.class);
        ProjectileDestroyedEvent event10 = Mockito.mock(ProjectileDestroyedEvent.class);
        ProjectileSpawnedEvent event11 = Mockito.mock(ProjectileSpawnedEvent.class);
        MonsterDamagedEvent event12 = Mockito.mock(MonsterDamagedEvent.class);

        manager.dispatchEvent(event1);
        manager.dispatchEvent(event2);
        manager.dispatchEvent(event3);
        manager.dispatchEvent(event4);
        manager.dispatchEvent(event5);
        manager.dispatchEvent(event6);
        manager.dispatchEvent(event7);
        manager.dispatchEvent(event8);
        manager.dispatchEvent(event9);
        manager.dispatchEvent(event10);
        manager.dispatchEvent(event11);
        manager.dispatchEvent(event12);

        Mockito.verify(observer).onMonsterCollisionWithMonster(event1);
        Mockito.verify(observer).onMonsterCollisionWithObstacle(event2);
        Mockito.verify(observer).onMonsterCollisionWithWalls(event3);
        Mockito.verify(observer).onPlayerCollisionWithMonster(event4);
        Mockito.verify(observer).onPlayerCollisionWithObstacle(event5);
        Mockito.verify(observer).onPlayerCollisionWithWalls(event6);
        Mockito.verify(observer).onPlayerEnterDoor(event7);
        Mockito.verify(observer).onPlayerUnlockDoor(event8);
        Mockito.verify(observer).onProjectileCollisionWithMonster(event9);
        Mockito.verify(observer).onProjectileDestroyed(event10);
        Mockito.verify(observer).onProjectileSpawned(event11);
        Mockito.verify(observer).onMonsterDamaged(event12);
    }
}
