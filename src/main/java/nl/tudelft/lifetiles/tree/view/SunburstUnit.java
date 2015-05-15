package nl.tudelft.lifetiles.tree.view;

import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 *
 * @author Albert Smit
 *
 */
public abstract class SunburstUnit extends Group {

    /**
     * the {@link PhylogeneticTreeItem} this node represents.
     */
    protected PhylogeneticTreeItem value;

    /**
     * A {@link Text} that will show this nodes name.
     */
    protected Text name;

    /**
     * The {@link Shape} that will be shown in the sunburst vew.
     *
     */
    protected Shape display;

    /*
     * Constants
     */

    /**
     * The radius of the circle that will represent the root node.
     */
    protected static final double CENTER_RADIUS = 50.0d;

    /**
     * The width of a single ring.
     */
    protected static final double RING_WIDTH = 25.0d;

    /*
     * Helpers
     */

    /**
     * A helper function to calculate the angle.
     * @param degreeStart the start
     * @param degreeEnd the end
     * @return the angle between the start and end
     */
    protected static double calculateAngle(final double degreeStart,
            final double degreeEnd) {
        double result = 0;

        if (degreeStart > degreeEnd) {
            result = 360 - (degreeStart - degreeEnd);

        } else if (degreeEnd  > degreeStart) {
            result = degreeEnd - degreeStart;
        }

        return result;
    }
}
