package nl.tudelft.lifetiles.sequence.model;

/**
 * Segment content with as content a string.
 *
 * @author Jos
 *
 */
public class SegmentString implements SegmentContent {

    /**
     * content of the segment content.
     */
    private final String content;

    /**
     * Constructs a sequence segment with given string as content.
     *
     * @param content
     *            + * String to be set as content of the sequence segment.
     */
    public SegmentString(final String content) {
        this.content = content;
    }

    /**
     * @return length of the string content in the segment.
     */
    @Override
    public long getLength() {
        return this.content.length();
    }

    /**
     * @return string representation of the empty segment.
     */
    @Override
    public String toString() {
        return this.content;
    }

    /**
     * @return that the segment content is not an empty node.
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * @return whether the segment has been collapsed.
     */
    @Override
    public boolean isCollapsed() {
        return false;
    }

}
