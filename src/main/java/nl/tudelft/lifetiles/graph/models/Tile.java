package nl.tudelft.lifetiles.graph.models;

import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;

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
        // AlignmentTraverser at = new AlignmentTraverser();
        // graph = at.traverseGraph(graph);
    }

    /**
     * Find the mutations on the graph.
     */
    private void findMutations() {
        // AlignmentTraverser at = new AlignmentTraverser();
        // graph = at.traverseGraph(graph);
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
