package nl.tudelft.lifetiles.graph.view;

import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nl.tudelft.lifetiles.annotation.model.KnownMutation;

/**
 * A bookmark is the equivalent of a known mutation mapped onto the graph.
 *
 * @author Jos
 *
 */
public class Bookmark extends Rectangle {

    /**
     * The standard opacity of the bookmark overlay.
     */
    private static final double OPACITY = 0.5;

    /**
     * The standard color of the bookmark overlay.
     */
    private static final Color STANDARD_COLOR = Color.YELLOW;

    /**
     * Constructs a bookmark from a vertex, a annotation and a segment position.
     *
     * @param vertex
     *            The vertex the bookmark is placed on.
     * @param knownMutation
     *            The known mutation which is visualized using this bookmark.
     * @param segmentPosition
     *            The position of the bookmark on the segment.
     * @param scale
     *            the factor to resize this bookmark
     */
    public Bookmark(final VertexView vertex,
            final KnownMutation knownMutation, final long segmentPosition,
            final double scale) {
        super(vertex.HORIZONTALSCALE * scale, vertex.getHeight());
        setLayoutX(vertex.getLayoutX() + segmentPosition
                * vertex.HORIZONTALSCALE * scale);
        setLayoutY(vertex.getLayoutY());
        setOpacity(OPACITY);
        setFill(STANDARD_COLOR);

        Tooltip tooltip = new Tooltip(knownMutation.toString());
        Tooltip.install(this, tooltip);
    }
}
