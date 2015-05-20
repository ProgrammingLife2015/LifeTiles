package nl.tudelft.lifetiles.graph.view;

import nl.tudelft.lifetiles.graph.models.Graph;
import nl.tudelft.lifetiles.graph.models.sequence.Sequence;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;
import nl.tudelft.lifetiles.traverser.models.Traverser;
import nl.tudelft.lifetiles.traverser.models.TraverserFactory;

/**
 * The Tile holds the graph and will be transformed to this modelgraph so
 * that the graph can be drawn on the screen.
 *
 */
public class Tile {

    /**
     * The Current graph that this model is holding.
     */
    private Graph<SequenceSegment> graph;

    /**
     * create a new Tile.
     *
     * @param gr
     *            The initial graph
     */
    public Tile(final Graph<SequenceSegment> gr) {
        graph = gr;

        // TODO: Temporary line until sequence selection is implemented.
        Sequence reference = graph.getSources().iterator().next().getSources()
                .iterator().next();

        TraverserFactory tfact = new TraverserFactory();
        alignGraph(tfact);
        findMutations(tfact, reference);
    }

    /**
     * Align the graph.
     *
     * @param tfact
     *            Traverser factory is being used to produce the traversers.
     */
    private void alignGraph(final TraverserFactory tfact) {
        Traverser upt = tfact.getTraverser("UnifiedPosition");
        Traverser est = tfact.getTraverser("EmptySegment");
        graph = est.traverseGraph(upt.traverseGraph(graph));
    }

    /**
     * Find the mutations on the graph.
     *
     * @param reference
     *            Reference of the graph which is used to indicate mutations.
     * @param tfact
     *            Traverser factory is being used to produce the traversers.
     */
    private void findMutations(final TraverserFactory tfact,
            final Sequence reference) {
        Traverser rmt = tfact.getTraverser("ReferencePosition", reference);
        Traverser mt = tfact.getTraverser("MutationIndication", reference);
        graph = mt.traverseGraph(rmt.traverseGraph(graph));
    }

    /**
     * Get the graph that this model is holding.
     *
     * @return graph
     */
    public final Graph<SequenceSegment> getGraph() {
        return graph;
    }

}
