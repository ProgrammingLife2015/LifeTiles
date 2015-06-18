package nl.tudelft.lifetiles.sequence.model;

/**
 * Collapsed SegmentString content.
 * Used to collapse total segments in the graph.
 *
 * @author Jos
 *
 */
public class SegmentStringCollapsed implements SegmentContent {

    /**
     * Thousand, used to indicate number of kilo bases.
     */
    private static final long THOUSAND = 1000;

    /**
     * The actual content of the collapsed segment string.
     */
    private final SegmentContent content;

    /**
     * The length of the collapsed segment string.
     */
    private final long length;

    /**
     * Constructs a collapsed segment string.
     *
     * @param content
     *            The actual content of the collapsed segment string.
     */
    public SegmentStringCollapsed(final SegmentContent content) {
        this.content = content;
        this.length = (long) Math.ceil(Math.log(content.getLength())
                / Math.log(2));
    }

    /**
     * @return length of the content of the segment.
     */
    @Override
    public long getLength() {
        return length;
    }

    /**
     * @return string representation of the collapsed segment.
     */
    @Override
    public String toString() {
        return "<" + formatString(content.getLength()) + ">";
    }

    /**
     * Formats the string to a base length.
     *
     * @param length
     *            Length of the content of the segment.
     * @return formatted length of the content of the segment.
     */
    private String formatString(final long length) {
        if (length > THOUSAND) {
            return (long) Math.ceil(length / THOUSAND) + "kb";
        }
        return length + "b";
    }

    /**
     * @return that the segment content is an empty node.
     */
    @Override
    public boolean isEmpty() {
        return content.isEmpty();
    }

}
