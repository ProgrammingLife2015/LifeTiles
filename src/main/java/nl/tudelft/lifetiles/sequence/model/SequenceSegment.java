package nl.tudelft.lifetiles.sequence.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ToIntBiFunction;

import nl.tudelft.lifetiles.graph.view.Mutation;

/**
 * @author Rutger van den Berg Contains a partial sequence.
 */
public class SequenceSegment implements Comparable<SequenceSegment> {
    /**
     * Keep track of already used ID's.
     */
    private static AtomicInteger nextId = new AtomicInteger();
    /**
     * Identifier for this segment.
     */
    private final int identifier;
    /**
     * The content of this segment.
     */
    private final SegmentContent content;
    /**
     * Contains the sources containing this segment.
     */
    private final Set<Sequence> sources;

    /**
     * The start position for this segment.
     */
    private final long start;
    /**
     * The end position for this segment.
     */
    private final long endVar;
    /**
     * The unified start position for this segment.
     */
    private long unifiedStartVar = 1;
    /**
     * The unified end position for this segment.
     */
    private long unifiedEndVar = Long.MAX_VALUE;
    /**
     * The start position in comparison with the reference.
     */
    private long referenceStartVar = 1;
    /**
     * The end position in comparison with the reference.
     */
    private long referenceEndVar = Long.MAX_VALUE;

    /**
     * The mutation annotation of this segment.
     */
    private Mutation mutationVar;

    /**
     * Used for compareTo.
     */
    private static final List<ToIntBiFunction<SequenceSegment, SequenceSegment>> COMPARATORS = Arrays
            .asList((left, right) -> {
                return Long.compare(left.getUnifiedStart(),
                        right.getUnifiedStart());
            },
                    (left, right) -> {
                        return Long.compare(left.getStart(), right.getStart());
                    },
                    (left, right) -> {
                        return Long.compare(left.getUnifiedEnd(),
                                right.getUnifiedEnd());
                    },
                    (left, right) -> {
                        return Long.compare(left.getEnd(), right.getEnd());
                    },
                    (left, right) -> {
                        return left.getContent().toString()
                                .compareTo(right.getContent().toString());
                    }, (left, right) -> {
                        return left.getSources().size()
                                - right.getSources().size();
                    });

    /**
     * @param sources
     *            The sources containing this segment.
     * @param startPosition
     *            The start position for this segment.
     * @param endPosition
     *            The end position for this segment.
     * @param content
     *            The content for this segment.
     */
    public SequenceSegment(final Set<Sequence> sources,
            final long startPosition, final long endPosition,
            final SegmentContent content) {
        this.sources = sources;
        this.start = startPosition;
        this.endVar = endPosition;
        this.content = content;
        identifier = nextId.incrementAndGet();
    }

    /**
     * @return the content
     */
    public final SegmentContent getContent() {
        return content;
    }

    /**
     * @return the end position
     */
    public final long getEnd() {
        return endVar;
    }

    /**
     * @return the sources
     */
    public final Set<Sequence> getSources() {
        return sources;
    }

    /**
     * @return the start position
     */
    public final long getStart() {
        return start;
    }

    /**
     * @return the unified start position
     */
    public final long getUnifiedStart() {
        return unifiedStartVar;
    }

    /**
     * @param unifiedStart
     *            unified start position of this sequence segment.
     */
    public final void setUnifiedStart(final long unifiedStart) {
        unifiedStartVar = unifiedStart;
    }

    /**
     * @return the unified end position
     */
    public final long getUnifiedEnd() {
        return unifiedEndVar;
    }

    /**
     * @param unifiedEnd
     *            unified end position of this sequence segment.
     */
    public final void setUnifiedEnd(final long unifiedEnd) {
        unifiedEndVar = unifiedEnd;
    }

    /**
     * @return mutation annotation of sequence segment.
     */
    public final Mutation getMutation() {
        return mutationVar;
    }

    /**
     * @param mutation
     *            Mutation which is annotated onto the sequence segment.
     */
    public final void setMutation(final Mutation mutation) {
        mutationVar = mutation;
    }

    /**
     * Returns the distance between this sequence segment and another.
     * (non-euclidian distance)
     *
     * @param other
     *            Sequence segment which needs to be compared.
     * @return
     *         Distance between this sequence and other sequence.
     */
    public final long distanceTo(final SequenceSegment other) {
        return other.getUnifiedStart() - getUnifiedEnd() - 1;
    }

    /**
     * Compares two segments, first by start positions, then end positions, then
     * content, then sources.
     *
     * @param other
     *            Sequence segment which needs to be compared.
     * @return the compare value of the start positions.
     */
    @Override
    public final int compareTo(final SequenceSegment other) {
        for (ToIntBiFunction<SequenceSegment, SequenceSegment> comp : COMPARATORS) {
            int result = comp.applyAsInt(this, other);
            if (result != 0) {
                return result;
            }
        }
        int candidateComp = 0;
        Iterator<Sequence> thisIt = this.getSources().iterator();
        Iterator<Sequence> otherIt = other.getSources().iterator();
        while (thisIt.hasNext()) {
            candidateComp = thisIt.next().getIdentifier()
                    .compareTo(otherIt.next().getIdentifier());
            if (candidateComp != 0) {
                return candidateComp;
            }
        }
        if (this.getIdentifier() == other.getIdentifier()) {
            candidateComp = 0;
        }
        return candidateComp;
    }

    /**
     * Returns the reference start position.
     *
     * @return reference start position.
     */
    public final long getReferenceStart() {
        return referenceStartVar;
    }

    /**
     * Returns the reference end position.
     *
     * @return reference end position.
     */
    public final long getReferenceEnd() {
        return referenceEndVar;
    }

    /**
     * Sets the reference start position.
     *
     * @param referenceStart
     *            Reference start position.
     */
    public final void setReferenceStart(final long referenceStart) {
        referenceStartVar = referenceStart;
    }

    /**
     * Sets the reference end position.
     *
     * @param referenceEnd
     *            Reference end position.
     */
    public final void setReferenceEnd(final long referenceEnd) {
        referenceEndVar = referenceEnd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + identifier;
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof SequenceSegment)) {
            return false;
        }
        SequenceSegment other = (SequenceSegment) obj;
        return this.getIdentifier() == other.getIdentifier();
    }

    /**
     * @return the identifier
     */
    public final int getIdentifier() {
        return identifier;
    }

    /**
     * Calculates the mutation for this sequence segment given that this segment
     * is not part of the reference sequence.
     *
     * @return calculated mutation type of this segment.
     */
    public final Mutation determineMutation() {
        Mutation mutation;
        if (content.isEmpty()) {
            mutation = Mutation.DELETION;
        } else if (referenceStartVar > referenceEndVar) {
            mutation = Mutation.INSERTION;
        } else {
            mutation = Mutation.POLYMORPHISM;
        }
        return mutation;
    }
}
