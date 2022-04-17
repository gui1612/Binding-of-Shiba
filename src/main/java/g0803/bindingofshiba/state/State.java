package g0803.bindingofshiba.state;

import g0803.bindingofshiba.App;
import g0803.bindingofshiba.Constants;
import g0803.bindingofshiba.controller.Controller;
import g0803.bindingofshiba.gui.GUI;
import g0803.bindingofshiba.math.Vec2D;
import g0803.bindingofshiba.view.View;
import java.io.IOException;

public class State<T> {

    private final T model;
    private final Controller<T> controller;
    private final View<T> view;

    public State(T model, Controller<T> controller, View<T> view) {
        assert model != null && controller != null && view != null;
        assert controller.getModel() == model;
        assert view.getModel() == model;

        this.model = model;
        this.controller = controller;
        this.view = view;
    }

    public T getModel() {
        return model;
    }

    public Controller<T> getController() {
        return controller;
    }

    public View<T> getView() {
        return view;
    }

    public void step(App app, GUI gui) throws IOException {
        this.controller.tick(app, 1D / Constants.FPS);

        gui.clear();
        this.view.draw(app, gui, Vec2D.zero());
        gui.refresh();
    }
}
