package g0803.bindingofshiba.events;

import g0803.bindingofshiba.events.game.*;

public interface Observer {

    default void onPlayerCollisionWithMonster(PlayerCollisionWithMonsterEvent event) {}

    default void onMonsterCollisionWithMonster(MonsterCollisionWithMonsterEvent event) {}

    default void onPlayerCollisionWithObstacle(PlayerCollisionWithObstacleEvent event) {}

    default void onMonsterCollisionWithObstacle(MonsterCollisionWithObstacleEvent event) {}

    default void onPlayerCollisionWithWalls(PlayerCollisionWithWallsEvent event) {}

    default void onMonsterCollisionWithWalls(MonsterCollisionWithWallsEvent event) {}

    default void onPlayerEnterDoor(PlayerEnterDoorEvent event) {}

    default void onPlayerUnlockDoor(PlayerUnlockDoorEvent event) {}

    default void onProjectileCollisionWithMonster(ProjectileCollisionWithMonsterEvent event) {}

    default void onProjectileDestroyed(ProjectileDestroyedEvent event) {}

    default void onProjectileSpawned(ProjectileSpawnedEvent event) {}

    default void onMonsterDamaged(MonsterDamagedEvent event) {}

    default void onPlayerDamagedByMonster(PlayerDamagedByMonsterEvent event) {}
}
