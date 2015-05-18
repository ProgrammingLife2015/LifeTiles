package nl.tudelft.lifetiles.traverser.models;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import nl.tudelft.lifetiles.graph.models.Edge;
import nl.tudelft.lifetiles.graph.models.Graph;
import nl.tudelft.lifetiles.graph.models.sequence.SegmentEmpty;
import nl.tudelft.lifetiles.graph.models.sequence.Sequence;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;

/**
 * Places empty vertices in the graph where there are unified gaps.
 *
 * @author Jos
 *
 */
public class EmptySegmentTraverser {
    /**
     * Traverser's copy of the graph.
     */
    private Graph<SequenceSegment> graphVar;

    /**
     * Traverses the copied graph.
     *
     * @param graph
     *            the graph to copy and traverse.
     * @return the traversed copy of the graph.
     */
    public final Graph<SequenceSegment> traverseGraph(
            final Graph<SequenceSegment> graph) {
        graphVar = graph;
        traverseGraph();
        return this.graphVar;
    }

    /**
     * Traverses the copied graph.
     */
    public final void traverseGraph() {
        for (SequenceSegment vertex : new HashSet<SequenceSegment>(
                graphVar.getAllVertices())) {
            traverseVertex(vertex);
        }
    }

    /**
     * Traverses a vertex in the copied graph.
     *
     * @param vertex
     *            the vertex to traverse.
     */
    private void traverseVertex(final SequenceSegment vertex) {
        HashSet<Sequence> buffer = new HashSet<Sequence>(vertex.getSources());
        PriorityQueue<SortedEdge> it = getSortedEdges(graphVar
                .getOutgoing(vertex));
        while (!it.isEmpty()) {
            SortedEdge edge = it.poll();
            SequenceSegment destination = edge.getSegment();
            HashSet<Sequence> sources = new HashSet<Sequence>(
                    destination.getSources());
            sources.retainAll(buffer);
            if (vertex.distanceTo(destination) > 0) {
                graphVar.splitEdge(edge.getEdge(),
                        bridgeSequence(vertex, destination, sources));
            }
            buffer.removeAll(sources);
        }
    }

    /**
     * Temporary method. Obsolete with #56 Internal sorting of edges on
     * destination starting position.
     *
     * @param edges
     *            the edges to sort.
     * @return priority queue of sorted edges.
     */
    @Deprecated
    private PriorityQueue<SortedEdge> getSortedEdges(
            final Set<Edge<SequenceSegment>> edges) {
        PriorityQueue<SortedEdge> it = new PriorityQueue<SortedEdge>();
        for (Edge<SequenceSegment> edge : edges) {
            it.add(new SortedEdge(edge, graphVar.getDestination(edge)));
        }
        return it;
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
    private SequenceSegment bridgeSequence(final SequenceSegment source,
            final SequenceSegment destination, final HashSet<Sequence> sources) {
        SequenceSegment vertex = new SequenceSegment(sources,
                source.getUnifiedEnd(), destination.getUnifiedStart(),
                new SegmentEmpty(source.distanceTo(destination) + 1));
        vertex.setUnifiedStart(vertex.getStart());
        vertex.setUnifiedEnd(vertex.getEnd());
        return vertex;
    }
}

/**
 * Temporary class. Obsolete with #56 Internal sorting of edges on destination
 * starting position.
 *
 * @author Jos
 *
 */
@Deprecated
class SortedEdge implements Comparable<SortedEdge> {

    /**
     * Actual edge in the graph.
     */
    private final Edge<SequenceSegment> edgeVar;
    /**
     * Actual destination of the edge in the graph.
     */
    private final SequenceSegment segmentVar;

    /**
     * Constructs a SortedEdge.
     *
     * @param edge
     *            Actual edge in the graph.
     * @param segment
     *            Actual destination of the edge in the graph.
     */
    @Deprecated
    public SortedEdge(final Edge<SequenceSegment> edge,
            final SequenceSegment segment) {
        edgeVar = edge;
        segmentVar = segment;
    }

    /**
     * Returns the actual edge in the graph.
     *
     * @return actual edge in the graph.
     */
    @Deprecated
    public final Edge<SequenceSegment> getEdge() {
        return edgeVar;
    }

    @Override
    @Deprecated
    public final int compareTo(final SortedEdge other) {
        return -((Long) other.getSegment().getUnifiedStart())
                .compareTo((Long) this.segmentVar.getUnifiedStart());
    }

    /**
     * Return the actual destination of the edge in the graph.
     *
     * @return actual destination of the edge in the graph.
     */
    @Deprecated
    public final SequenceSegment getSegment() {
        return segmentVar;
    }

    /**
     * Returns the string representation of the sources of this segment.
     *
     * @return string representation of the sources of this segment.
     */
    @Deprecated
    public final String toString() {
        return segmentVar.getSources().toString();
    }

}
