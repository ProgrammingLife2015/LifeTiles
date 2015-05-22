package nl.tudelft.lifetiles.traverser.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

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
     * Map which maps sequence segments to unifiedPositions, used to make
     * modifications on during traversal. Inserted into graph at end of
     * traversal to avoid concurrent modification-like exceptions.
     */
    private HashMap<SequenceSegment, Long> unifiedPositions;

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
        unifiedPositions = new HashMap<SequenceSegment, Long>();
        for (SequenceSegment vertex : graphVar.getSources()) {
            unifiedPositions.put(vertex, (long) 1);
            unifyVertex(vertex);
        }
        for (Entry<SequenceSegment, Long> entry : unifiedPositions.entrySet()) {
            SequenceSegment vertex = entry.getKey();
            Long position = entry.getValue();
            vertex.setUnifiedStart(position);
            vertex.setUnifiedEnd(position + vertex.getContent().getLength());
        }
    }

    /**
     * Traverses the vertex, calculates its unified position.
     *
     * @param vertex
     *            Vertex to be traversed.
     */
    private void unifyVertex(final SequenceSegment vertex) {
        long vertexPosition = unifiedPositions.get(vertex)
                + vertex.getContent().getLength();
        for (Edge<SequenceSegment> edge : new ArrayList<Edge<SequenceSegment>>(
                graphVar.getOutgoing(vertex))) {
            SequenceSegment destination = graphVar.getDestination(edge);
            if (!unifiedPositions.containsKey(destination)
                    || unifiedPositions.get(destination) < vertexPosition) {
                unifiedPositions.put(destination, vertexPosition);
                unifyVertex(destination);
            }
        }
    }

}
