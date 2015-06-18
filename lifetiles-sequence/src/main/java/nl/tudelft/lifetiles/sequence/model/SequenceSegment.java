package nl.tudelft.lifetiles.sequence.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ToIntBiFunction;

import nl.tudelft.lifetiles.sequence.Mutation;

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
    private SegmentContent content;
    /**
     * Contains the sources containing this segment.
     */
    private Set<Sequence> sources;

    /**
     * The start position for this segment.
     */
    private final long start;
    /**
     * The end position for this segment.
     */
    private final long end;
    /**
     * The unified start position for this segment.
     */
    private long unifiedStart = 1;
    /**
     * The unified end position for this segment.
     */
    private long unifiedEnd = Long.MAX_VALUE;
    /**
     * The start position in comparison with the reference.
     */
    private long referenceStart = 1;
    /**
     * The end position in comparison with the reference.
     */
    private long referenceEnd = Long.MAX_VALUE;

    /**
     * The mutation annotation of this segment.
     */
    private Mutation mutation;

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
        this.end = endPosition;
        this.content = content;
        identifier = nextId.incrementAndGet();
    }

    /**
     * Copy constructor.
     *
     * @param original
     *            The SequenceSegment to base the new one on.
     */
    public SequenceSegment(final SequenceSegment original) {
        this.sources = new HashSet<Sequence>(original.getSources());
        this.start = original.getStart();
        this.end = original.getEnd();
        this.content = original.getContent();
        this.identifier = original.getIdentifier();
        this.unifiedEnd = original.getUnifiedEnd();
        this.unifiedStart = original.getUnifiedStart();
        this.mutation = original.getMutation();
        this.referenceStart = original.getReferenceStart();
        this.referenceEnd = original.getReferenceEnd();
    }

    /**
     * Change the content of the segment.
     *
     * @param content
     *            new content.
     */
    public void setContent(final SegmentContent content) {
        this.content = content;
    }

    /**
     * @return the content
     */
    public SegmentContent getContent() {
        return content;
    }

    /**
     * @return the end position
     */
    public long getEnd() {
        return end;
    }

    /**
     * @return the sources
     */
    public Set<Sequence> getSources() {
        return sources;
    }

    /**
     * Change the current sources to the new sources.
     *
     * @param set
     *            new sources
     */
    public void setSources(final Set<Sequence> set) {
        sources = set;
    }

    /**
     * @return the start position
     */
    public long getStart() {
        return start;
    }

    /**
     * @return the unified start position
     */
    public long getUnifiedStart() {
        return unifiedStart;
    }

    /**
     * @param unifiedStart
     *            unified start position of this sequence segment.
     */
    public void setUnifiedStart(final long unifiedStart) {
        this.unifiedStart = unifiedStart;
    }

    /**
     * @return the unified end position
     */
    public long getUnifiedEnd() {
        return unifiedEnd;
    }

    /**
     * @param unifiedEnd
     *            unified end position of this sequence segment.
     */
    public void setUnifiedEnd(final long unifiedEnd) {
        this.unifiedEnd = unifiedEnd;
    }

    /**
     * @return mutation annotation of sequence segment.
     */
    public Mutation getMutation() {
        return mutation;
    }

    /**
     * @param mutation
     *            Mutation which is annotated onto the sequence segment.
     */
    public void setMutation(final Mutation mutation) {
        this.mutation = mutation;
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
    public long distanceTo(final SequenceSegment other) {
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
    public int compareTo(final SequenceSegment other) {
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
    public long getReferenceStart() {
        return referenceStart;
    }

    /**
     * Returns the reference end position.
     *
     * @return reference end position.
     */
    public long getReferenceEnd() {
        return referenceEnd;
    }

    /**
     * Sets the reference start position.
     *
     * @param referenceStart
     *            Reference start position.
     */
    public void setReferenceStart(final long referenceStart) {
        this.referenceStart = referenceStart;
    }

    /**
     * Sets the reference end position.
     *
     * @param referenceEnd
     *            Reference end position.
     */
    public void setReferenceEnd(final long referenceEnd) {
        this.referenceEnd = referenceEnd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + identifier;
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
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
    public int getIdentifier() {
        return identifier;
    }

    /**
     * Calculates the mutation for this sequence segment given that this segment
     * is not part of the reference sequence.
     *
     * @return calculated mutation type of this segment.
     */
    public Mutation determineMutation() {
        Mutation mutation;
        if (content.isEmpty()) {
            mutation = Mutation.DELETION;
        } else if (referenceStart > referenceEnd) {
            mutation = Mutation.INSERTION;
        } else {
            mutation = Mutation.POLYMORPHISM;
        }
        return mutation;
    }

    /**
     * Calculate the contextless interestingness of this sequence. This is
     * effectivly the fraction between length and number of sources, unknown
     * nucleotides weigh less.
     *
     * @return the interestingness score
     */
    public double interestingness() {
        if (content.isEmpty()) {
            return 0;
        }

        final String uninteresting = "N";

        final String rawContent = content.toString();
        final String cleanContent = rawContent.replace(uninteresting, "");

        double contentScore = (rawContent.length() + cleanContent.length()) / 2.0;

        return contentScore / sources.size();
    }
}
