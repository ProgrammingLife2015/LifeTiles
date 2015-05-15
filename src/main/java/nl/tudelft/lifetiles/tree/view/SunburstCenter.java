package nl.tudelft.lifetiles.tree.view;

import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
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
        display = new Circle(CENTER_RADIUS,Color.BLUE);
        getChildren().add(display);
    }

    /**
     * Generates a SunburstCenter for the node.
     * @param v the node that this will represent
     */
    public SunburstCenter(PhylogeneticTreeItem v){
        value = v;
        display = new Circle(CENTER_RADIUS, Color.BLUE);
        name = new Text(value.getName());
        this.getChildren().addAll(display, name);
    }

}
