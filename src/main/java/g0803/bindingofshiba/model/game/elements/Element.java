package g0803.bindingofshiba.model.game.elements;

import g0803.bindingofshiba.math.Vec2D;

public class Element {

    private Vec2D position;

    public Element(Vec2D position) {
        this.position = position;
    }

    public Element(int x, int y) {
        this.position = new Vec2D(x, y);
    }

    public Vec2D getPosition() {
        return position;
    }

    public void setPosition(Vec2D position) {
        this.position = position;
    }
}
