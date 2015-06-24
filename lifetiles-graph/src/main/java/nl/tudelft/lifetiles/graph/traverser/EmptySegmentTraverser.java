package nl.tudelft.lifetiles.graph.traverser;

import java.util.HashSet;
import java.util.Set;

import nl.tudelft.lifetiles.core.util.Timer;
import nl.tudelft.lifetiles.graph.model.Edge;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.sequence.model.SegmentEmpty;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

/**
 * Places empty vertices in the graph where there are unified gaps.
 *
 * @author Jos
 *
 */
public final class EmptySegmentTraverser {

    /**
     * Don't instantiate.
     */
    private EmptySegmentTraverser() {

    }

    /**
     * Traverses the graph. Adds empty vertices to the graph which are being
     * used to indicate mutations on.
     *
     * @param graph
     *            the graph to traverse.
     */
    public static void addEmptySegmentsGraph(final Graph<SequenceSegment> graph) {
        Timer timer = Timer.getAndStart();

        for (SequenceSegment vertex : graph.getAllVertices()) {
            addEmptySegmentsVertex(graph, vertex);
        }

        timer.stopAndLog("Adding empty segments");
    }

    /**
     * Traverses a vertex in the graph. Adds empty vertices to the graph which
     * are being used to indicate mutations on.
     *
     * @param graph
     *            The graph to traverse.
     * @param vertex
     *            the vertex to traverse.
     */
    private static void addEmptySegmentsVertex(
            final Graph<SequenceSegment> graph, final SequenceSegment vertex) {
        Set<Sequence> buffer = new HashSet<Sequence>(vertex.getSources());
        for (Edge<SequenceSegment> edge : graph.getOutgoing(vertex)) {
            SequenceSegment destination = graph.getDestination(edge);

            // Need a new set on each iteration, so we do need this.
            @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
            Set<Sequence> sources = new HashSet<Sequence>(
                    destination.getSources());
            sources.retainAll(buffer);
            if (vertex.distanceTo(destination) > 0) {
                graph.splitEdge(edge,
                        bridgeSequence(vertex, destination, sources));
            }
            buffer.removeAll(sources);
        }
    }

    /**
     * Creates a bridge sequence segment between a source and destination
     * segment.
     *
     * @param source
     *            The vertex that is the source segment.
     * @param destination
     *            The vertex that is the destination segment.
     * @param sources
     *            The set of sources in the to be constructed segment.
     * @return sequence segment between source and destination segment
     */
    private static SequenceSegment bridgeSequence(final SequenceSegment source,
            final SequenceSegment destination, final Set<Sequence> sources) {
        SequenceSegment vertex = new SequenceSegment(sources,
                source.getUnifiedEnd(), destination.getUnifiedStart(),
                new SegmentEmpty(source.distanceTo(destination) + 1));
        vertex.setUnifiedStart(vertex.getStart());
        vertex.setUnifiedEnd(vertex.getEnd());
        return vertex;
    }
}
