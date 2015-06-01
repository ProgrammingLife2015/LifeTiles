package nl.tudelft.lifetiles.tree.view;

import java.util.Set;

import javafx.scene.control.Control;
import javafx.scene.input.MouseButton;
import nl.tudelft.lifetiles.core.util.Message;
import nl.tudelft.lifetiles.tree.controller.TreeController;
import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;

/**
 * A View to display a tree.
 * The tree will be displayed in a circle.
 *
 * @author Albert Smit
 *
 */
public class SunburstView extends Control {

    /**
     * The root of the tree this view will show.
     */
    private PhylogeneticTreeItem rootItem;
    /**
     * the current node we use as the center of the view.
     */
    private PhylogeneticTreeItem currentItem;

    /**
     * the center X coordinate of the view.
     */
    private final double centerX;
    /**
     * the center Y coordinate of the view.
     */
    private final double centerY;
    /**
     * the {@link TreeController} controlling this SunburstView.
     */
    private TreeController controller;

    /**
     * Creates a new SunburstView.
     */
    public SunburstView() {
        super();
        centerX = getWidth() / 2d;
        centerY = getHeight() / 2d;
    }

    /**
     * Creates a new SunburstView.
     *
     * @param root
     *            the root of the tree to display
     */
    public SunburstView(final PhylogeneticTreeItem root) {
        super();

        centerX = getWidth() / 2d;
        centerY = getHeight() / 2d;
        rootItem = root;
        selectNode(rootItem);

    }

    /**
     * changes the currently selected node.
     *
     * @param selected
     *            the new selected node.
     */
    public final void selectNode(final PhylogeneticTreeItem selected) {
        if (selected != null) {
            currentItem = selected;
            update();
        }

    }

    /**
     * Changes the displayed tree.
     *
     * @param root
     *            the new root
     */
    public final void setRoot(final PhylogeneticTreeItem root) {
        rootItem = root;
        selectNode(rootItem);
    }

    /**
     * @param controller
     *            the {@link TreeController} controlling this tree
     */
    public final void setController(final TreeController controller) {
        this.controller = controller;
    }

    /**
     * updates the view by redrawing all elements.
     */
    private void update() {
        // remove the old elements
        getChildren().clear();

        // add a center unit
        SunburstCenter center = new SunburstCenter(currentItem);
        center.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                selectNode(currentItem.getParent());
                controller.shout(Message.FILTERED, currentItem.getChildSequences());
           }
        });
        getChildren().add(center);

        // add the ring units
        double totalDescendants = currentItem.numberDescendants();
        double degreeStart = 0;
        for (PhylogeneticTreeItem child : currentItem.getChildren()) {
            double sectorSize = (child.numberDescendants() + 1)
                    / totalDescendants;
            double degreeEnd = degreeStart
                    + (AbstractSunburstNode.CIRCLEDEGREES * sectorSize);

            drawRingRecursive(child, 0, degreeStart, degreeEnd);
            degreeStart = degreeEnd;
        }
    }

    /**
     * draws all ringUnits.
     *
     * @param node
     *            the {@link PhylogeneticTreeItem} that this
     *            {@link SunburstRingSegment} will represent.
     * @param layer
     *            the layer on which this {@link SunburstRingSegment} is located
     * @param degreeStart
     *            the start point in degrees
     * @param degreeEnd
     *            the end point in degrees
     */
    private void drawRingRecursive(final PhylogeneticTreeItem node,
            final int layer, final double degreeStart, final double degreeEnd) {
        // generate ring
        SunburstRingSegment ringUnit = new SunburstRingSegment(node, layer,
                degreeStart, degreeEnd, centerX, centerY);
        ringUnit.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                selectNode(node);
                controller.shout(Message.FILTERED, currentItem.getChildSequences());
            }
        });
        getChildren().add(ringUnit);

        double totalDescendants = node.numberDescendants();
        double start = degreeStart;
        double sectorAngle = AbstractSunburstNode.calculateAngle(degreeStart,
                degreeEnd);

        // generate rings for child nodes
        for (PhylogeneticTreeItem child : node.getChildren()) {
            double sectorSize = (child.numberDescendants() + 1)
                    / totalDescendants;
            double end = start + (sectorAngle * sectorSize);

            drawRingRecursive(child, layer + 1, start, end);
            start = end;
        }

    }

}
