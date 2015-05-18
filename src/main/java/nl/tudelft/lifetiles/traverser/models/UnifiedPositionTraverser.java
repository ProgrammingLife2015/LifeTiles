package nl.tudelft.lifetiles.traverser.models;

import nl.tudelft.lifetiles.graph.models.Edge;
import nl.tudelft.lifetiles.graph.models.Graph;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;

/**
 * UnifiedPositionTraverser which generates unified positions for each graph so
 * all vertices independent of it's reference in the original are unified within
 * the same coordinate system.
 *
 * @author Jos
 *
 */
public class UnifiedPositionTraverser {
    /**
     * Copy of the graph which is being traversed.
     */
    private Graph<SequenceSegment> graphVar;

    /**
     * Traverses and copies a graph.
     *
     * @param graph
     *            Graph to be copied and traversed, calculate unified positions.
     * @return traversed copy of the graph.
     */
    public final Graph<SequenceSegment> traverseGraph(
            final Graph<SequenceSegment> graph) {
        this.graphVar = graph.copy();
        traverseGraph();
        return graphVar;
    }

    /**
     * Traverses the graph, calculates unified positions.
     */
    public final void traverseGraph() {
        for (SequenceSegment vertex : graphVar.getSources()) {
            vertex.setUnifiedStart(1);
            vertex.setUnifiedEnd(vertex.getUnifiedStart()
                    + vertex.getContent().getLength());
            traverseVertex(vertex);
        }
    }

    /**
     * Traverses the vertex, calculates its unified position.
     *
     * @param vertex
     *            Vertex to be traversed.
     */
    private void traverseVertex(final SequenceSegment vertex) {
        for (Edge<SequenceSegment> edge : graphVar.getOutgoing(vertex)) {
            SequenceSegment destination = graphVar.getDestination(edge);
            if (destination.getUnifiedStart() < vertex.getUnifiedEnd()) {
                destination.setUnifiedStart(vertex.getUnifiedEnd());
                destination.setUnifiedEnd(destination.getUnifiedStart()
                        + destination.getContent().getLength());
                traverseVertex(destination);
            }
        }
    }

}
