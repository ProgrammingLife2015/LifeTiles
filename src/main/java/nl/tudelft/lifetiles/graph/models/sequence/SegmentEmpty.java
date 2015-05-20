package nl.tudelft.lifetiles.graph.models.sequence;

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
    private long lengthVar;

    /**
     * Constructs a sequence segment with given string as content.
     *
     * @param length
     *            Length of the empty content of the segment.
     */
    public SegmentEmpty(final long length) {
        lengthVar = length;
    }

    /**
     * @return length of the empty content of the segment.
     */
    @Override
    public final long getLength() {
        return lengthVar;
    }

    /**
     * @return string representation of the empty segment.
     */
    public final String toString() {
        return new String(new char[(int) lengthVar]).replace("\0", "_");
    }

    /**
     * @return that the segment content is an empty node.
     */
    @Override
    public final boolean isEmpty() {
        return true;
    }

}
