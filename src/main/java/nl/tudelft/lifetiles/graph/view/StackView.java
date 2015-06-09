package nl.tudelft.lifetiles.graph.view;

import java.util.List;

import nl.tudelft.lifetiles.core.util.ColorUtils;
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
public class StackView extends Rectangle {
    /**
     * Default color of a tile element.
     */
    private static Color defaultColor = Color.web("a1d3ff");
    /**
     * Vertical scale of the StackView.
     */
    private static final double VERTICAL_SCALE = 30;
    /**
     * Horizontal scale of the StackView.
     */
    static final double HORIZONTAL_SCALE = 30;

    /**
     * Constructs a stack view, which contains a single stacked column of the
     * diagram view.
     * 
     * @param quantities
     *            List with quantities of the total vertices and mutations.
     */
    StackView(List<Long> quantities) {
        setWidth(HORIZONTAL_SCALE);
        setHeight(VERTICAL_SCALE);
        setStyle("-fx-fill:" + ColorUtils.webCode(defaultColor));

        Rectangle insertion = new Rectangle();
        insertion.setHeight(VERTICAL_SCALE * quantities.get(1)
                / quantities.get(0));
        insertion.setWidth(HORIZONTAL_SCALE);
        insertion.setStyle("-fx-fill:"
                + ColorUtils.webCode(Mutation.INSERTION.getColor()));

        Rectangle deletion = new Rectangle();
        deletion.setLayoutY(insertion.getHeight());
        deletion.setHeight(VERTICAL_SCALE * quantities.get(2)
                / quantities.get(0));
        deletion.setWidth(HORIZONTAL_SCALE);
        deletion.setStyle("-fx-fill:"
                + ColorUtils.webCode(Mutation.DELETION.getColor()));

        Rectangle polymorphism = new Rectangle();
        polymorphism.setLayoutY(deletion.getHeight());
        polymorphism.setHeight(VERTICAL_SCALE * quantities.get(3)
                / quantities.get(0));
        polymorphism.setWidth(HORIZONTAL_SCALE);
        polymorphism.setStyle("-fx-fill:"
                + ColorUtils.webCode(Mutation.POLYMORPHISM.getColor()));

        Group bars = new Group();
        bars.getChildren().addAll(insertion, deletion, polymorphism);
        setClip(bars);
    }

}
