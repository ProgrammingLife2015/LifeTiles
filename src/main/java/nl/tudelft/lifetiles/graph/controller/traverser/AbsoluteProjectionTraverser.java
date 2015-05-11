package nl.tudelft.lifetiles.graph.controller.traverser;

import nl.tudelft.lifetiles.graph.models.Graph;
import nl.tudelft.lifetiles.graph.models.edge.Edge;
import nl.tudelft.lifetiles.graph.models.sequence.SegmentString;
import nl.tudelft.lifetiles.graph.models.sequence.Sequence;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;

/**
 * Projects sequences in a graph of sequences onto the reference,
 * by generating the absolute ranges of the sequences.
 * 
 * @author Jos
 */
public class AbsoluteProjectionTraverser implements
        GraphTraverser<SequenceSegment> {

    private Sequence reference;

    /**
     * Construct a AbsoluteProjectionTraverser with the given reference.
     * 
     * @param reference
     *            Reference used to keep track of the absolute mapping of the
     *            graph.
     */
    public AbsoluteProjectionTraverser(Sequence reference) {
        this.reference = reference;
    }

    /**
     * Traverse a graph and return the absolute projected graph.
     * 
     * @param graph
     *            The graph that is being projected.
     * @return absolute projected graph.
     */
    @Override
    public Graph<SequenceSegment> traverseGraph(Graph<SequenceSegment> graph) {
        traverseGraphForward(graph);
        traverseGraphBackward(graph);
        return graph;
    }

    /**
     * Traverse a graph forward, generating absolute start positions.
     * 
     * @param graph
     *            The graph that is being projected.
     */
    private void traverseGraphForward(Graph<SequenceSegment> graph) {
        for (SequenceSegment source : graph.getSource()) {
            source.setAbsStart(1);
            traverseVertexForward(graph, source);
        }
    }

    /**
     * Traverse a graph backward, generating absolute end positions.
     * 
     * @param graph
     *            The graph that is being projected.
     */
    private void traverseGraphBackward(Graph<SequenceSegment> graph) {
        for (SequenceSegment sink : graph.getSink()) {
            sink.setAbsEnd(sink.getAbsStart() + sink.getContent().length() - 1);
            traverseVertexBackward(graph, sink);
        }
    }

    /**
     * Traverse a vertex forward in the graph.
     * 
     * @param graph
     *            The graph that can be modified on.
     * @param vertex
     *            The vertex that is being projected.
     */
    private void traverseVertexForward(Graph<SequenceSegment> graph,
            SequenceSegment vertex) {
        long position = vertex.getAbsStart();

        if (vertex.getSources().contains(reference)
                && vertex.getContent() instanceof SegmentString) {
            position += vertex.getContent().length();
        }
        for (Edge<SequenceSegment> edge : graph.getOutgoing(vertex)) {
            SequenceSegment destination = graph.getDestination(edge);
            if (destination.getAbsStart() < position) {
                destination.setAbsStart(position);
                traverseVertexForward(graph, destination);
            }
        }
    }

    /**
     * Traverse a vertex backward in the graph.
     * 
     * @param graph
     *            The graph that can be modified on.
     * @param vertex
     *            The vertex that is being projected.
     */
    private void traverseVertexBackward(Graph<SequenceSegment> graph,
            SequenceSegment vertex) {
        long position = vertex.getAbsEnd();
        if (vertex.getSources().contains(reference)
                && vertex.getContent() instanceof SegmentString) {
            position -= vertex.getContent().length();
        }
        for (Edge<SequenceSegment> edge : graph.getIncoming(vertex)) {
            SequenceSegment destination = graph.getSource(edge);
            if (destination.getAbsEnd() > position) {
                destination.setAbsEnd(position);
                traverseVertexBackward(graph, destination);
            }
        }
    }

}
