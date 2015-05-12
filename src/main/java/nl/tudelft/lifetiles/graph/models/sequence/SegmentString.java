package nl.tudelft.lifetiles.graph.models.sequence;

/**
 * Segment content with as content a string.
 * 
 * @author Jos
 *
 */
public class SegmentString implements SegmentContent {

    private String contentVar;

    /**
     * Constructs a sequence segment with given string as content.
     * 
     * @param content
     *            + * String to be set as content of the sequence segment.
     */
    public SegmentString(String content) {
        contentVar = content;
    }

    /**
     * @return length of the string content in the segment.
     */
    @Override
    public long getLength() {
        return contentVar.length();
    }

    /**
     * @return string representation of the empty segment.
     */
    public String toString() {
        return contentVar;
    }

}