package nl.tudelft.lifetiles.graph.models.sequence;

/**
 * Segment content with empty content.
 * 
 * @author Jos
 *
 */
public class SegmentEmpty implements SegmentContent {

    private long lengthVar;

    /**
     * Constructs a sequence segment with given string as content.
     * 
     * @param length
     *            Length of the empty content of the segment.
     */
    public SegmentEmpty(long length) {
        lengthVar = length;
    }

    /**
     * @return length of the empty content of the segment.
     */
    @Override
    public long length() {
        return lengthVar;
    }

    /**
     * @return string representation of the empty segment.
     */
    public String toString() {
        return new String(new char[(int) lengthVar]).replace("\0", "_");
    }

}