package nl.tudelft.lifetiles.graph.traverser;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import nl.tudelft.lifetiles.core.util.Timer;
import nl.tudelft.lifetiles.graph.model.Edge;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

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
    private Graph<SequenceSegment> graph;

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
        this.graph = graph;
        unifyGraph();
        return graph;
    }

    /**
     * Traverses the graph, calculates unified positions. Unified positions
     * are needed to visualize a comprehensible model of the graph.
     */
    private void unifyGraph() {
        Timer timer = Timer.getAndStart();

        // a queue for breadth-first
        Queue<SequenceSegment> queue = new ArrayDeque<>();
        Map<SequenceSegment, Integer> waiting = new HashMap<>();

        for (SequenceSegment vertex : graph.getSources()) {
            vertex.setUnifiedStart(1);
            vertex.setUnifiedEnd(1 + vertex.getContent().getLength());
            queue.add(vertex);
        }

        while (!queue.isEmpty()) {
            SequenceSegment vertex = queue.poll();

            // Only continue traversal once all incoming paths to this vertex
            // have been traversed.
            final int inLinks = graph.getIncoming(vertex).size();
            if (waiting.containsKey(vertex)) {
                int newVal = waiting.get(vertex) - 1;
                if (newVal < 1) {
                    // not waiting for the vertex anymore, continue traversal
                    waiting.remove(vertex);
                } else {
                    waiting.put(vertex, newVal);
                    continue;
                }
            } else if (inLinks > 1) {
                waiting.put(vertex, inLinks - 1);
                continue;
            }

            final long position = vertex.getUnifiedStart()
                    + vertex.getContent().getLength();
            for (Edge<SequenceSegment> edge : graph.getOutgoing(vertex)) {
                SequenceSegment next = graph.getDestination(edge);
                if (next.getUnifiedStart() < position) {
                    next.setUnifiedStart(position);
                    next.setUnifiedEnd(position + next.getContent().getLength());
                }
                queue.add(next);
            }
        }

        timer.stopAndLog("Graph unification");
    }

}
