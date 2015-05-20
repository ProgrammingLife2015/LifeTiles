package nl.tudelft.lifetiles.traverser.models;

import nl.tudelft.lifetiles.graph.models.sequence.Sequence;

/**
 * Factory which produces Traverser implementations based on string and
 * reference input.
 *
 * @author Jos
 *
 */
public class TraverserFactory {

    /**
     * Produces a traverser implementation based on string and reference input.
     *
     * @param traverser
     *            String prefix of the traverser class. Exception is thrown if
     *            no such class can be produced.
     * @param reference
     *            Reference to be used in the traverser production. Exception is
     *            thrown if reference is incorrect on a class which needs a
     *            correct reference.
     * @return produced traverser based on input.
     */
    public final Traverser getTraverser(final String traverser,
            final Sequence reference) {
        if (reference == null) {
            throw new NullPointerException(
                    "Traverser can only be produced using reference.");
        }
        switch (traverser) {
        case "ReferencePosition":
            return new ReferencePositionTraverser(reference);
        case "MutationIndication":
            return new MutationIndicationTraverser(reference);
        default:
            throw new IllegalArgumentException(
                    "Traverser can't be produced in this Factory");
        }
    }

    /**
     * Produces a traverser implementation based on string and reference input.
     *
     * @param traverser
     *            String prefix of the traverser class. Exception is thrown if
     *            no such class can be produced.
     * @return produced traverser based on input.
     */
    public final Traverser getTraverser(final String traverser) {
        switch (traverser) {
        case "EmptySegment":
            return new EmptySegmentTraverser();
        case "UnifiedPosition":
            return new UnifiedPositionTraverser();
        default:
            throw new IllegalArgumentException(
                    "Traverser can't be produced in this Factory");
        }
    }

}
