package nl.tudelft.lifetiles.tree.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import nl.tudelft.lifetiles.sequence.SequenceColor;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;

/**
 * A {@link SunburstRingSegment} represents a segment of the sunburst diagrams
 * rings.
 *
 * @author Albert Smit
 *
 */

public class SunburstRingSegment extends AbstractSunburstNode {
    /**
     * the default color for this segment.
     */
    private static final Color DEFAULT_COLOR = Color.RED;

    /**
     * Creates a SunburstRingSegment.
     *
     *
     * @param value
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
    public SunburstRingSegment(final PhylogeneticTreeItem value, final int layer,
            final double degreeStart, final double degreeEnd,
            final double centerX, final double centerY) {
        // set the value, and create the text and semi-circle
        setValue(value);
        setName(new Text(getValue().getName()));
        setDisplay(createRing(layer, degreeStart, degreeEnd, centerX, centerY));

        // calculate the positon of the text
        double radius = CENTER_RADIUS + (layer * RING_WIDTH) + (RING_WIDTH / 2);

        double degreeCenter = degreeStart
                + AbstractSunburstNode.calculateAngle(degreeStart, degreeEnd) / 2;
        //convert to radians
        double angle = Math.toRadians(degreeCenter);

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
            final double degreeEnd, final double centerX, final double centerY) {
        Path result = new Path();

        result.setFill(createColor());
        result.setFillRule(FillRule.EVEN_ODD);

        // check if this is a large arc
        double arcSize = AbstractSunburstNode.calculateAngle(degreeStart, degreeEnd);
        boolean largeArc = arcSize > (AbstractSunburstNode.CIRCLEDEGREES / 2);

        // calculate the radii of the two arcs
        double innerRadius = CENTER_RADIUS + (layer * RING_WIDTH);
        double outerRadius = innerRadius + RING_WIDTH;

        // convert degrees to radians for Math.sin and Math.cos
        double angleAlpha = Math.toRadians(degreeStart);
        double angleAlphaNext = Math.toRadians(degreeEnd);

        // draw the semi-circle
        // first go to the start point
        double startX = centerX + innerRadius * Math.sin(angleAlpha);
        double startY = centerY - innerRadius * Math.cos(angleAlpha);
        MoveTo move1 = new MoveTo(startX, startY);

        // draw a line from point 1 to point 2
        LineTo line1To2 = createLine(outerRadius, centerX, centerY, angleAlpha);

        // draw an arc from point 2 to point 3
        ArcTo arc2To3 = createArc(outerRadius, centerX, centerY,
                angleAlphaNext, true, largeArc);

        // draw a line from point 3 to point 4
        LineTo line3To4 = createLine(innerRadius, centerX, centerY,
                angleAlphaNext);

        // draw an arc from point 4 back to point 1
        ArcTo arc4To1 = createArc(innerRadius, centerX, centerY, angleAlpha,
                false, largeArc);

        // add all elements to the path
        result.getElements()
                .addAll(move1, line1To2, arc2To3, line3To4, arc4To1);

        return result;
    }

    /**
     * Creates an {@link ArcTo} with the specified parameters.
     *
     * Coordinates of the end point of the arc are given in polar form relative
     * to the center of the arcs.
     *
     * @param radius
     *            The radius of the arc.
     * @param centerX
     *            The center X coordinate of the arc.
     * @param centerY
     *            The center Y coordinate of the arc.
     * @param angle
     *            The angle of the end point.
     * @param sweep
     *            The draw direction of the arc.
     * @param largeArc
     *            if true draw an arc larger than 180 degrees.
     * @return an ArcTo with the specified parameters.
     */
    private ArcTo createArc(final double radius, final double centerX,
            final double centerY, final double angle, final boolean sweep,
            final boolean largeArc) {
        // calculate the end point of the arc
        double endX = centerX + radius * Math.sin(angle);
        double endY = centerY - radius * Math.cos(angle);

        // create the arc
        ArcTo result = new ArcTo();
        result.setRadiusX(radius);
        result.setRadiusY(radius);
        result.setX(endX);
        result.setY(endY);
        result.setSweepFlag(sweep);
        result.setLargeArcFlag(largeArc);

        return result;
    }

    /**
     * Creates a {@link LineTo} with the specified parameters.
     *
     * Coordinates of the end point of the arc are given in polar form relative
     * to the center of the arcs.
     *
     * @param radius
     *            The radius of the arc.
     * @param centerX
     *            The center X coordinate of the arc.
     * @param centerY
     *            The center Y coordinate of the arc.
     * @param angle
     *            The angle of the end point.
     * @return the LineTo with the specified parameters.
     */
    private LineTo createLine(final double radius, final double centerX,
            final double centerY, final double angle) {
        // calculate the end point coordinates
        double endX = centerX + radius * Math.sin(angle);
        double endY = centerY - radius * Math.cos(angle);

        return new LineTo(endX, endY);
    }

    /**
     * Creates a {@link Color} for this node. the color will be red by default,
     * and the color associated with the sequence when the node has a sequence.
     *
     * @return a Color object that specifies what color this node will be.
     */
    private Color createColor() {
        Sequence sequence = getValue().getSequence();
        if (sequence == null) {
            return DEFAULT_COLOR;
        } else {
            return SequenceColor.getColor(sequence);
        }
    }

}
