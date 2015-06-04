package nl.tudelft.lifetiles.graph.view;

import nl.tudelft.lifetiles.annotation.model.ResistanceAnnotation;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Tooltip;

/**
 * A bookmark is the equivalent of a annotation mapped onto the graph.
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
     * @param annotation
     *            The annotation which is visualized using this bookmark.
     * @param segmentPosition
     *            The position of the bookmark on the segment.
     */
    public Bookmark(final VertexView vertex,
            final ResistanceAnnotation annotation, final long segmentPosition) {
        super(vertex.HORIZONTALSCALE, vertex.getHeight());
        setLayoutX(vertex.getLayoutX() + segmentPosition
                * vertex.HORIZONTALSCALE);
        setLayoutY(vertex.getLayoutY());
        setOpacity(OPACITY);
        setFill(STANDARD_COLOR);

        Tooltip tooltip = new Tooltip(annotation.toString());
        Tooltip.install(this, tooltip);
    }
}
