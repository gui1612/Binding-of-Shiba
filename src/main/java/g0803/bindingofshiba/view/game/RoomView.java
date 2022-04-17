package g0803.bindingofshiba.view.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.events.Observer;
import g0803.bindingofshiba.events.game.MonsterDamagedEvent;
import g0803.bindingofshiba.events.game.ProjectileDestroyedEvent;
import g0803.bindingofshiba.events.game.ProjectileSpawnedEvent;
import g0803.bindingofshiba.gui.GUI;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.model.game.elements.Monster;
import g0803.bindingofshiba.model.game.elements.Obstacle;
import g0803.bindingofshiba.model.game.elements.Projectile;
import g0803.bindingofshiba.model.game.room.Door;
import g0803.bindingofshiba.model.game.room.DoorPosition;
import g0803.bindingofshiba.model.game.room.Room;
import g0803.bindingofshiba.textures.ITexture;
import g0803.bindingofshiba.view.View;
import g0803.bindingofshiba.view.ViewFactory;
import java.util.*;

public class RoomView extends View<Room> implements Observer {

    private final ViewFactory<Monster> monsterViewFactory;
    private final ViewFactory<Obstacle> obstacleViewFactory;
    private final ViewFactory<Projectile> projectileViewFactory;

    private final List<View<Monster>> monsterViews = new ArrayList<>();
    private final List<View<Obstacle>> obstacleViews = new ArrayList<>();
    private final List<View<Projectile>> projectileViews = new ArrayList<>();

    public RoomView(Room model, IEventManager eventManager) {
        this(model, eventManager, MonsterView::new, ObstacleView::new, ProjectileView::new);
    }

    public RoomView(
            Room model,
            IEventManager eventManager,
            ViewFactory<Monster> monsterViewFactory,
            ViewFactory<Obstacle> obstacleViewFactory,
            ViewFactory<Projectile> projectileViewFactory) {
        super(model, eventManager);

        this.monsterViewFactory = monsterViewFactory;
        this.obstacleViewFactory = obstacleViewFactory;
        this.projectileViewFactory = projectileViewFactory;

        createViews();
        getEventManager().addObserver(this);
    }

    private void createViews() {
        monsterViews.clear();
        obstacleViews.clear();
        projectileViews.clear();

        for (Monster monster : getModel().getMonsters()) {
            monsterViews.add(monsterViewFactory.create(monster, getEventManager()));
        }

        for (Obstacle obstacle : getModel().getObstacles()) {
            obstacleViews.add(obstacleViewFactory.create(obstacle, getEventManager()));
        }

        for (Projectile projectile : getModel().getProjectiles()) {
            projectileViews.add(projectileViewFactory.create(projectile, getEventManager()));
        }
    }

    private ITexture getWallTextureFromDoorPosition(
            Bundle<ITexture> textures, DoorPosition doorPosition) {
        String builder =
                "room.walls."
                        + (getModel().getDoors().containsKey(doorPosition) ? "open." : "closed.")
                        + doorPosition.name().toLowerCase(Locale.ROOT);
        return textures.get(builder);
    }

    private void drawWalls(App app, GUI gui, Vec2D offset) {
        Bundle<ITexture> textures = app.getTextures();

        for (DoorPosition doorPosition : DoorPosition.values()) {
            ITexture texture = getWallTextureFromDoorPosition(textures, doorPosition);
            gui.blit((int) offset.getX(), (int) offset.getY(), texture);
        }
    }

    private void drawDoors(App app, GUI gui, Vec2D offset) {
        Bundle<ITexture> textures = app.getTextures();
        Map<DoorPosition, Door> doors = getModel().getDoors();

        for (Door door : doors.values()) {
            ITexture texture =
                    door.getUnlocked()
                            ? textures.get(door.getDoorPosition(getModel()).getOpenKey())
                            : textures.get(door.getDoorPosition(getModel()).getClosedKey());

            Vec2D position = door.getPositionByWall(getModel()).add(offset).round();
            gui.blit((int) position.getX(), (int) position.getY(), texture);
        }
    }

    private void drawObstacles(App app, GUI gui, Vec2D offset) {
        for (View<Obstacle> view : obstacleViews) {
            view.draw(app, gui, offset);
        }
    }

    private void drawMonsters(App app, GUI gui, Vec2D offset) {
        for (View<Monster> view : monsterViews) {
            view.draw(app, gui, offset);
        }
    }

    private void drawProjectiles(App app, GUI gui, Vec2D offset) {
        for (View<Projectile> view : projectileViews) {
            view.draw(app, gui, offset);
        }
    }

    @Override
    public void draw(App app, GUI gui, Vec2D offset) {
        drawWalls(app, gui, offset);
        drawDoors(app, gui, offset);
        drawObstacles(app, gui, offset);
        drawMonsters(app, gui, offset);
        drawProjectiles(app, gui, offset);
    }

    @Override
    public void onProjectileSpawned(ProjectileSpawnedEvent event) {
        if (event.getRoom() != getModel()) return;

        projectileViews.add(projectileViewFactory.create(event.getProjectile(), getEventManager()));
    }

    @Override
    public void onProjectileDestroyed(ProjectileDestroyedEvent event) {
        if (event.getRoom() != getModel()) return;

        projectileViews.removeIf(view -> view.getModel() == event.getProjectile());
    }

    @Override
    public void onMonsterDamaged(MonsterDamagedEvent event) {
        if (event.getRoom() != getModel() || event.getMonster().isAlive()) return;

        monsterViews.removeIf(view -> view.getModel() == event.getMonster());
    }
}
