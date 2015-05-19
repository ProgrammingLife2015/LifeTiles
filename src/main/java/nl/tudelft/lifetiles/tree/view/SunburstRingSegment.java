package nl.tudelft.lifetiles.tree.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;

/**
 * A {@link SunburstRingSegment} represents a segment
 * of the sunburst diagrams rings.
 * @author Albert Smit
 *
 */

public class SunburstRingSegment extends SunburstNode {

    /**
     * Creates a SunburstRingSegment.
     *
     *
     * @param v
     *            the {@link PhylogeneticTreeItem} this part of the ring will
     *            represent
     * @param layer
     *            the layer at which it is located in the tree, layer 0 is the
     *            first layer
     * @param degreeStart
     *            the start position in degrees
     * @param degreeEnd
     *            the end position in degrees
     * @param centerX
     *            the X coordinate of the center of the circle
     * @param centerY
     *            the Y coordinate of the center of the circle
     */
    public SunburstRingSegment(final PhylogeneticTreeItem v, final int layer,
            final double degreeStart, final double degreeEnd,
            final double centerX, final double centerY) {
        // set the value, and create the text and semi-circle
        setValue(v);
        setName(new Text(getValue().getName()));
        setDisplay(createRing(layer, degreeStart, degreeEnd, centerX, centerY));

        // calculate the positon of the text
        double radius = CENTER_RADIUS + (layer * RING_WIDTH) + (RING_WIDTH / 2);

        double degreeCenter = degreeStart
                + SunburstNode.calculateAngle(degreeStart, degreeEnd) / 2;
        //convert to radians
        double angle = degreeCenter * (Math.PI / (CIRCLEDEGREES / 2));

        double pointRingCenterX = centerX + radius * Math.sin(angle);
        double pointRingCenterY = centerY - radius * Math.cos(angle);

        // move the text into position
        getName().relocate(pointRingCenterX, pointRingCenterY);

        // add the text and semicircle to the group
        getChildren().addAll(getDisplay(), getName());
    }

    /**
     * Creates a semi-circle with in the specified location.
     *
     * layer starts at 0
     *
     * @param layer
     *            The layer to place this element at, the first child of root is
     *            layer 0.
     * @param degreeStart
     *            the start position in degrees
     * @param degreeEnd
     *            the end position in degrees
     * @param centerX
     *            the X coordinate of the center of the circle
     * @param centerY
     *            the Y coordinate of the center of the circle
     * @return a semi-circle with the specified dimensions
     */
    private Shape createRing(final int layer, final double degreeStart,
            final double degreeEnd, final double centerX,
            final double centerY) {
        Path result = new Path();

        result.setFill(Color.RED);
        result.setFillRule(FillRule.EVEN_ODD);

        // check if this is a large arc
        double arcSize = SunburstNode.calculateAngle(degreeStart, degreeEnd);
        boolean largeArc = arcSize > (CIRCLEDEGREES / 2);

        // calculate the radii of the two arcs
        double innerRadius = CENTER_RADIUS + (layer * RING_WIDTH);
        double outerRadius = innerRadius + RING_WIDTH;

        // convert degrees to radians for Math.sin and Math.cos
        double angleAlpha = degreeStart * (Math.PI / (CIRCLEDEGREES / 2));
        double angleAlphaNext = degreeEnd * (Math.PI / (CIRCLEDEGREES / 2));

        // calculate the positon of the four corners of the semi-circle
        double point1X = centerX + innerRadius * Math.sin(angleAlpha);
        double point1Y = centerY - innerRadius * Math.cos(angleAlpha);

        double point2X = centerX + outerRadius * Math.sin(angleAlpha);
        double point2Y = centerY - outerRadius * Math.cos(angleAlpha);

        double point3X = centerX + outerRadius * Math.sin(angleAlphaNext);
        double point3Y = centerY - outerRadius * Math.cos(angleAlphaNext);

        double point4X = centerX + innerRadius * Math.sin(angleAlphaNext);
        double point4Y = centerY - innerRadius * Math.cos(angleAlphaNext);

        // draw the semi-circle
        // first go to the start point
        MoveTo move1 = new MoveTo(point1X, point1Y);
        //draw a line from point 1 to point 2
        LineTo line1To2 = new LineTo(point2X, point2Y);

        // draw an arc from point 2 to point 3
        ArcTo arc2To3 = new ArcTo();
        arc2To3.setRadiusX(outerRadius);
        arc2To3.setRadiusY(outerRadius);
        arc2To3.setX(point3X);
        arc2To3.setY(point3Y);
        arc2To3.setSweepFlag(true);
        arc2To3.setLargeArcFlag(largeArc);

        // draw a line from point 3 to point 4
        LineTo line3To4 = new LineTo(point4X, point4Y);

        // draw an arc from point 4 back to point 1
        ArcTo arc4To1 = new ArcTo();
        arc4To1.setRadiusX(innerRadius);
        arc4To1.setRadiusY(innerRadius);
        arc4To1.setX(point1X);
        arc4To1.setY(point1Y);
        arc4To1.setSweepFlag(false);
        arc4To1.setLargeArcFlag(largeArc);

        // add all elements to the path
        result.getElements().add(move1);
        result.getElements().add(line1To2);
        result.getElements().add(arc2To3);
        result.getElements().add(line3To4);
        result.getElements().add(arc4To1);

        return result;
    }

}
