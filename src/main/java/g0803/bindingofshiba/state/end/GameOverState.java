package g0803.bindingofshiba.state.end;

import g0803.bindingofshiba.controller.end.GameOverController;
import g0803.bindingofshiba.model.end.GameOver;
import g0803.bindingofshiba.state.State;
import g0803.bindingofshiba.view.end.GameOverView;

public class GameOverState extends State<GameOver> {

    public GameOverState(boolean victory) {
        this(new GameOver(victory));
    }

    public GameOverState(GameOver model) {
        super(model, new GameOverController(model, null), new GameOverView(model, null));
    }
}
