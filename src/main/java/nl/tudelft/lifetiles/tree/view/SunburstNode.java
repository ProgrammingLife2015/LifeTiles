package nl.tudelft.lifetiles.tree.view;

import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;
import javafx.scene.Group;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 *
 * @author Albert Smit
 *
 */
public abstract class SunburstNode extends Group {

    /**
     * the {@link PhylogeneticTreeItem} this node represents.
     */
    private PhylogeneticTreeItem value;

    /**
     * A {@link Text} that will show this nodes name.
     */
    private Text name;

    /**
     * The {@link Shape} that will be shown in the sunburst vew.
     *
     */
    private Shape display;

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

    /**
     * the number of degrees in a circle.
     */
    protected static final double CIRCLEDEGREES = 360.0d;


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
            result = CIRCLEDEGREES - (degreeStart - degreeEnd);

        } else if (degreeEnd  > degreeStart) {
            result = degreeEnd - degreeStart;
        }

        return result;
    }

    /*
     * Getters and setters.
     */

    /**
     * @return the display
     */
    protected final Shape getDisplay() {
        return display;
    }

    /**
     * @param disp the display to set
     */
    protected final void setDisplay(final Shape disp) {
        this.display = disp;
    }

    /**
     * @return the value
     */
    protected final PhylogeneticTreeItem getValue() {
        return value;
    }

    /**
     * @param val the value to set
     */
    protected final void setValue(final PhylogeneticTreeItem val) {
        this.value = val;
    }

    /**
     * @return the name
     */
    protected final Text getName() {
        return name;
    }

    /**
     * @param nameText the name to set
     */
    protected final void setName(final Text nameText) {
        this.name = nameText;
    }
}
