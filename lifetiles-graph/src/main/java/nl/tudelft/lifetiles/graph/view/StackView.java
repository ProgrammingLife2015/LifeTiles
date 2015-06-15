package nl.tudelft.lifetiles.graph.view;

import java.util.Arrays;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import nl.tudelft.lifetiles.sequence.Mutation;

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
     * Width of the rectangle.
     */
    private final double rectangleWidth;

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
        rectangleWidth = width * SPACING_SCALE;

        getChildren().add(drawPart(VERTICAL_SCALE, 0.0, defaultColor));

        List<Paint> colors = Arrays.asList(Mutation.INSERTION.getColor(),
                Mutation.DELETION.getColor(), Mutation.POLYMORPHISM.getColor());

        double offset = 0.0;
        for (int index = 1; index <= colors.size(); index++) {
            double height = VERTICAL_SCALE * quantities.get(index) / max;
            getChildren().add(drawPart(height, offset, colors.get(index - 1)));
            offset += height;
        }
    }

    /**
     * Paints a single part of the stack view.
     *
     * @param height
     *            Height of the stack part.
     * @param offset
     *            Offset of the stack part.
     * @param paint
     *            Color of the stack part.
     * @return rectangle presentation of the stack part.
     */
    private Rectangle drawPart(final double height, final double offset,
            final Paint paint) {
        Rectangle rectangle = new Rectangle();
        rectangle.setLayoutY(offset);
        rectangle.setHeight(height);
        rectangle.setWidth(rectangleWidth);
        rectangle.setFill(paint);
        return rectangle;
    }

}
