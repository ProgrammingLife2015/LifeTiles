package nl.tudelft.lifetiles.graph.view;

import nl.tudelft.lifetiles.graph.models.Graph;
import nl.tudelft.lifetiles.graph.models.sequence.Sequence;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;
import nl.tudelft.lifetiles.traverser.models.EmptySegmentTraverser;
import nl.tudelft.lifetiles.traverser.models.MutationIndicationTraverser;
import nl.tudelft.lifetiles.traverser.models.ReferencePositionTraverser;
import nl.tudelft.lifetiles.traverser.models.UnifiedPositionTraverser;

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
     *            - The initial graph
     */
    public Tile(final Graph<SequenceSegment> gr) {
        graph = gr;

        alignGraph();
        findMutations();
    }

    /**
     * Align the graph.
     */
    private void alignGraph() {
        UnifiedPositionTraverser pt = new UnifiedPositionTraverser();
        EmptySegmentTraverser est = new EmptySegmentTraverser();

        graph = est.traverseGraph(pt.traverseGraph(graph));
    }

    /**
     * Find the mutations on the graph.
     */
    private void findMutations() {
        Sequence reference = graph.getSources().iterator().next().getSources()
                .iterator().next();
        ReferencePositionTraverser rmt = new ReferencePositionTraverser(
                reference);
        MutationIndicationTraverser mt = new MutationIndicationTraverser(
                reference);
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
