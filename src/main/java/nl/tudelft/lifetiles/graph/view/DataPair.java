package nl.tudelft.lifetiles.graph.view;

/**
 * A DataPair (point) in 2D space.
 *
 */
public final class DataPair {
    /**
     * X Coordinate in 2D space.
     */
    private final double xcoord;
    /**
     * Y Coordinate in 2D space.
     */
    private final double ycoord;

    /**
     *
     * @param xCoord
     *            x coordinate
     * @param yCoord
     *            y coordinate
     */
    public DataPair(final double xCoord, final double yCoord) {
        this.xcoord = xCoord;
        this.ycoord = yCoord;
    }

    /**
     *
     * @return x coordinate
     */
    public double getX() {
        return xcoord;
    }

    /**
     *
     * @return the y coordinate
     */
    public double getY() {
        return ycoord;
    }
}
