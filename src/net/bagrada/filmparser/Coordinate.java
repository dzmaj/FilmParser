package net.bagrada.filmparser;

public class Coordinate {
    private long x;
    private long y;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("x=").append(String.format("%.3f", x/512.0));
        sb.append(", y=").append(String.format("%.3f", y/512.0));
        sb.append('}');
        return sb.toString();
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }
}
