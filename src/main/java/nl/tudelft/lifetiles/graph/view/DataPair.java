package nl.tudelft.lifetiles.graph.view;

/**
 * A DataPair (point) in 2D space.
 *
 */
public final class DataPair {
    /**
     * X Coordinate in 2D space.
     */
    private double x;
    /**
     * Y Coordinate in 2D space.
     */
    private double y;

    /**
     *
     * @param xC
     *            x coordinate
     * @param yC
     *            y coordinate
     */
    public DataPair(final double xC, final double yC) {
        this.x = xC;
        this.y = yC;
    }

    /**
     *
     * @return x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     *
     * @return the y coordinate
     */
    public double getY() {
        return y;
    }
}
