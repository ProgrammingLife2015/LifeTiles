package nl.tudelft.lifetiles.tree.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;

/**
 * A sunburstCenter represents the node that is displayed in the center.
 *
 * @author Albert Smit
 *
 */
public class SunburstCenter extends AbstractSunburstNode {

    /**
     * Generates an empty SunburstCenter.
     */
    public SunburstCenter() {
        setDisplay(new Circle(CENTER_RADIUS, Color.BLUE));
        getChildren().add(getDisplay());
    }

    /**
     * Generates a SunburstCenter for the node.
     *
     * @param vertex
     *            the node that this will represent
     */
    public SunburstCenter(final PhylogeneticTreeItem vertex) {
        setValue(vertex);
        setDisplay(new Circle(CENTER_RADIUS, Color.BLUE));
        setName(new Text(getValue().getName()));
        this.getChildren().addAll(getDisplay(), getName());
    }

}
