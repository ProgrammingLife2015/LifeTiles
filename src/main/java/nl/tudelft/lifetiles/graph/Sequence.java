package nl.tudelft.lifetiles.graph;

import java.util.List;

/**
 * @author Rutger van den Berg
 *         Interface for complete sequences.
 */
public interface Sequence {
    /**
     * Return the segments related to this sequence.
     *
     * @return A list of sequence segments.
     */
    List<SequenceSegment> getSegments();

    /**
     * Add a segment to the sequence.
     *
     * @param segment
     *            The segment to append.
     */
    void appendSegment(SequenceSegment segment);

    /**
     * @return The identifier for this sequence.
     */
    String getIdentifier();
}
