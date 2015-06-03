package nl.tudelft.lifetiles.annotation.model;

/**
 * Abstract class for a domain annotation.
 * Has a start and end coordinate to the reference sequence.
 *
 * @author Jos
 *
 */
public abstract class AbstractDomainAnnotation {

    /**
     * Start coordinate of the annotation domain.
     */
    private final long start;
    /**
     * End coordinate of the annotation domain.
     */
    private final long end;

    /**
     * Construct a domain annotation.
     * 
     * @param start
     *            Start coordinate for the annotation domain.
     * @param end
     *            End coordinate for the annotation domain.
     */
    public AbstractDomainAnnotation(final long start, final long end) {
        this.start = start;
        this.end = end;
    }

    /**
     * @return the start
     */
    public long getStart() {
        return start;
    }

    /**
     * @return the end
     */
    public long getEnd() {
        return end;
    }
}
