package nl.tudelft.lifetiles.tree.view;


import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Control;
import javafx.scene.input.MouseButton;
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
     * The center coordinates of this view.
     */
    private Point2D center;
    /**
     * the {@link TreeController} controlling this SunburstView.
     */
    private TreeController controller;
    /**
    * the scaling factor to calculate coordinates, starts at 1.
    */
    private double scale = 1d;
    /**
     * The bounds for this view, used to scale content to fit.
     */
    private Bounds layoutBounds;


    /**
     * Creates a new SunburstView.
     */
    public SunburstView() {
        super();
        center = new Point2D(getWidth() / 2d, getHeight() / 2d);
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
            scale = calculateScale();
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
     * stores a reference to this nodes' parents bounds
     * because the nodes' own bounds are not accurate.
     * @param bounds
     *              The bounds of the parent node
     */
    public final void setBounds(final Bounds bounds) {
        layoutBounds = bounds;
        if (rootItem != null) {
            scale = calculateScale();
            update();
        }
    }

    /**
     * updates the view by redrawing all elements.
     */
    private void update() {
        // remove the old elements
        getChildren().clear();

        center = new Point2D(getWidth() / 2d, getHeight() / 2d);

        // add a center unit
        SunburstCenter center = new SunburstCenter(currentItem, scale);
        center.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                selectNode(currentItem.getParent());
                controller.shoutVisible(currentItem.getChildSequences());
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
     *            SunburstRingSegment will represent.
     * @param layer
     *            the layer on which this SunburstRingSegment is located
     * @param degreeStart
     *            the start point in degrees
     * @param degreeEnd
     *            the end point in degrees
     */
    private void drawRingRecursive(final PhylogeneticTreeItem node,
            final int layer, final double degreeStart, final double degreeEnd) {
        // generate ring
       SunburstRingSegment ringUnit = new SunburstRingSegment(node, layer,
                degreeStart, degreeEnd, center, scale);
        ringUnit.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                selectNode(node);
                controller.shoutVisible(currentItem.getChildSequences());
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

    /**
     * Calculate the scaling factor needed for rendering the full tree
     * in the available space.
     * @return a double between 0 and 1
     */
    private double calculateScale() {
        int depth = currentItem.maxDepth();
        double minSize = Math.min(layoutBounds.getWidth(),
                layoutBounds.getHeight());

        double maxRadius = AbstractSunburstNode.CENTER_RADIUS;
        maxRadius += depth * AbstractSunburstNode.RING_WIDTH;

        double scale = Math.min(1, minSize / (maxRadius * 2));
        return scale;
    }
}
