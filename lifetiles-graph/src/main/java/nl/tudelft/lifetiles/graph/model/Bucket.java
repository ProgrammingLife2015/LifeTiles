package nl.tudelft.lifetiles.graph.model;

import java.util.TreeSet;

import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

/**
 * A bucket containing a subset of the graph.
 *
 * @author Joren Hammudoglu
 *
 */
public final class Bucket extends TreeSet<SequenceSegment> {

    /**
     * Generated serial UID.
     */
    private static final long serialVersionUID = -3948952618147505238L;

    /**
     * The interestingness of this bucket. Sums the interestingness of the
     * containing nodes.
     *
     * @return the interestingness score
     */
    public double interestingness() {
        return parallelStream().mapToDouble(SequenceSegment::interestingness)
                .sum();
    }

}
