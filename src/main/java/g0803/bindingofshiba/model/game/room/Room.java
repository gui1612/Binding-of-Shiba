package g0803.bindingofshiba.model.game.room;

import g0803.bindingofshiba.model.game.elements.Monster;
import g0803.bindingofshiba.model.game.elements.Obstacle;
import g0803.bindingofshiba.model.game.elements.Projectile;
import java.util.*;

public class Room {
    private final int width;
    private final int height;
    private final List<Monster> monsters;
    private final Map<DoorPosition, Door> doors = new HashMap<>();
    private final List<Obstacle> obstacles;
    private final Set<Projectile> projectiles = new HashSet<>();

    public Room(int width, int height, List<Monster> monsters, List<Obstacle> obstacles) {
        this.width = width;
        this.height = height;
        this.monsters = new ArrayList<>();
        this.obstacles = new ArrayList<>();

        if (obstacles != null) {
            this.obstacles.addAll(obstacles);
        }

        if (monsters != null) {
            this.monsters.addAll(monsters);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Obstacle> getObstacles() {
        return this.obstacles;
    }

    public List<Monster> getMonsters() {
        return this.monsters;
    }

    public Set<Projectile> getProjectiles() {
        return projectiles;
    }

    public Map<DoorPosition, Door> getDoors() {
        return this.doors;
    }

    public void addDoor(Door door) {
        if (this != door.getOriginRoom() && this != door.getDestinationRoom()) {
            throw new IllegalArgumentException("Door does not belong to this room");
        }

        DoorPosition position =
                this == door.getOriginRoom()
                        ? door.getOriginPosition()
                        : door.getDestinationPosition();
        if (doors.containsKey(position)) {
            throw new IllegalStateException("There can't be two doors in the same position");
        }

        doors.put(position, door);
    }

    public void addProjectile(Projectile projectile) {
        this.projectiles.add(projectile);
    }

    public void removeProjectile(Projectile projectile) {
        this.projectiles.remove(projectile);
    }
}
