package g0803.bindingofshiba.model.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu {

    private int index = 0;
    private final String title;
    private final List<MenuOption> options = new ArrayList<>();

    public Menu(String title, MenuOption... menuOptions) {
        this.title = title;
        options.addAll(Arrays.asList(menuOptions));
    }

    public List<MenuOption> getOptions() {
        return options;
    }

    public MenuOption getSelectedOption() {
        return options.get(index);
    }

    public String getTitle() {
        return title;
    }

    public void nextOption() {
        index = Math.min(options.size() - 1, index + 1);
    }

    public void previousOption() {
        index = Math.max(0, index - 1);
    }
}
