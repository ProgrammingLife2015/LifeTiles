package nl.tudelft.lifetiles.graph.models.sequence;

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
    long length();

    /**
     * @return string representation of the segment content.
     */
    String toString();

}