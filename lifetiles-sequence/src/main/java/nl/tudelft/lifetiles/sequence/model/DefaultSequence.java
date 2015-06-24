package nl.tudelft.lifetiles.sequence.model;

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
    private final String ident;
    /**
     * List of sequence segments related to this sequence.
     */
    private final List<SequenceSegment> sequenceList;

    /**
     * @param identifier
     *            The identifier for this sequence.
     */
    public DefaultSequence(final String identifier) {
        ident = identifier;
        sequenceList = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void appendSegment(final SequenceSegment segment) {
        sequenceList.add(segment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIdentifier() {
        return ident;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SequenceSegment> getSegments() {
        return sequenceList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result;
        if (ident != null) {
            result += ident.hashCode();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object other) {
        if (other instanceof Sequence) {
            return ident.equals(((Sequence) other).getIdentifier());
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[Sequence: " + getIdentifier() + "]";
    }
}
