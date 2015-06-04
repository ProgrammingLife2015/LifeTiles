package nl.tudelft.lifetiles.graph.view;

import javafx.geometry.Bounds;
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
    private static final int HEAD_RADIUS = 2;

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
        line = new Line();
        line.getStyleClass().add("edgeline");

        Bounds sourceBound = source.getBoundsInParent();
        Bounds destinationBound = destination.getBoundsInParent();

        if (outofboundsY(sourceBound, destinationBound)) {
            drawCrossLine(sourceBound, destinationBound);
        } else {
            drawStraightLine(sourceBound, destinationBound);
        }

    }

    /**
     * Draw a Crossed line between two Vertices.
     *
     * @param source
     *            The Bounds of the start Vertex
     * @param destination
     *            The Bounds of the end Vertex
     */
    private void drawCrossLine(final Bounds source, final Bounds destination) {

        double fromX = source.getMaxX();
        double fromY = source.getMinY() + (source.getHeight() / 2);

        double destX = destination.getMinX();
        double destY = destination.getMinY() + (destination.getHeight() / 2);

        drawLine(fromX, fromY, destX, destY);

    }

    /**
     * Draw a straight line between two vertices.
     *
     * @param source
     *            The Bounds of the start Vertex
     * @param destination
     *            The Bounds of the end Vertex
     */
    private void drawStraightLine(final Bounds source, final Bounds destination) {

        double startY = 0;

        // Decide from which bound the Y value should be taken from
        if (source.getMaxY() < destination.getMaxY()
                || source.getMinY() > destination.getMinY()) {

            startY = source.getMinY() + source.getHeight() / 2;
        } else {
            startY = destination.getMinY() + destination.getHeight() / 2;
        }

        drawLine(source.getMaxX(), startY, destination.getMinX(), startY);

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

        Circle head = new Circle(HEAD_RADIUS);
        Circle tail = new Circle(HEAD_RADIUS);

        head.getStyleClass().add("edgeHead");
        tail.getStyleClass().add("edgeTail");

        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);

        head.setCenterX(line.getStartX());
        head.setCenterY(line.getStartY());
        tail.setCenterX(line.getEndX());
        tail.setCenterY(line.getEndY());

        this.getChildren().addAll(line, head, tail);
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
     * Check if the y coordinates of the source lies completely above or below
     * the destination.
     *
     * @param source
     *            the left bound
     * @param destination
     *            the right bound
     * @return true if source lies indeed lies above or below
     */
    private Boolean outofboundsY(final Bounds source, final Bounds destination) {
        // Check if source is completely below the destination

        if (source.getMinY() > destination.getMaxY()
                && source.getMaxY() > destination.getMinY()
                && source.getMinY() > destination.getMinY()
                && source.getMaxY() > destination.getMaxY()) {

            return true;
        }
        // Check if source is completely above the destination
        if (source.getMinY() < destination.getMaxY()
                && source.getMaxY() < destination.getMinY()
                && source.getMinY() < destination.getMinY()
                && source.getMaxY() < destination.getMaxY()) {
            return true;
        }

        return false;
    }
}
