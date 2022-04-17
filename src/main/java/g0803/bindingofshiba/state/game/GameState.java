package g0803.bindingofshiba.state.game;

import g0803.bindingofshiba.controller.game.GameController;
import g0803.bindingofshiba.events.EventManager;
import g0803.bindingofshiba.events.IEventManager;
import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.state.State;
import g0803.bindingofshiba.view.game.GameView;

public class GameState extends State<Game> {

    public GameState(Game game) {
        this(game, new EventManager());
    }

    public GameState(Game game, IEventManager eventManager) {
        super(game, new GameController(game, eventManager), new GameView(game, eventManager));
    }
}
