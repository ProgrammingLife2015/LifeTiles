package nl.tudelft.lifetiles.tree.view;

import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import nl.tudelft.lifetiles.sequence.SequenceColor;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;

/**
 * A sunburstCenter represents the node that is displayed in the center.
 *
 * @author Albert Smit
 *
 */
public class SunburstCenter extends AbstractSunburstNode {

    /**
     * the default color for this segment.
     */
    private static final Color DEFAULT_COLOR = Color.BLUE;

    /**
     * Generates a SunburstCenter for the node.
     *
     * @param value
     *            the node that this will represent
     * @param scale
     *            a scaling factor
     */
    public SunburstCenter(final PhylogeneticTreeItem value, final double scale) {
        setValue(value);
        setDisplay(new Circle(scale * CENTER_RADIUS,  createColor()));
        String name = getValue().getName();
        String tooltip;
        if (name != null) {
            setName(new Tooltip(name));
        }
        this.getChildren().add(getDisplay());
    }

    /**
     * Creates a {@link Color} for this node. the color will be blue by default,
     * and the color associated with the sequence when the node has a sequence.
     *
     * @return a Color object that specifies what color this node will be.
     */
    private Color createColor() {
        Sequence sequence = getValue().getSequence();
        if (sequence == null) {
            return DEFAULT_COLOR;
        } else {
            return SequenceColor.getColor(sequence);
        }
    }
}
