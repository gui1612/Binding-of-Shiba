package g0803.bindingofshiba.events.game;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.events.Event;
import g0803.bindingofshiba.model.game.elements.Monster;
import g0803.bindingofshiba.model.game.elements.Player;

public class PlayerDamagedByMonsterEvent extends Event {

    private final Player player;
    private final Monster monster;

    public PlayerDamagedByMonsterEvent(double dt, App app, Player player, Monster monster) {
        super(dt, app);

        this.player = player;
        this.monster = monster;
    }

    public Player getPlayer() {
        return player;
    }

    public Monster getMonster() {
        return monster;
    }
}
