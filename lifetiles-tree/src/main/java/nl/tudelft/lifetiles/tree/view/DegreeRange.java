package nl.tudelft.lifetiles.tree.view;

/**
 *
 * @author Albert Smit
 *
 */
public class DegreeRange {

    /**
     * The starting angle in degrees.
     */
    private double startAngle;

    /**
     * The ending angle in degrees.
     */
    private double endAngle;

    /**
     * Constructor for degree range, creates a new immutable DegreeRange.
     * @param start the start of the range
     * @param end the end of the range
     */
    public DegreeRange(final double start, final double end) {
        startAngle = start;
        endAngle = end;

    }

    /**
     * @return the starting angle.
     */
    public double getStartAngle() {
        return startAngle;
    }

    /**
     * @return the ending angle.
     */
    public double getEndAngle() {
        return endAngle;
    }

    /**
     * Calculates the angle between the start and end, is calculated clockwise.
     * @return the size of the angle covered by this range
     */
    public double angle() {
        double result = 0;

        if (startAngle > endAngle) {
            result = AbstractSunburstNode.CIRCLEDEGREES - (startAngle - endAngle);

        } else if (endAngle  > startAngle) {
            result = endAngle - startAngle;
        }

        return result;
    }
}
