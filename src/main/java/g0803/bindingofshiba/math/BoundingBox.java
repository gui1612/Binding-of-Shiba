package g0803.bindingofshiba.math;

import java.util.Objects;

public class BoundingBox {

    private final double x, y;
    private final double width, height;

    public BoundingBox(double width, double height) {
        this(0, 0, width, height);
    }

    public BoundingBox(double x, double y, double width, double height) {
        if (width <= 0 || height <= 0)
            throw new IllegalArgumentException(
                    "Neither width nor height may not be negative nor zero");

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public BoundingBox(Vec2D topLeftCorner, double width, double height) {
        this(topLeftCorner.getX(), topLeftCorner.getY(), width, height);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Vec2D getTopLeftCorner() {
        return new Vec2D(x, y);
    }

    public Vec2D getTopRightCorner() {
        return new Vec2D(x + width, y);
    }

    public Vec2D getBottomLeftCorner() {
        return new Vec2D(x, y + height);
    }

    public Vec2D getBottomRightCorner() {
        return new Vec2D(x + width, y + height);
    }

    public Vec2D getDimensions() {
        return new Vec2D(width, height);
    }

    public BoundingBox translate(Vec2D vec) {
        return new BoundingBox(this.getTopLeftCorner().add(vec), this.width, this.height);
    }

    public BoundingBox translate(double x, double y) {
        return this.translate(new Vec2D(x, y));
    }

    public boolean collides(BoundingBox other) {
        Vec2D topLeft = this.getTopLeftCorner();
        Vec2D otherTopLeft = other.getTopLeftCorner();
        Vec2D bottomRight = this.getBottomRightCorner();
        Vec2D otherBottomRight = other.getBottomRightCorner();

        // Although the if statements are repetitive,
        // short-circuiting the method execution like this gives a good
        // performance increase when these operations are executed many times per second
        boolean isOnTheLeft =
                otherTopLeft.getX() < topLeft.getX() && otherBottomRight.getX() < topLeft.getX();
        if (isOnTheLeft) return false;

        boolean isOnTheRight =
                otherTopLeft.getX() > bottomRight.getX()
                        && otherBottomRight.getX() > bottomRight.getX();
        if (isOnTheRight) return false;

        boolean isOnTheTop =
                otherTopLeft.getY() < topLeft.getY() && otherBottomRight.getY() < topLeft.getY();
        if (isOnTheTop) return false;

        boolean isOnTheBottom =
                otherTopLeft.getY() > bottomRight.getY()
                        && otherBottomRight.getY() > bottomRight.getY();
        return !isOnTheBottom;
    }

    public boolean contains(BoundingBox other) {
        Vec2D topLeft = this.getTopLeftCorner();
        Vec2D otherTopLeft = other.getTopLeftCorner();
        Vec2D bottomRight = this.getBottomRightCorner();
        Vec2D otherBottomRight = other.getBottomRightCorner();

        boolean isTopLeftInside =
                otherTopLeft.getX() >= topLeft.getX()
                        && otherTopLeft.getY() >= topLeft.getY()
                        && otherTopLeft.getX() <= bottomRight.getX()
                        && otherTopLeft.getY() <= bottomRight.getY();

        if (!isTopLeftInside) return false;

        boolean isBottomRightInside =
                otherBottomRight.getX() >= topLeft.getX()
                        && otherBottomRight.getY() >= topLeft.getY()
                        && otherBottomRight.getX() <= bottomRight.getX()
                        && otherBottomRight.getY() <= bottomRight.getY();

        return isBottomRightInside;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoundingBox that)) return false;

        return Double.compare(that.x, x) == 0
                && Double.compare(that.y, y) == 0
                && Double.compare(that.width, width) == 0
                && Double.compare(that.height, height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height);
    }

    @Override
    public String toString() {
        return "BoundingBox{"
                + "x="
                + x
                + ", y="
                + y
                + ", width="
                + width
                + ", height="
                + height
                + '}';
    }

    public boolean isSimilar(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoundingBox that)) return false;

        Vec2D pos1 = this.getTopLeftCorner();
        Vec2D pos2 = that.getTopLeftCorner();
        Vec2D dim1 = this.getDimensions();
        Vec2D dim2 = that.getDimensions();

        return pos1.isSimilar(pos2) && dim1.isSimilar(dim2);
    }
}
