package g0803.bindingofshiba.model.menu;

import g0803.bindingofshiba.model.game.Game;
import g0803.bindingofshiba.state.game.GameState;

public class MainMenu extends Menu {

    public MainMenu() {
        super(
                "Binding of Shiba",
                new MenuOption("Play", app -> app.setState(new GameState(new Game()))),
                new MenuOption("Exit", app -> app.setState(null)));
    }
}
