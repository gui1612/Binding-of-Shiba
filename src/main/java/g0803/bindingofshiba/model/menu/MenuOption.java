package g0803.bindingofshiba.model.menu;

import g0803.bindingofshiba.App;
import java.util.function.Consumer;

public class MenuOption {

    private final String text;
    private final Consumer<App> action;

    public MenuOption(String text, Consumer<App> action) {
        this.text = text;
        this.action = action;
    }

    public String getText() {
        return text;
    }

    public void execute(App app) {
        action.accept(app);
    }
}
