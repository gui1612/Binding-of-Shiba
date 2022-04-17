package g0803.bindingofshiba.model.game.elements;

import g0803.bindingofshiba.math.Vec2D;

public class MoveableElement extends Element {

    private Vec2D velocity = Vec2D.zero();
    private Vec2D acceleration = Vec2D.zero();

    public MoveableElement(Vec2D position) {
        super(position);
    }

    public Vec2D getVelocity() {
        return velocity;
    }

    public Vec2D getAcceleration() {
        return acceleration;
    }

    public void setVelocity(Vec2D velocity) {
        this.velocity = velocity;
    }

    public void setAcceleration(Vec2D acceleration) {
        this.acceleration = acceleration;
    }

    public Vec2D getNextVelocity(double dt) {
        return this.velocity.add(this.acceleration.scale(dt));
    }

    public Vec2D getNextPosition(double dt) {
        return this.getPosition().add(getNextVelocity(dt).scale(dt));
    }

    public void move(double dt) {
        Vec2D nextVelocity = getNextVelocity(dt);
        Vec2D nextPosition = getNextPosition(dt);

        this.setVelocity(nextVelocity);
        this.setPosition(nextPosition);
    }
}
