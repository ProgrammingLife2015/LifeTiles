package nl.tudelft.lifetiles.traverser.models;

import nl.tudelft.lifetiles.graph.models.Graph;
import nl.tudelft.lifetiles.graph.models.sequence.Sequence;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;

/**
 * Indicates mutations by traversing over all the vertices.
 *
 * @author Jos
 *
 */
public class MutationIndicationTraverser implements Traverser {

    /**
     * Reference which is compared to determine the mutation types.
     */
    private Sequence referenceVar;

    /**
     * Constructs a MutationIndicationTraverser.
     *
     * @param reference
     *            Reference which is compared to determine the mutation types.
     */
    public MutationIndicationTraverser(final Sequence reference) {
        referenceVar = reference;
    }

    /**
     * Graph which is being traversed.
     */
    private Graph<SequenceSegment> graphVar;

    /**
     * Traverses the graph and indicates the mutation types.
     *
     * @param graph
     *            The graph to traverse.
     * @return the traversed graph.
     */
    public final Graph<SequenceSegment> traverseGraph(
            final Graph<SequenceSegment> graph) {
        graphVar = graph;
        indicateGraphMutations();
        return graphVar;
    }

    /**
     * Traverse the graph and indicates the mutation types.
     *
     * @return traversed graph.
     */
    private Graph<SequenceSegment> indicateGraphMutations() {
        for (SequenceSegment vertex : graphVar.getAllVertices()) {
            indicateVertexMutations(vertex);
        }
        return graphVar;
    }

    /**
     * Traverse a vertex in the copy of the graph and determines the mutation
     * type of the mutation, if it has one.
     *
     * @param vertex
     *            Vertex in the graph to be traversed.
     */
    private void indicateVertexMutations(final SequenceSegment vertex) {
        if (!vertex.getSources().contains(referenceVar)) {
            vertex.setMutation(vertex.determineMutation());
        }
    }

}
