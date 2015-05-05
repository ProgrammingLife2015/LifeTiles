package nl.tudelft.lifetiles.graph.sequence;

import java.util.Set;

/**
 * @author Rutger van den Berg Contains a partial sequence.
 */
public class SequenceSegment {
    /**
     * Contains the sources containing this segment.
     */
    private Set<String> sourcesVar;

    /**
     * The start position for this segment.
     */
    private long startVar;
    /**
     * The end position for this segment.
     */
    private long endVar;
    /**
     * The content of this segment.
     */
    private SegmentContent contentVar;

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
    public SequenceSegment(final Set<String> sources, final long startPosition,
            final long endPosition, final SegmentContent content) {
        sourcesVar = sources;
        startVar = startPosition;
        endVar = endPosition;
        contentVar = content;
    }
    
    /**
     * Returns the distance between this sequence segment and another.
     * (non-euclidian distance)
     * @param other
     *            Sequence segment which needs to be compared.
     * @return
     *            Distance between this sequence and other sequence.
     */
    public long distanceTo(SequenceSegment other) {
		return other.getStart() - getEnd() - 1;
    }

    /**
     * @return the sources
     */
    public final Set<String> getSources() {
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
     * @return the content
     */
    public final SegmentContent getContent() {
        return contentVar;
    }
}
