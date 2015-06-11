package nl.tudelft.lifetiles.graph.view;

import java.util.List;

import nl.tudelft.lifetiles.core.util.ColorUtils;
import nl.tudelft.lifetiles.sequence.Mutation;
import javafx.scene.Group;
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
     * Scale used to create the spacing between the different stack views in the
     * diagram view.
     */
    private static final double SPACING_SCALE = 0.85;

    /**
     * String used to assign the style field for the background color of the
     * rectangle.
     */
    private static final String FX_FILL = "-fx-fill:";

    /**
     * Constructs a stack view, which contains a single stacked column of the
     * diagram view.
     *
     * @param quantities
     *            List with quantities of the total vertices and mutations.
     * @param width
     *            Width of this stack view based on the zoom level of the graph
     *            controller.
     * @param max
     *            The maximal scale of the quantity percentage, we just want to
     *            see the interesting information.
     */
    StackView(final List<Long> quantities, final double width, final Long max) {
        double rectangleWidth = width * SPACING_SCALE;

        Rectangle reference = new Rectangle();
        reference.setHeight(VERTICAL_SCALE);
        reference.setWidth(rectangleWidth);
        reference.setStyle(FX_FILL + ColorUtils.webCode(defaultColor));

        Rectangle insertion = new Rectangle();
        insertion.setHeight(VERTICAL_SCALE * quantities.get(1) / max);
        insertion.setWidth(rectangleWidth);
        insertion.setStyle(FX_FILL
                + ColorUtils.webCode(Mutation.INSERTION.getColor()));

        Rectangle deletion = new Rectangle();
        deletion.setLayoutY(insertion.getHeight());
        deletion.setHeight(VERTICAL_SCALE * quantities.get(2) / max);
        deletion.setWidth(rectangleWidth);
        deletion.setStyle(FX_FILL
                + ColorUtils.webCode(Mutation.DELETION.getColor()));

        Rectangle polymorphism = new Rectangle();
        polymorphism.setLayoutY(insertion.getHeight() + deletion.getHeight());
        polymorphism.setHeight(VERTICAL_SCALE * quantities.get(3) / max);
        polymorphism.setWidth(rectangleWidth);
        polymorphism.setStyle(FX_FILL
                + ColorUtils.webCode(Mutation.POLYMORPHISM.getColor()));

        getChildren().addAll(reference, insertion, deletion, polymorphism);
    }

}
