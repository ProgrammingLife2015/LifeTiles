package nl.tudelft.lifetiles.graph.traverser;

import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

/**
 * Indicates mutations by traversing over all the vertices.
 *
 * @author Jos
 *
 */
public class MutationIndicationTraverser {

    /**
     * Reference which is compared to determine the mutation types.
     */
    private final Sequence reference;

    /**
     * Constructs a MutationIndicationTraverser.
     *
     * @param reference
     *            Reference which is compared to determine the mutation types.
     */
    public MutationIndicationTraverser(final Sequence reference) {
        this.reference = reference;
    }

    /**
     * Traverse the graph and indicates the mutation types.
     *
     * @param graph
     *            The graph to use.
     * @return traversed graph.
     */
    public final Graph<SequenceSegment> indicateGraphMutations(
            final Graph<SequenceSegment> graph) {
        for (SequenceSegment vertex : graph.getAllVertices()) {
            indicateVertexMutations(vertex);
        }
        return graph;
    }

    /**
     * Traverse a vertex in the copy of the graph and determines the mutation
     * type of the mutation, if it has one.
     *
     * @param vertex
     *            Vertex in the graph to be traversed.
     */
    private void indicateVertexMutations(final SequenceSegment vertex) {
        if (!vertex.getSources().contains(reference)) {
            vertex.setMutation(vertex.determineMutation());
        }
    }

}
