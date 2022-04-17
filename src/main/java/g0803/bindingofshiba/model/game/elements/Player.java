package g0803.bindingofshiba.model.game.elements;

import g0803.bindingofshiba.math.Vec2D;

public class Player extends MoveableElement {

    private int numberOfKeys;
    private int hp;
    private final int maxHp;
    private int damage;

    public Player(Vec2D position, int numberOfKeys, int hp, int damage) {
        super(position);
        this.numberOfKeys = numberOfKeys;
        this.hp = hp;
        this.maxHp = hp;
        this.damage = damage;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getNumberOfKeys() {
        return numberOfKeys;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void pickKey() {
        this.numberOfKeys++;
    }

    public void dropKey() {
        if (this.numberOfKeys <= 0) throw new IllegalStateException("There are no keys to remove");
        this.numberOfKeys--;
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public void decreaseHpByAmount(int amount) throws IllegalArgumentException {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");

        this.hp = Math.max(this.hp - amount, 0);
    }
}
