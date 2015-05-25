package nl.tudelft.lifetiles.graph.model.sequence;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains an entire sequence.
 *
 * @author Rutger van den Berg
 */
public class DefaultSequence implements Sequence {
    /**
     * Identifier for this sequence.
     */
    private String ident;
    /**
     * List of sequence segments related to this sequence.
     */
    private List<SequenceSegment> sequenceList;

    /**
     * @param identifier
     *            The identifier for this sequence.
     */
    public DefaultSequence(final String identifier) {
        ident = identifier;
        sequenceList = new ArrayList<>();
    }

    @Override
    public final void appendSegment(final SequenceSegment segment) {
        sequenceList.add(segment);
    }

    @Override
    public final String getIdentifier() {
        return ident;
    }

    @Override
    public final List<SequenceSegment> getSegments() {
        return sequenceList;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result;
        if (ident != null) {
            result += ident.hashCode();
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(final Object other) {
        if (other instanceof Sequence) {
            return ident.equals(((Sequence) other).getIdentifier());
        }
        return false;
    }

}
