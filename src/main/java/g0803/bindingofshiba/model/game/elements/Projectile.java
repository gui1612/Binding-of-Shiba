package g0803.bindingofshiba.model.game.elements;

import g0803.bindingofshiba.math.Vec2D;

public class Projectile extends MoveableElement {

    private final float damage;

    public Projectile(Vec2D position, float damage) {
        super(position);
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }
}
