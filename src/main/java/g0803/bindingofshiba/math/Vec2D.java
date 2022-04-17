package g0803.bindingofshiba.math;

import java.util.Objects;

public class Vec2D {

    private static final Vec2D RIGHT = new Vec2D(1, 0);
    private static final Vec2D UP = new Vec2D(0, -1);
    private static final Vec2D LEFT = new Vec2D(-1, 0);
    private static final Vec2D DOWN = new Vec2D(0, 1);
    private static final Vec2D ZERO = new Vec2D(0, 0);

    public static Vec2D zero() {
        return ZERO;
    }

    public static Vec2D right() {
        return RIGHT;
    }

    public static Vec2D up() {
        return UP;
    }

    public static Vec2D left() {
        return LEFT;
    }

    public static Vec2D down() {
        return DOWN;
    }

    private final double x, y;

    public Vec2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2D(Vec2D vec) {
        this(vec.x, vec.y);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public Vec2D setX(double x) {
        return new Vec2D(x, this.y);
    }

    public Vec2D setY(double y) {
        return new Vec2D(this.x, y);
    }

    public double getLength() {
        return Math.sqrt(this.getLengthSquared());
    }

    public double getLengthSquared() {
        return this.x * this.x + this.y * this.y;
    }

    public Vec2D add(Vec2D vec) {
        return new Vec2D(this.x + vec.x, this.y + vec.y);
    }

    public Vec2D subtract(Vec2D vec) {
        return new Vec2D(this.x - vec.x, this.y - vec.y);
    }

    public Vec2D scale(double scalar) {
        return new Vec2D(this.x * scalar, this.y * scalar);
    }

    public Vec2D scaleX(double scalar) {
        return this.setX(this.x * scalar);
    }

    public Vec2D scaleY(double scalar) {
        return this.setY(this.y * scalar);
    }

    public double scalarProduct(Vec2D vec) {
        return this.x * vec.x + this.y * vec.y;
    }

    public Vec2D rotate(double radians) {
        // We have to use the symmetric value of the sin
        // of the angle because our y-axis is inverted
        double sin = Math.sin(-radians);
        double cos = Math.cos(radians);

        return new Vec2D(this.x * cos - this.y * sin, this.x * sin + this.y * cos);
    }

    public Vec2D normalize() {
        return this.scale(1 / this.getLength());
    }

    public Vec2D round() {
        return new Vec2D(Math.round(this.x), Math.round(this.y));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vec2D that)) return false;

        return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0;
    }

    public boolean isSimilar(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vec2D that)) return false;

        float x1 = Math.round(this.x * 1000);
        float x2 = Math.round(that.x * 1000);
        float y1 = Math.round(this.y * 1000);
        float y2 = Math.round(that.y * 1000);

        return Float.compare(x1, x2) == 0 && Float.compare(y1, y2) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Vec2D{" + "x=" + x + ", y=" + y + '}';
    }
}
