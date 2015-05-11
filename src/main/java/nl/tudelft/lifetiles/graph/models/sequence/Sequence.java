package nl.tudelft.lifetiles.graph.models.sequence;

import java.util.List;

/**
 * Interface for complete sequences.
 *
 * @author Rutger van den Berg
 */
public interface Sequence {
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

    /**
     * Return the segments related to this sequence.
     *
     * @return A list of sequence segments.
     */
    List<SequenceSegment> getSegments();
}
