package nl.tudelft.lifetiles.tree.view;

import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SunburstView extends Control {

    PhylogeneticTreeItem rootItem;
    PhylogeneticTreeItem currentItem;
    HBox container;

    private double factor;
    private double centerX;
    private double centerY;

    public SunburstView() {
        super();
        centerX = getWidth() / 2d;
        centerY = getHeight() / 2d;
    }

    public SunburstView(PhylogeneticTreeItem root) {
        super();

        centerX = getWidth() / 2d;
        centerY = getHeight() / 2d;
        rootItem = root;
        selectNode(rootItem);

    }

    public void selectNode(PhylogeneticTreeItem selected) {
        currentItem = selected;
        update();
    }

    private void update() {
        // remove the old elements
        getChildren().clear();

        //add a center unit
        SunburstCenter center = new SunburstCenter(currentItem);
        getChildren().add(center);
        
        // add the ring units 
        double totalDescendants = currentItem.numberDescendants();
        double degreeStart = 0;
        for (PhylogeneticTreeItem child : currentItem.getChildren()) {
            double sectorSize = (child.numberDescendants() + 1) / totalDescendants;
            double degreeEnd = degreeStart + (360 * sectorSize);

            drawRingRecursive(child, 0, degreeStart, degreeEnd);
            degreeStart = degreeEnd;
        }
    }

    private void drawRingRecursive(PhylogeneticTreeItem node, int layer,
            double degreeStart, double degreeEnd) {
        // generate ring
        SunburstRing ringUnit = new SunburstRing(node, layer, degreeStart,
                degreeEnd, centerX, centerY);
        getChildren().add(ringUnit);

        double totalDescendants = currentItem.numberDescendants();
        double start = degreeStart;
        double sectorAngle = SunburstUnit
                .calculateAngle(degreeStart, degreeEnd);
        
        // generate rings for child nodes
        for (PhylogeneticTreeItem child : node.getChildren()) {
            double sectorSize = (child.numberDescendants() + 1) / totalDescendants;
            double end = start + (sectorAngle * sectorSize);

            drawRingRecursive(child, layer + 1, start, end);
            start = end;
        }

    }

}
