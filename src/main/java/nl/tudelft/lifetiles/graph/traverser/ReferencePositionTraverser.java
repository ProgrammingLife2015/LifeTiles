package nl.tudelft.lifetiles.graph.traverser;

import java.util.Calendar;

import nl.tudelft.lifetiles.graph.model.Edge;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.sequence.model.SegmentString;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

/**
 * Traverser which generates for each segment the coordinates in comparison with
 * the reference sequence.
 *
 * @author Jos
 *
 */
public class ReferencePositionTraverser {

    /**
     * Reference sequence which segments are compared to.
     */
    private final Sequence reference;

    /**
     * Constructs a ReferencePositionTraverser.
     *
     * @param reference
     *            Reference sequence which segments are compared to.
     */
    public ReferencePositionTraverser(final Sequence reference) {
        this.reference = reference;
    }

    /**
     * Traverses the graph. Computes the position of the vertices in comparison
     * to the reference sequence. Reference coordinates are needed to indicate
     * mutations.
     *
     * @param graph
     *            Graph to be traversed.
     */
    public final void referenceMapGraph(final Graph<SequenceSegment> graph) {
        long startTime = Calendar.getInstance().getTimeInMillis();
        referenceMapGraphForward(graph);
        referenceMapGraphBackward(graph);
        System.out.println("Mapped graph onto reference. Took "
                + (Calendar.getInstance().getTimeInMillis() - startTime)
                + " ms.");
    }

    /**
     * Traverses the graph forward to generate reference start positions.
     *
     * @param graph
     *            Graph to be traversed.
     */
    private void referenceMapGraphForward(final Graph<SequenceSegment> graph) {
        for (SequenceSegment source : graph.getSources()) {
            source.setReferenceStart(1);
            referenceMapVertexForward(source, graph);
        }
    }

    /**
     * Traverses the graph backward to generate reference end position.
     *
     * @param graph
     *            Graph to be traversed.
     */
    private void referenceMapGraphBackward(final Graph<SequenceSegment> graph) {
        for (SequenceSegment sink : graph.getSinks()) {
            if (sink.getSources().contains(reference)) {
                sink.setReferenceEnd(sink.getReferenceStart()
                        + sink.getContent().getLength() - 1);
            } else {
                sink.setReferenceEnd(sink.getReferenceStart() - 1);
            }
            referenceMapVertexBackward(sink, graph);
        }
    }

    /**
     * Traverses a vertex forward to generate its reference start position.
     *
     * @param vertex
     *            Vertex to be traversed, calculate reference start position.
     * @param graph
     *            Graph to be traversed.
     */
    private void referenceMapVertexForward(final SequenceSegment vertex,
            final Graph<SequenceSegment> graph) {
        long position = vertex.getReferenceStart();

        if (vertex.getSources().contains(reference)
                && vertex.getContent() instanceof SegmentString) {
            position += vertex.getContent().getLength();
        }
        for (Edge<SequenceSegment> edge : graph.getOutgoing(vertex)) {
            SequenceSegment destination = graph.getDestination(edge);
            if (destination.getReferenceStart() < position) {
                destination.setReferenceStart(position);
                referenceMapVertexForward(destination, graph);
            }
        }
    }

    /**
     * Traverses a vertex backward to generate its reference end position.
     *
     * @param vertex
     *            Vertex to be traversed, calculate reference end position.
     * @param graph
     *            Graph to be traversed.
     */
    private void referenceMapVertexBackward(final SequenceSegment vertex,
            final Graph<SequenceSegment> graph) {
        long position = vertex.getReferenceEnd();

        if (vertex.getSources().contains(reference)
                && vertex.getContent() instanceof SegmentString) {
            position -= vertex.getContent().getLength();
        }
        for (Edge<SequenceSegment> edge : graph.getIncoming(vertex)) {
            SequenceSegment source = graph.getSource(edge);
            if (source.getReferenceEnd() > position) {
                source.setReferenceEnd(position);
                referenceMapVertexBackward(source, graph);
            }
        }
    }

}
