package nl.tudelft.lifetiles.graph.view;

import nl.tudelft.lifetiles.annotation.model.ResistanceAnnotation;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A bookmark is the equivalent of a annotation mapped onto the graph.
 *
 * @author Jos
 *
 */
public class Bookmark extends Group {

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
        setLayoutX(vertex.getLayoutX() + segmentPosition
                * vertex.HORIZONTALSCALE);
        setLayoutY(vertex.getLayoutY());

        Rectangle rectangle = new Rectangle(vertex.HORIZONTALSCALE,
                vertex.getHeight());
        rectangle.setOpacity(0.5);
        rectangle.setFill(Color.YELLOW);

        getChildren().add(rectangle);
    }

}
