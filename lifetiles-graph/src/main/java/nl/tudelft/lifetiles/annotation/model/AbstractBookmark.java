package nl.tudelft.lifetiles.annotation.model;

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
     * Returns the position of the bookmark mapped to a sequence segment.
     *
     * @param segment
     *            Sequence segment which bookmark is mapped to.
     * @return position of bookmark on segment.
     */
    protected long segmentPosition(final SequenceSegment segment) {
        return segment.getUnifiedStart() + getGenomePosition()
                - segment.getStart();
    }

//    /**
//     * Abstract method which returns the string representation of the bookmark.
//     * For the bookmark view used to explore the known mutation, genes and
//     * user bookmarks.
//     *
//     * @return cell string representation of the bookmark.
//     */
//    public abstract String toCellString();

    /**
     * Abstract method which return the unified position of the bookmark in the
     * unified graph.
     *
     * @return unified position of the bookmark in the graph.
     */
    public abstract long getUnifiedPosition();
}
