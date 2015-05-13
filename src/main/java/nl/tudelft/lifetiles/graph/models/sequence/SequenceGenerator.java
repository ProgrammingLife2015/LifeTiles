package nl.tudelft.lifetiles.graph.models.sequence;

import java.util.HashMap;
import java.util.Map;

import nl.tudelft.lifetiles.graph.models.Edge;
import nl.tudelft.lifetiles.graph.models.Graph;

/**
 * Class that derives seperate sequences from a sequencegraph.
 *
 * @author Rutger van den Berg
 */
public class SequenceGenerator {
    /**
     * Keep track of which segments have been processed.
     */
    private Map<SequenceSegment, Boolean> processed;
    /**
     * The generated sequences.
     */
    private Map<String, Sequence> sequences;
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
        processed = new HashMap<>();
        sequences = new HashMap<>();
    }

    /**
     * @return A list of sequences
     */
    public final Map<String, Sequence> generateSequences() {

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
            if (!sequences.containsValue(seq)) {
                sequences.put(seq.getIdentifier(), seq);
            }
            seq.appendSegment(vertex);
        }
        for (Edge<SequenceSegment> edge : sourceGraph.getOutgoing(vertex)) {
            SequenceSegment candidate = sourceGraph.getDestination(edge);
            if (!processed.containsKey(candidate)) {
                processed.put(candidate, Boolean.valueOf(true));
                generateSequences(candidate);
            }
        }
    }
}
