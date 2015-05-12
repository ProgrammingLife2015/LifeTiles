package nl.tudelft.lifetiles.tree.view;

import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class SunburstCenter extends SunburstUnit {
    
    

    public SunburstCenter() {
        Circle circle = new Circle(50,Color.BLUE);
        getChildren().add(circle);
    }

    public SunburstCenter(PhylogeneticTreeItem v){
        value = v;
        Circle circle = new Circle(50,Color.BLUE);
        Text text = new Text(value.getName());
        this.getChildren().addAll(circle,text);
    }

}
