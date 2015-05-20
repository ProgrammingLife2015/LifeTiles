package nl.tudelft.lifetiles.graph.models.sequence;

import java.util.Iterator;
import java.util.Set;

import nl.tudelft.lifetiles.graph.view.Mutation;

/**
 * @author Rutger van den Berg Contains a partial sequence.
 */
public class SequenceSegment implements Comparable<SequenceSegment> {
    /**
     * The content of this segment.
     */
    private SegmentContent contentVar;
    /**
     * The end position for this segment.
     */
    private long endVar;
    /**
     * Contains the sources containing this segment.
     */
    private Set<Sequence> sourcesVar;

    /**
     * The start position for this segment.
     */
    private long startVar;

    /**
     * The absolute start position for this segment.
     */
    private long absStartVar = 0;
    /**
     * The absolute end position for this segment.
     */
    private long absEndVar = Long.MAX_VALUE;

    /**
     * The mutation annotation of this segment.
     */
    private Mutation mutationVar;

    /**
     * Distance to shift. Used for hashcode.
     */
    private static final int SHIFTDISTANCE = 32;

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
     * @return the content
     */
    public final SegmentContent getContent() {
        return contentVar;
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
        return sourcesVar;
    }

    /**
     * @return the start position
     */
    public final long getStart() {
        return startVar;
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
    public final void setAbsStart(final long absStart) {
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
    public final void setAbsEnd(final long absEnd) {
        absEndVar = absEnd;
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
        return other.getStart() - getEnd() - 1;
    }

    /**
     * Compares the the start position of this and another sequence segment.
     *
     * @param other
     *            Sequence segment which needs to be compared.
     * @return the compare value of the start positions.
     */
    @Override
    public final int compareTo(final SequenceSegment other) {
        int candidateComp = Long.compare(this.getAbsStart(),
                other.getAbsStart());
        if (candidateComp == 0) {
            candidateComp = Long.compare(this.getStart(), other.getStart());
        }
        if (candidateComp == 0) {
            candidateComp = Long.compare(this.getAbsEnd(), other.getAbsEnd());
        }
        if (candidateComp == 0) {
            candidateComp = Long.compare(this.getEnd(), other.getEnd());
        }
        if (candidateComp == 0) {
            candidateComp = this.getContent().toString()
                    .compareTo(other.getContent().toString());
        }
        if (candidateComp == 0) {
            candidateComp = this.getSources().size()
                    - other.getSources().size();
        }
        if (candidateComp == 0) {
            Iterator<Sequence> thisIt = this.getSources().iterator();
            Iterator<Sequence> otherIt = other.getSources().iterator();
            while (thisIt.hasNext()) {
                if (candidateComp == 0) {
                    candidateComp = thisIt.next().getIdentifier()
                            .compareTo(otherIt.next().getIdentifier());
                }
            }
        }
        return candidateComp;
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
        result += (int) (absEndVar ^ (absEndVar >>> SHIFTDISTANCE));
        result = prime * result
                + (int) (absStartVar ^ (absStartVar >>> SHIFTDISTANCE));
        result = prime * result;
        if (contentVar != null) {
            result += contentVar.hashCode();
        }
        result = prime * result + (int) (endVar ^ (endVar >>> SHIFTDISTANCE));
        result = prime * result;
        if (mutationVar != null) {
            result += mutationVar.hashCode();
        }
        result = prime * result;
        if (sourcesVar != null) {
            result += sourcesVar.hashCode();
        }
        result = prime * result
                + (int) (startVar ^ (startVar >>> SHIFTDISTANCE));
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
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
        if (absEndVar != other.absEndVar) {
            return false;
        }
        if (absStartVar != other.absStartVar) {
            return false;
        }
        if (contentVar == null) {
            if (other.contentVar != null) {
                return false;
            }
        } else if (!contentVar.equals(other.contentVar)) {
            return false;
        }
        if (endVar != other.endVar) {
            return false;
        }
        if (sourcesVar == null) {
            if (other.sourcesVar != null) {
                return false;
            }
        } else if (!sourcesVar.equals(other.sourcesVar)) {
            return false;
        }
        if (startVar != other.startVar) {
            return false;
        }
        return true;
    }
}
