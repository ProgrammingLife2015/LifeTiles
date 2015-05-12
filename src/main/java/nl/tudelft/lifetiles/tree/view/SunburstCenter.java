package nl.tudelft.lifetiles.tree.view;

import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class SunburstCenter extends SunburstUnit {
    
    

    public SunburstCenter() {
        display = new Circle(CENTER_DIAMETER,Color.BLUE);
        getChildren().add(display);
    }

    public SunburstCenter(PhylogeneticTreeItem v){
        value = v;
        display = new Circle(CENTER_DIAMETER, Color.BLUE);
        name = new Text(value.getName());
        this.getChildren().addAll(display, name);
    }

}
