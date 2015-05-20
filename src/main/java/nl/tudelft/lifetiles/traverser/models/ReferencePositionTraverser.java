package nl.tudelft.lifetiles.traverser.models;

import nl.tudelft.lifetiles.graph.models.Edge;
import nl.tudelft.lifetiles.graph.models.Graph;
import nl.tudelft.lifetiles.graph.models.sequence.SegmentString;
import nl.tudelft.lifetiles.graph.models.sequence.Sequence;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;

/**
 * Traverser which generates for each segment the coordinates in comparison with
 * the reference sequence.
 *
 * @author Jos
 *
 */
public class ReferencePositionTraverser implements Traverser {

    /**
     * Reference sequence which segments are compared to.
     */
    private Sequence referenceVar;

    /**
     * Graph to be traversed.
     */
    private Graph<SequenceSegment> graphVar;

    /**
     * Constructs a ReferencePositionTraverser.
     *
     * @param reference
     *            Reference sequence which segments are compared to.
     */
    public ReferencePositionTraverser(final Sequence reference) {
        referenceVar = reference;
    }

    /**
     * Traverses the graph. Computes the position of the vertices in comparison
     * to the reference sequence. Reference coordinates are needed to indicate
     * mutations.
     *
     * @param graph
     *            Graph to be traversed.
     * @return Traversed graph with reference positions.
     */
    public final Graph<SequenceSegment> traverseGraph(
            final Graph<SequenceSegment> graph) {
        graphVar = graph;
        referenceMapGraph();
        return graphVar;
    }

    /**
     * Traverses the graph. Computes the position of the vertices in comparison
     * to the reference sequence. Reference coordinates are needed to indicate
     * mutations.
     */
    private void referenceMapGraph() {
        referenceMapGraphForward();
        referenceMapGraphBackward();
    }

    /**
     * Traverses the graph forward to generate reference start positions.
     */
    private void referenceMapGraphForward() {
        for (SequenceSegment source : graphVar.getSources()) {
            source.setReferenceStart(1);
            referenceMapVertexForward(source);
        }
    }

    /**
     * Traverses the graph backward to generate reference end position.
     */
    private void referenceMapGraphBackward() {
        for (SequenceSegment sink : graphVar.getSinks()) {
            if (sink.getSources().contains(referenceVar)) {
                sink.setReferenceEnd(sink.getReferenceStart()
                        + sink.getContent().getLength() - 1);
            } else {
                sink.setReferenceEnd(sink.getReferenceStart() - 1);
            }
            referenceMapVertexBackward(sink);
        }
    }

    /**
     * Traverses a vertex forward to generate its reference start position.
     *
     * @param vertex
     *            Vertex to be traversed, calculate reference start position.
     */
    private void referenceMapVertexForward(final SequenceSegment vertex) {
        long position = vertex.getReferenceStart();

        if (vertex.getSources().contains(referenceVar)
                && vertex.getContent() instanceof SegmentString) {
            position += vertex.getContent().getLength();
        }
        for (Edge<SequenceSegment> edge : graphVar.getOutgoing(vertex)) {
            SequenceSegment destination = graphVar.getDestination(edge);
            if (destination.getReferenceStart() < position) {
                destination.setReferenceStart(position);
                referenceMapVertexForward(destination);
            }
        }
    }

    /**
     * Traverses a vertex backward to generate its reference end position.
     *
     * @param vertex
     *            Vertex to be traversed, calculate reference end position.
     */
    private void referenceMapVertexBackward(final SequenceSegment vertex) {
        long position = vertex.getReferenceEnd();

        if (vertex.getSources().contains(referenceVar)
                && vertex.getContent() instanceof SegmentString) {
            position -= vertex.getContent().getLength();
        }
        for (Edge<SequenceSegment> edge : graphVar.getIncoming(vertex)) {
            SequenceSegment source = graphVar.getSource(edge);
            if (source.getReferenceEnd() > position) {
                source.setReferenceEnd(position);
                referenceMapVertexBackward(source);
            }
        }
    }

}
