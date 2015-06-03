package nl.tudelft.lifetiles.graph.traverser;

import java.util.Iterator;

import nl.tudelft.lifetiles.core.util.IterUtils;
import nl.tudelft.lifetiles.core.util.Timer;
import nl.tudelft.lifetiles.graph.model.BreadthFirstIterator;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

/**
 * Traverser which generates for each segment the coordinates in comparison with
 * the reference sequence.
 *
 * @author Jos
 * @author Joren Hammudoglu
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
     * Traverses the graph. Computes the position of the vertices in
     * comparison
     * to the reference sequence. Reference coordinates are needed to indicate
     * mutations.
     *
     * @param graph
     *            Graph to be traversed.
     */
    public final void referenceMapGraph(final Graph<SequenceSegment> graph) {
        Timer timer = Timer.getAndStart();

        referenceMapGraphForward(graph);
        referenceMapGraphBackward(graph);

        timer.stopAndLog("Mapping graph onto reference");
    }

    /**
     * Traverses the graph forward to generate reference start
     * positions.
     *
     * @param graph
     *            Graph to be traversed.
     */
    private void referenceMapGraphForward(final Graph<SequenceSegment> graph) {
        for (SequenceSegment source : graph.getSources()) {
            source.setReferenceStart(1);
        }

        Iterator<SequenceSegment> iterator = new BreadthFirstIterator<>(graph);
        long position = 1;
        for (SequenceSegment vertex : IterUtils.toIterable(iterator)) {
            // update start position
            if (vertex.getReferenceStart() < position) {
                vertex.setReferenceStart(position);
            }

            // update current position
            position = vertex.getReferenceStart();
            if (vertex.getSources().contains(reference)
                    && !vertex.getContent().isEmpty()) {
                position += vertex.getContent().getLength();
            }
        }
    }

    /**
     * Traverses the graph backward to generate reference end position.
     *
     * @param graph
     *            Graph to be traversed.
     */
    private void referenceMapGraphBackward(final Graph<SequenceSegment> graph) {
        long referenceEnd = Long.MAX_VALUE;
        for (SequenceSegment sink : graph.getSinks()) {
            referenceEnd = sink.getReferenceStart() - 1;
            if (sink.getSources().contains(reference)) {
                referenceEnd += sink.getContent().getLength();
            }
            sink.setReferenceEnd(referenceEnd);
        }

        Iterator<SequenceSegment> iterator = new BreadthFirstIterator<>(graph, true);
        long position = referenceEnd;
        for (SequenceSegment vertex : IterUtils.toIterable(iterator)) {
            // update start position
            if (vertex.getReferenceEnd() > position) {
                vertex.setReferenceEnd(position);
            }

            // update current position
            position = vertex.getReferenceEnd();
            if (vertex.getSources().contains(reference)
                    && !vertex.getContent().isEmpty()) {
                position -= vertex.getContent().getLength();
            }
        }
    }

}
