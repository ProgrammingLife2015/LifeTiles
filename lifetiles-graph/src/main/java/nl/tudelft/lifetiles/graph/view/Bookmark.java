package nl.tudelft.lifetiles.graph.view;

import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import nl.tudelft.lifetiles.annotation.model.KnownMutation;
import nl.tudelft.lifetiles.core.util.Settings;

/**
 * A bookmark is the equivalent of a known mutation mapped onto the graph.
 *
 * @author Jos
 *
 */
public class Bookmark extends Circle {

    /**
     * Final string for opacity settings property.
     */
    private static final String OPACITY_SETTING = "bookmark_opacity";

    /**
     * The standard opacity of the bookmark overlay.
     */
    private static final double OPACITY = Double.parseDouble(Settings
            .get(OPACITY_SETTING));

    /**
     * The standard color of the bookmark overlay.
     */
    private static final Color STANDARD_COLOR = Color.YELLOW;

    /**
     * Final string for radius settings property.
     */
    private static final String RADIUS_SETTING = "bookmark_radius";

    /**
     * The radius of the bookmark circle.
     */
    private static final double RADIUS = Double.parseDouble(Settings
            .get(RADIUS_SETTING));

    /**
     * Center of the segment so the origin of the circle is placed at the
     * correct segment position.
     */
    private static final double SEGMENT_CENTER = 0.5;

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
    public Bookmark(final VertexView vertex, final KnownMutation knownMutation,
            final long segmentPosition, final double scale) {
        super(RADIUS);
        setLayoutX(vertex.getLayoutX() + (segmentPosition + SEGMENT_CENTER)
                * vertex.HORIZONTALSCALE * scale);
        setLayoutY(vertex.getLayoutY() + vertex.getHeight() / 2);
        setOpacity(OPACITY);
        setFill(STANDARD_COLOR);

        Tooltip tooltip = new Tooltip(knownMutation.toString());
        Tooltip.install(this, tooltip);
    }
}
