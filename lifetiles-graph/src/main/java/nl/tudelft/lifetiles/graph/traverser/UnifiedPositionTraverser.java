package nl.tudelft.lifetiles.graph.traverser;

import java.util.Iterator;

import nl.tudelft.lifetiles.core.util.IteratorUtils;
import nl.tudelft.lifetiles.core.util.Timer;
import nl.tudelft.lifetiles.graph.model.BreadthFirstIterator;
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
public final class UnifiedPositionTraverser {

    /**
     * Don't instantiate.
     */
    private UnifiedPositionTraverser() {

    }

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
     */
    public static void unifyGraph(final Graph<SequenceSegment> graph) {
        Timer timer = Timer.getAndStart();

        for (SequenceSegment vertex : graph.getSources()) {
            vertex.setUnifiedStart(1);
            vertex.setUnifiedEnd(1 + vertex.getContent().getLength());
        }

        Iterator<SequenceSegment> iterator = new BreadthFirstIterator<>(graph);
        for (SequenceSegment vertex : IteratorUtils.toIterable(iterator)) {
            long position = vertex.getUnifiedStart()
                    + vertex.getContent().getLength();
            for (Edge<SequenceSegment> edge : graph.getOutgoing(vertex)) {
                SequenceSegment next = graph.getDestination(edge);
                if (next.getUnifiedStart() < position) {
                    next.setUnifiedStart(position);
                    next.setUnifiedEnd(position + next.getContent().getLength());
                }
            }
        }

        timer.stopAndLog("Graph unification");
    }

}
