package nl.tudelft.lifetiles.annotation.model;

import java.util.Set;

import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

/**
 * Abstract class for Bookmarks. A bookmark is a point on the genome with
 * some information attached to it.
 *
 * @author Jos
 *
 */
public abstract class AbstractBookmark {

    /**
     * Position of the bookmark on the genome.
     */
    private final long genomePosition;

    /**
     * Constructs a bookmark.
     *
     * @param genomePosition
     *            Position of the annotation on the genome.
     */
    protected AbstractBookmark(final long genomePosition) {
        this.genomePosition = genomePosition;
    }

    /**
     * Returns the position of the annotation on the genome.
     *
     * @return position of the annotation on the genome.
     */
    public final long getGenomePosition() {
        return genomePosition;
    }

    /**
     * Abstract method which maps this annotation to a sequence.
     *
     * @param segments
     *            Segments to map the annotation to.
     * @param reference
     *            The current reference used in the list of segments.
     * @return segment which annotation should be mapped to.
     */
    public abstract SequenceSegment mapOntoSequence(
            final Set<SequenceSegment> segments, final Sequence reference);
}
