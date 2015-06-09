package nl.tudelft.lifetiles.graph.traverser;

import nl.tudelft.lifetiles.core.util.Timer;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

/**
 * Indicates mutations by traversing over all the vertices.
 *
 * @author Jos
 *
 */
public final class MutationIndicationTraverser {

    /**
     * Don't instaniate.
     */
    private MutationIndicationTraverser() {

    }

    /**
     * Traverse the graph and indicates the mutation types.
     *
     * @param graph
     *            The graph to use.
     * @param reference
     *            The reference sequence.
     * @return traversed graph.
     */
    public static Graph<SequenceSegment> indicateGraphMutations(
            final Graph<SequenceSegment> graph, final Sequence reference) {
        Timer timer = Timer.getAndStart();

        for (SequenceSegment vertex : graph.getAllVertices()) {
            indicateVertexMutations(vertex, reference);
        }

        timer.stopAndLog("Calculating mutations");

        return graph;
    }

    /**
     * Traverse a vertex in the copy of the graph and determines the mutation
     * type of the mutation, if it has one.
     *
     * @param vertex
     *            Vertex in the graph to be traversed.
     * @param reference
     *            The reference sequence.
     */
    private static void indicateVertexMutations(final SequenceSegment vertex,
            final Sequence reference) {
        if (!vertex.getSources().contains(reference)) {
            vertex.setMutation(vertex.determineMutation());
        }
    }

}
