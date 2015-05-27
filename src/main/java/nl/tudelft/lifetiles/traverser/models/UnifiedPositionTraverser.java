package nl.tudelft.lifetiles.traverser.models;

import java.util.ArrayList;
import java.util.Calendar;

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
     * Graph which is being traversed.
     */
    private Graph<SequenceSegment> graphVar;

    /**
     * Traverses the graph, calculates the unified position. Unified positions
     * are needed to visualize a comprehensible model of the graph.
     *
     * @param graph
     *            Graph to be traversed, calculate unified positions.
     * @return traversed graph.
     */
    public final Graph<SequenceSegment> unifyGraph(
            final Graph<SequenceSegment> graph) {
        this.graphVar = graph;
        unifyGraph();
        return graphVar;
    }

    /**
     * Traverses the graph, calculates unified positions. Unified positions
     * are needed to visualize a comprehensible model of the graph.
     */
    private void unifyGraph() {
        long startTime = Calendar.getInstance().getTimeInMillis();
        for (SequenceSegment vertex : graphVar.getSources()) {
            vertex.setUnifiedStart(1);
            vertex.setUnifiedEnd(1 + vertex.getContent().getLength());
            unifyVertex(vertex);
        }
        System.out.println("Graph unified. Took "
                + (Calendar.getInstance().getTimeInMillis() - startTime)
                + " ms.");
    }

    /**
     * Traverses the vertex, calculates its unified position.
     *
     * @param vertex
     *            Vertex to be traversed.
     */
    private void unifyVertex(final SequenceSegment vertex) {
        long vertexPosition = vertex.getUnifiedStart()
                + vertex.getContent().getLength();
        for (Edge<SequenceSegment> edge : new ArrayList<Edge<SequenceSegment>>(
                graphVar.getOutgoing(vertex))) {
            SequenceSegment destination = graphVar.getDestination(edge);
            if (destination.getUnifiedStart() < vertexPosition) {
                destination.setUnifiedStart(vertexPosition);
                destination.setUnifiedEnd(vertexPosition
                        + destination.getContent().getLength());
                unifyVertex(destination);
            }
        }
    }

}
