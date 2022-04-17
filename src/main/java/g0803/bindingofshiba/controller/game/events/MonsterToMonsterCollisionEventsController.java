package g0803.bindingofshiba.controller.game.events;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.bundles.Bundle;
import g0803.bindingofshiba.controller.Controller;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.events.game.MonsterCollisionWithMonsterEvent;
import g0803.bindingofshiba.math.BoundingBox;
import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.model.game.elements.Monster;
import java.util.ArrayList;
import java.util.List;

public class MonsterToMonsterCollisionEventsController extends Controller<Game> {

    public MonsterToMonsterCollisionEventsController(Game model, IEventManager eventManager) {
        super(model, eventManager);
    }

    @Override
    public void tick(App app, double dt) {
        Bundle<BoundingBox> boundingBoxes = app.getBoundingBoxes();
        List<Monster> monsters = getModel().getCurrentRoom().getMonsters();
        ArrayList<MonsterCollisionWithMonsterEvent> eventsToDispatch = new ArrayList<>();

        BoundingBox monsterBox = boundingBoxes.get("monster");
        for (int i = 0; i < monsters.size() - 1; i++) {
            Monster monster1 = monsters.get(i);

            for (int j = i + 1; j < monsters.size(); j++) {
                Monster monster2 = monsters.get(j);

                BoundingBox monsterBox1 = monsterBox.translate(monster1.getNextPosition(dt));
                BoundingBox monsterBox2 = monsterBox.translate(monster2.getNextPosition(dt));
                if (monsterBox1.collides(monsterBox2))
                    eventsToDispatch.add(
                            new MonsterCollisionWithMonsterEvent(dt, app, monster1, monster2));
            }
        }

        for (MonsterCollisionWithMonsterEvent event : eventsToDispatch)
            getEventManager().dispatchEvent(event);
    }
}
