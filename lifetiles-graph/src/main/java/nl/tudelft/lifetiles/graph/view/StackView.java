package nl.tudelft.lifetiles.graph.view;

import java.util.List;

import nl.tudelft.lifetiles.core.util.ColorUtils;
import nl.tudelft.lifetiles.sequence.Mutation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Stack view class which contains a single stacked column of the diagram view.
 * Contains several rectangles proportional to the percentage of mutations in
 * the bucket.
 * 
 * @author Jos
 *
 */
public class StackView extends Group {
    /**
     * Default color of a tile element.
     */
    private static Color defaultColor = Color.web("a1d3ff");
    /**
     * Vertical scale of the StackView.
     */
    private static final double VERTICAL_SCALE = 300;
    /**
     * Horizontal scale of the StackView.
     */
    static final double HORIZONTAL_SCALE = 50;

    /**
     * Constructs a stack view, which contains a single stacked column of the
     * diagram view.
     * 
     * @param quantities
     *            List with quantities of the total vertices and mutations.
     * @param zoomLevel 
     * @param max 
     */
    StackView(List<Long> quantities, double zoomLevel, Long max) {
        Rectangle reference = new Rectangle();
        reference.setHeight(VERTICAL_SCALE);
        reference.setWidth(HORIZONTAL_SCALE * zoomLevel);
        reference.setStyle("-fx-fill:" + ColorUtils.webCode(defaultColor));

        Rectangle insertion = new Rectangle();
        insertion.setHeight(VERTICAL_SCALE * quantities.get(1)
                / max);
        insertion.setWidth(HORIZONTAL_SCALE * zoomLevel);
        insertion.setStyle("-fx-fill:"
                + ColorUtils.webCode(Mutation.INSERTION.getColor()));

        Rectangle deletion = new Rectangle();
        deletion.setLayoutY(insertion.getHeight());
        deletion.setHeight(VERTICAL_SCALE * quantities.get(2)
                / max);
        deletion.setWidth(HORIZONTAL_SCALE * zoomLevel);
        deletion.setStyle("-fx-fill:"
                + ColorUtils.webCode(Mutation.DELETION.getColor()));

        Rectangle polymorphism = new Rectangle();
        polymorphism.setLayoutY(insertion.getHeight() + deletion.getHeight());
        polymorphism.setHeight(VERTICAL_SCALE * quantities.get(3)
                / max);
        polymorphism.setWidth(HORIZONTAL_SCALE * zoomLevel);
        polymorphism.setStyle("-fx-fill:"
                + ColorUtils.webCode(Mutation.POLYMORPHISM.getColor()));

        Group bars = new Group();
        getChildren().addAll(reference, insertion, deletion, polymorphism);
    }

}
