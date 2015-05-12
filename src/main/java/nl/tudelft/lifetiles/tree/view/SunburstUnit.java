package nl.tudelft.lifetiles.tree.view;

import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public abstract class SunburstUnit extends StackPane {

    protected PhylogeneticTreeItem value;
    
    protected Text name;
    
    protected Shape display;
    
    /*
     * Constants
     */
    
    protected static final double CENTER_DIAMETER = 50.0d;

    protected static final double RING_WIDTH = 25.0d;

}
