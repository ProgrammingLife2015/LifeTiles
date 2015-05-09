package nl.tudelft.lifetiles.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rutger van den Berg
 *         Contains an entire sequence.
 */
public class SequenceImplementation implements Sequence {
    /**
     * List of sequence segments related to this sequence.
     */
    private List<SequenceSegment> sequenceList;
    /**
     * Identifier for this sequence.
     */
    private String ident;

    /**
     * @param identifier
     *            The identifier for this sequence.
     */
    public SequenceImplementation(final String identifier) {
        ident = identifier;
        sequenceList = new ArrayList<>();
    }

    @Override
    public final List<SequenceSegment> getSegments() {

        return sequenceList;
    }

    @Override
    public final void appendSegment(final SequenceSegment segment) {
        sequenceList.add(segment);

    }

    @Override
    public final String getIdentifier() {
        return ident;
    }

}
