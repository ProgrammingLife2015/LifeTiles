package nl.tudelft.lifetiles.graph.view;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
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
    private final Line line;

    /**
     * Create a new line from a vertex to another vertex. It tries to create a
     * straight line but if it fails then the line will be drawn from the middle
     * of the vertex to the middle of the other vertex.
     *
     * @param source
     *            Vertex to draw from
     * @param destination
     *            Vertex to draw to
     */
    public EdgeLine(final Node source, final Node destination) {
        this.line = new Line();
        Bounds boundFrom = source.getBoundsInParent();
        Bounds boundTo = destination.getBoundsInParent();

        if (outofboundsY(boundFrom, boundTo)) {
            drawCrossLine(source, destination, boundFrom, boundTo);
        } else {
            drawStraightLine(boundFrom, boundTo);
        }

    }

    /**
     *
     Calculate the y intersection of two line 2 sections by using the cross
     * product. See: http://en.wikipedia.org/wiki/Line%E2%80%93line_intersection
     * for more information.
     *
     * @param line0source
     *            Point 0 of the first line
     * @param line0destination
     *            Point 1 of the first line
     * @param line1source
     *            Point 0 of the second line
     * @param line1destination
     *            Point 1 of the second line
     * @return y intersection point
     */
    private double calculateYIntersection(final Point2D line0source,
            final Point2D line0destination, final Point2D line1source,
            final Point2D line1destination) {

        double s10x = line0destination.getX() - line0source.getX();
        double s10y = line0destination.getY() - line0source.getY();
        double s32x = line1destination.getX() - line1source.getX();
        double s32y = line1destination.getY() - line1source.getY();

        double denom = s10x * s32y - s32x * s10y;
        boolean denomPositive = denom > 0;

        double s02x = line0source.getX() - line1source.getX();
        double s02y = line0source.getY() - line1source.getY();
        double snumer = s10x * s02y - s10y * s02x;
        double tnumer = s32x * s02y - s32y * s02x;

        // No collision
        if (denom == 0 || (snumer < 0) == denomPositive
                || (tnumer < 0) == denomPositive
                || (snumer > denom) == denomPositive
                || (tnumer > denom) == denomPositive) {
            return -1;
        }

        // Collision detected
        return line0source.getY() + ((tnumer / denom) * s10y);
    }

    /**
     * Draw a Crossed line between two Vertices.
     *
     * @param source
     *            Start Vertex
     * @param destination
     *            Destination Vertex
     * @param boundFrom
     *            The Bounds of the start Vertex
     * @param boundTo
     *            The Bounds of the end Vertex
     */
    private void drawCrossLine(final Node source, final Node destination,
            final Bounds boundFrom, final Bounds boundTo) {
        Circle head = new Circle();
        head.setRadius(HEAD_RADIUS);

        double fromX = source.getLayoutX() + (boundFrom.getWidth() / 2);
        double fromY = source.getLayoutY() + (boundFrom.getHeight() / 2);

        // Draw the line from the middle of the destination
        double toX = destination.getLayoutX();
        double toY = destination.getLayoutY() + (boundTo.getHeight() / 2);

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
     *            starting x coordinate
     * @param startY
     *            starting y coordinate
     * @param endX
     *            ending x coordinate
     * @param endY
     *            ending y coordinate
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
     * Destination Vertex
     *
     * @param boundFrom
     *            The Bounds of the start Vertex
     * @param boundTo
     *            The Bounds of the end Vertex
     */
    private void drawStraightLine(final Bounds boundFrom, final Bounds boundTo) {

        Circle head = new Circle();
        head.setRadius(HEAD_RADIUS);

        double startX = boundFrom.getMinX() + (boundFrom.getWidth() / 2);

        // The line be drawn from the middle of the vertex to be drawn to
        final double minYLeft = boundFrom.getMinY();
        final double maxYLeft = boundFrom.getMaxY();
        final double maxXLeft = boundFrom.getMaxX();

        final double maxYRight = boundTo.getMaxY();
        final double minYRight = boundTo.getMinY();
        final double minXRight = boundTo.getMinX();

        if (minYLeft <= minYRight && maxYLeft >= maxYRight) {
            final double startY = minYRight + boundTo.getHeight() / 2;
            drawLine(startX, startY, minXRight, startY);
        } else {
            Point2D line0source = new Point2D(maxXLeft, minYLeft);
            Point2D line0destination = new Point2D(minXRight, maxYRight);
            Point2D line1source = new Point2D(maxXLeft, maxYLeft);
            Point2D line1destination = new Point2D(minXRight, minYRight);

            double intersect = calculateYIntersection(line0source,
                    line0destination, line1source, line1destination);

            final double endX = minXRight + (boundTo.getWidth() / 2);
            drawLine(startX, intersect, endX, intersect);
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
     * @param source
     *            the left bound
     * @param destination
     *            the right bound
     * @return true on y coordinate is outside of the range
     */
    private Boolean outofboundsY(final Bounds source, final Bounds destination) {
        if (source.getMinY() > destination.getMinY()
                && source.getMinY() > destination.getMaxY()
                && source.getMaxY() > destination.getMaxY()
                && source.getMaxY() > destination.getMinY()) {
            return true;
        }
        if (source.getMinY() < destination.getMinY()
                && source.getMinY() < destination.getMaxY()
                && source.getMaxY() < destination.getMaxY()
                && source.getMaxY() < destination.getMinY()) {
            return true;
        }
        return false;
    }

}
