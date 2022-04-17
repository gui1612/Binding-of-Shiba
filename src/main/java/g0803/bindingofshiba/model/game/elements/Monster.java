package g0803.bindingofshiba.model.game.elements;

import g0803.bindingofshiba.math.Vec2D;

public class Monster extends MoveableElement {

    private float hp;
    private float damage;

    public Monster(Vec2D position, float hp, float damage) {
        super(position);
        this.hp = hp;
        this.damage = damage;
    }

    public float getHp() {
        return this.hp;
    }

    public float getDamage() {
        return this.damage;
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public void decreaseHpByAmount(float amount) throws IllegalArgumentException {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");

        this.hp = Math.max(this.hp - amount, 0);
    }
}
