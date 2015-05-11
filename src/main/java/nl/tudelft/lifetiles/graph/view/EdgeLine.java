package nl.tudelft.lifetiles.graph.view;

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
@SuppressWarnings("restriction")
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
    public EdgeLine(final VertexView from, final VertexView to) {
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
    private double calculateYIntersection(final DataPair p0, final DataPair t0,
            final DataPair p1, final DataPair t1) {

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
    private void drawCrossLine(final VertexView from, final VertexView to,
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
    private void drawStraightLine(final VertexView from, final VertexView to,
            final Bounds boundFrom, final Bounds boundTo) {
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
            DataPair p0 = new DataPair(maxXLeft, minYLeft);
            DataPair t0 = new DataPair(minXRight, maxYRight);
            DataPair p1 = new DataPair(maxXLeft, maxYLeft);
            DataPair t1 = new DataPair(minXRight, minYRight);

            double intersect = calculateYIntersection(p0, t0, p1, t1);

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
