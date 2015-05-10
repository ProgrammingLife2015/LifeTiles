package nl.tudelft.lifetiles.tilegraph;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 * An edge is visual line drawn from a starting vertex to an end vertex. The
 * default drawing style is to draw a line from the middle of both boxes,
 * however this can be changed.
 *
 */
public class EdgeLine extends Group {

    /**
     * Defines the radius of the head circle.
     */
    private static final int HEAD_RADIUS = 1;

    /**
     * The line that this edge draws.
     */
    private Line line;

    /**
     * Create a new line from a vertex to another vertex. It tries to create a
     * straight line but if it fails then the line will be drawn from the middle
     * of the vertex to the middle of the other vertex.
     *
     * @param from
     *            Vertex to draw from
     * @param to
     *            Vertex to draw to
     */
    public EdgeLine(final Vertex from, final Vertex to) {
        this.line = new Line();
        Bounds boundFrom = from.getBoundsInParent();
        Bounds boundTo = to.getBoundsInParent();

        if (!outofboundsY(boundFrom, boundTo)) {
            drawStraightLine(from, to, boundFrom, boundTo);
        } else {
            drawCrossLine(from, to, boundFrom, boundTo);
        }

    }

    /**
     *
     Calculate the y intersection of two line 2 sections by using the cross
     * product. See: http://en.wikipedia.org/wiki/Line%E2%80%93line_intersection
     * for more information.
     *
     * @param p0
     *            - Point 0 of the first line
     * @param t0
     *            - Point 1 of the first line
     * @param p1
     *            - Point 0 of the second line
     * @param t1
     *            - Point 1 of the second line
     * @return - y intersection point
     */
    private double calculateYIntersection(final Pair p0, final Pair t0,
            final Pair p1, final Pair t1) {

        double s10x = t0.getX() - p0.getX();
        double s10y = t0.getY() - p0.getY();
        double s32x = t1.getX() - p1.getX();
        double s32y = t1.getY() - p1.getY();

        double denom = s10x * s32y - s32x * s10y;
        boolean denomPositive = denom > 0;

        double s02x = p0.getX() - p1.getX();
        double s02y = p0.getY() - p1.getY();
        double snumer = s10x * s02y - s10y * s02x;
        double tnumer = s32x * s02y - s32y * s02x;

        // No collision
        if ((denom == 0) || (snumer < 0) == denomPositive
                || (tnumer < 0) == denomPositive
                || ((snumer > denom) == denomPositive)
                || ((tnumer > denom) == denomPositive)) {
            return -1;
        }

        // Collision detected
        return p0.getY() + ((tnumer / denom) * s10y);
    }

    /**
     * Draw a Crossed line between two Vertices.
     *
     * @param from
     *            - Start Vertex
     * @param to
     *            - Destination Vertex
     * @param boundFrom
     *            - The Bounds of the start Vertex
     * @param boundTo
     *            - The Bounds of the end Vertex
     */
    private void drawCrossLine(final Vertex from, final Vertex to,
            final Bounds boundFrom, final Bounds boundTo) {
        Circle head = new Circle();
        head.setRadius(HEAD_RADIUS);

        double fromX = from.getLayoutX() + (boundFrom.getWidth() / 2);
        double fromY = from.getLayoutY() + (boundFrom.getHeight() / 2);

        // Draw the line from the middle of the destination
        double toX = to.getLayoutX();
        double toY = to.getLayoutY() + (boundTo.getHeight() / 2);

        drawLine(fromX, fromY, toX, toY);

        head.setCenterX(line.getEndX() - head.getRadius());

        // Check if the head has to be moved slightly up and down
        // to display it more clearly
        if (line.getEndY() - line.getStartY() > 0) {
            head.setCenterY(line.getEndY() - head.getRadius());
        }
        if (line.getEndY() - line.getStartY() < 0) {
            head.setCenterY(line.getEndY() + head.getRadius());
        }

        this.getChildren().addAll(line, head);
    }

    /**
     * Draw a line given the start and end coordinates.
     *
     * @param startX
     *            - starting x coordinate
     * @param startY
     *            - starting y coordinate
     * @param endX
     *            - ending x coordinate
     * @param endY
     *            - ending y coordinate
     */
    private void drawLine(final double startX, final double startY,
            final double endX, final double endY) {
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
    }

    /**
     * Draw a straight between two vertices.
     *
     * @param from
     *            - Start Vertex
     * @param to
     *            - Destination Vertex
     * @param boundFrom
     *            - The Bounds of the start Vertex
     * @param boundTo
     *            - The Bounds of the end Vertex
     */
    private void drawStraightLine(final Vertex from, final Vertex to,
            final Bounds boundFrom, final Bounds boundTo) {
        Circle head = new Circle();
        head.setRadius(HEAD_RADIUS);

        double startX = boundFrom.getMinX() + (boundFrom.getWidth() / 2);

        // The line be drawn from the middle of the vertex to be drawn to
        if (boundFrom.getMinY() <= boundTo.getMinY()
                && boundFrom.getMaxY() >= boundTo.getMaxY()) {

            drawLine(startX, boundTo.getMinY() + boundTo.getHeight() / 2,
                    boundTo.getMinX(), boundTo.getMinY() + boundTo.getHeight()
                            / 2);

        } else {
            Pair p0 = new Pair(boundFrom.getMaxX(), boundFrom.getMinY());
            Pair t0 = new Pair(boundTo.getMinX(), boundTo.getMaxY());
            Pair p1 = new Pair(boundFrom.getMaxX(), boundFrom.getMaxY());
            Pair t1 = new Pair(boundTo.getMinX(), boundTo.getMinY());

            double intersect = calculateYIntersection(p0, t0, p1, t1);

            drawLine(startX, intersect, boundTo.getMinX()
                    + (boundTo.getWidth() / 2), intersect);
        }
        head.setCenterX(line.getEndX() - head.getRadius());
        head.setCenterY(line.getEndY());

        this.getChildren().addAll(line, head);
    }

    /**
     * Returns the line that this edge draws.
     *
     * @return drawn line
     */
    public final Line getLine() {
        return line;
    }

    /**
     * Check if the y coordinates of 'to' lie outside the range of the y
     * coordinates of 'from'.
     *
     * @param from
     *            the left bound
     * @param to
     *            the right bound
     * @return true on y coordinate is outside of the range
     */
    private Boolean outofboundsY(final Bounds from, final Bounds to) {
        if (from.getMinY() > to.getMinY() && from.getMinY() > to.getMaxY()
                && from.getMaxY() > to.getMaxY()
                && from.getMaxY() > to.getMinY()) {
            return true;
        }
        if (from.getMinY() < to.getMinY() && from.getMinY() < to.getMaxY()
                && from.getMaxY() < to.getMaxY()
                && from.getMaxY() < to.getMinY()) {
            return true;
        }
        return false;
    }

}

/**
 * A Pair (point) in 2D space.
 *
 */
final class Pair {
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
     *            - x coordinate
     * @param yC
     *            - y coordinate
     */
    public Pair(final double xC, final double yC) {
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
