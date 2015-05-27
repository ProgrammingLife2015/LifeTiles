package nl.tudelft.lifetiles.graph.traverser;

import java.util.Calendar;

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
    public final Graph<SequenceSegment> indicateGraphMutations(
            final Graph<SequenceSegment> graph) {
        graphVar = graph;
        indicateGraphMutations();
        return graphVar;
    }

    /**
     * Traverse the graph and indicates the mutation types.
     */
    private void indicateGraphMutations() {
        long startTime = Calendar.getInstance().getTimeInMillis();
        for (SequenceSegment vertex : graphVar.getAllVertices()) {
            indicateVertexMutations(vertex);
        }
        System.out.println("Mutations indicated. Took "
                + (Calendar.getInstance().getTimeInMillis() - startTime)
                + " ms.");
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
