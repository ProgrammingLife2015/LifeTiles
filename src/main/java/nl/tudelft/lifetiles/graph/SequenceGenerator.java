package nl.tudelft.lifetiles.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that derives seperate sequences from a sequencegraph.
 *
 * @author Rutger van den Berg
 */
public class SequenceGenerator {
    /**
     * keep track of which segments have been processed.
     */
    private Map<SequenceSegment, Boolean> processed;
    /**
     * the generated sequences.
     */
    private List<Sequence> sequences;
    /**
     * The graph from which the sequences are derived.
     */
    private Graph<SequenceSegment> sourceGraph;

    /**
     * @param graph
     *            A DAG graph for which to generate sequences.
     */
    public SequenceGenerator(final Graph<SequenceSegment> graph) {
        sourceGraph = graph;
    }

    /**
     * @return A list of sequences
     */
    public final List<Sequence> generateSequences() {
        processed = new HashMap<>();
        sequences = new ArrayList<>();
        for (SequenceSegment segment : sourceGraph.getSource()) {
            generateSequences(segment);
        }
        return sequences;
    }

    /**
     * Helper for generating sequences.
     *
     * @param vertex
     *            The vertex from which to start adding segments.
     */
    private void generateSequences(final SequenceSegment vertex) {
        for (Sequence seq : vertex.getSources()) {
            seq.appendSegment(vertex);
        }
        for (Edge<SequenceSegment> edge : sourceGraph.getOutgoing(vertex)) {
            SequenceSegment candidate = sourceGraph.getDestination(edge);
            if (!processed.containsKey(candidate)) {
                processed.put(candidate, new Boolean(true));
                generateSequences(candidate);
            }
        }
    }
}
