package nl.tudelft.lifetiles.sequence.model;

/**
 * Segment content with empty content.
 *
 * @author Jos
 *
 */
public class SegmentEmpty implements SegmentContent {

    /**
     * length of the segment content.
     */
    private final long length;

    /**
     * Constructs a sequence segment with given string as content.
     *
     * @param length
     *            Length of the empty content of the segment.
     */
    public SegmentEmpty(final long length) {
        this.length = length;
    }

    /**
     * @return length of the empty content of the segment.
     */
    @Override
    public final long getLength() {
        return this.length;
    }

    /**
     * @return string representation of the empty segment.
     */
    @Override
    public final String toString() {
        return new String(new char[(int) this.length]).replace("\0", "_");
    }

    /**
     * @return that the segment content is an empty node.
     */
    @Override
    public final boolean isEmpty() {
        return true;
    }

}
