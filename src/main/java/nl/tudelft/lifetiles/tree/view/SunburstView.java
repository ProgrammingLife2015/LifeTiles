package nl.tudelft.lifetiles.tree.view;

import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SunburstView extends Control{

    PhylogeneticTreeItem rootItem;
    PhylogeneticTreeItem currentItem;
    
    private double factor;
    private double centerX;
    private double centerY;
    
    public SunburstView() {
        super();
        centerY = getHeight() / 2;
        centerX = getWidth() / 2 ;
        PhylogeneticTreeItem root = new PhylogeneticTreeItem();
        root.setName("Root");
        SunburstUnit center = new SunburstCenter(root);
        //center.relocate(centerX, centerY);
        getChildren().add(center);
    }

}
