package nl.tudelft.lifetiles.graph.model.sequence;

/**
 * Interface for the content in a sequence segment.
 *
 * @author Jos
 *
 */
public interface SegmentContent {

    /**
     * @return length of the content in the segment.
     */
    long getLength();

    /**
     * @return string representation of the segment content.
     */
    String toString();

    /**
     * @return whether the segment content is an empty node or not.
     */
    boolean isEmpty();

}
