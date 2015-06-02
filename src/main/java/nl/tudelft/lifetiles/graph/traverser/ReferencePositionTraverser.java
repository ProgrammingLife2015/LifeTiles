package nl.tudelft.lifetiles.graph.traverser;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import nl.tudelft.lifetiles.core.util.Timer;
import nl.tudelft.lifetiles.graph.model.Edge;
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
     * Traverses the graph. Computes the position of the vertices in comparison
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
     * Traverses the graph forward to generate reference start positions.
     *
     * @param graph
     *            Graph to be traversed.
     */
    private void referenceMapGraphForward(final Graph<SequenceSegment> graph) {
        // a queue for breadth-first
        Queue<SequenceSegment> queue = new ArrayDeque<>();
        Map<SequenceSegment, Integer> waiting = new HashMap<>();

        // initialize the stack
        for (SequenceSegment source : graph.getSources()) {
            source.setReferenceStart(1);
            queue.add(source);
        }

        while (!queue.isEmpty()) {
            SequenceSegment vertex = queue.poll();
            long position = vertex.getReferenceStart();

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

            if (vertex.getSources().contains(reference)
                    && !vertex.getContent().isEmpty()) {
                position += vertex.getContent().getLength();
            }

            for (Edge<SequenceSegment> edge : graph.getOutgoing(vertex)) {
                SequenceSegment destination = graph.getDestination(edge);
                if (destination.getReferenceStart() < position) {
                    destination.setReferenceStart(position);
                }
                queue.add(destination);
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
        // a queue for breadth-first
        Queue<SequenceSegment> queue = new ArrayDeque<>();
        Map<SequenceSegment, Integer> waiting = new HashMap<>();

        // initialize the stack
        for (SequenceSegment sink : graph.getSinks()) {
            if (sink.getSources().contains(reference)) {
                sink.setReferenceEnd(sink.getReferenceStart()
                        + sink.getContent().getLength() - 1);
            } else {
                sink.setReferenceEnd(sink.getReferenceStart() - 1);
            }
            queue.add(sink);
        }

        while (!queue.isEmpty()) {
            SequenceSegment vertex = queue.poll();
            long position = vertex.getReferenceEnd();

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

            if (vertex.getSources().contains(reference)
                    && !vertex.getContent().isEmpty()) {
                position -= vertex.getContent().getLength();
            }
            for (Edge<SequenceSegment> edge : graph.getIncoming(vertex)) {
                SequenceSegment source = graph.getSource(edge);
                if (source.getReferenceEnd() > position) {
                    source.setReferenceEnd(position);
                }
                queue.add(source);
            }
        }
    }

}
