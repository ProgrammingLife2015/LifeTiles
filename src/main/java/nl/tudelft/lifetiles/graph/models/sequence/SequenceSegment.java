package nl.tudelft.lifetiles.graph.models.sequence;

import java.util.Set;

import nl.tudelft.lifetiles.graph.models.sequence.mutation.Mutation;

/**
 * @author Rutger van den Berg Contains a partial sequence.
 */
public class SequenceSegment implements Comparable<SequenceSegment> {
    /**
     * Contains the sources containing this segment.
     */
    private Set<Sequence> sourcesVar;

    /**
     * The start position for this segment.
     */
    private long startVar;
    /**
     * The end position for this segment.
     */
    private long endVar;
    /**
     * The absolute start position for this segment.
     */
    private long absStartVar = 0;
    /**
     * The absolute end position for this segment.
     */
    private long absEndVar = Long.MAX_VALUE;
    /**
     * The content of this segment.
     */
    private SegmentContent contentVar;
    /**
     * The mutation annotation of this segment.
     */
    private Mutation mutationVar;

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
        sourcesVar = sources;
        startVar = startPosition;
        endVar = endPosition;
        contentVar = content;
    }

    /**
     * @return the sources
     */
    public final Set<Sequence> getSources() {
        return sourcesVar;
    }

    /**
     * @return the start position
     */
    public final long getStart() {
        return startVar;
    }

    /**
     * @return the end position
     */
    public final long getEnd() {
        return endVar;
    }

    /**
     * @return the absolute start position
     */
    public final long getAbsStart() {
        return absStartVar;
    }

    /**
     * @param absStart
     *            absolute start position of this sequence segment.
     */
    public final void setAbsStart(long absStart) {
        absStartVar = absStart;
    }

    /**
     * @return the absolute end position
     */
    public final long getAbsEnd() {
        return absEndVar;
    }

    /**
     * @param absEnd
     *            absolute end position of this sequence segment.
     */
    public final void setAbsEnd(long absEnd) {
        absEndVar = absEnd;
    }

    /**
     * @return the content
     */
    public final SegmentContent getContent() {
        return contentVar;
    }

    /**
     * @return mutation annotation of sequence segment.
     */
    public Mutation getMutation() {
        return mutationVar;
    }

    /**
     * @param mutationVar
     *            Mutation which is annotated onto the sequence segment.
     */
    public void setMutation(Mutation mutation) {
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
    public long distanceTo(SequenceSegment other) {
        return other.getStart() - getEnd() - 1;
    }

    /**
     * Compares the the start position of this and another sequence segment.
     * 
     * @param other
     *            Sequence segment which needs to be compared.
     */
    @Override
    public int compareTo(SequenceSegment other) {
        return ((Long) startVar).compareTo((Long) other.getStart());
    }
}
