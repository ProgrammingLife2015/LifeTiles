package nl.tudelft.lifetiles.tree.view;

import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 *
 * @author Albert Smit
 *
 */
public class SunburstCenter extends SunburstUnit {


    /**
     * Generates an empty SunburstCenter.
     */
    public SunburstCenter() {
        setDisplay(new Circle(CENTER_RADIUS, Color.BLUE));
        getChildren().add(getDisplay());
    }

    /**
     * Generates a SunburstCenter for the node.
     * @param v the node that this will represent
     */
    public SunburstCenter(final PhylogeneticTreeItem v) {
        setValue(v);
        setDisplay(new Circle(CENTER_RADIUS, Color.BLUE));
        setName(new Text(getValue().getName()));
        this.getChildren().addAll(getDisplay(), getName());
    }

}
