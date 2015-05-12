package nl.tudelft.lifetiles.graph.controller.traverser;

import nl.tudelft.lifetiles.graph.models.Graph;
import nl.tudelft.lifetiles.graph.models.sequence.SegmentEmpty;
import nl.tudelft.lifetiles.graph.models.sequence.Sequence;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;
import nl.tudelft.lifetiles.graph.models.sequence.mutation.DeletionMutation;
import nl.tudelft.lifetiles.graph.models.sequence.mutation.InsertionMutation;
import nl.tudelft.lifetiles.graph.models.sequence.mutation.PolymorphismMutation;

/**
 * Position mutation annotations on sequences in a graph of sequences,
 * by using the type of sequence content and absolute coördinates.
 *
 * @author Jos
 */
public class MutationTraverser implements GraphTraverser<SequenceSegment> {

    /**
     * Reference sequence which mutations are annotated to.
     */
    private Sequence reference;

    /**
     * Construct a MutationTraverser with the given reference.
     *
     * @param reference
     *            Reference used to keep track of the reference segments.
     */
    public MutationTraverser(final Sequence reference) {
        this.reference = reference;
    }

    /**
     * Traverse a graph and return the mutation annotated graph.
     *
     * @param graph
     *            The graph that is being mutation annotated.
     * @return mutation annotated graph.
     */
    @Override
    public Graph<SequenceSegment> traverseGraph(Graph<SequenceSegment> graph) {
        for (SequenceSegment vertex : graph.getAllVertices()) {
            traverseVertex(graph, vertex);
        }
        return graph;
    }

    /**
     * Traverse a vertex in the graph and annotates it with a mutation.
     *
     * @param graph
     *            The graph that can be modified on.
     * @param vertex
     *            The vertex that is being mutation annotated.
     */
    private void traverseVertex(Graph<SequenceSegment> graph,
            final SequenceSegment vertex) {
        if (!vertex.getSources().contains(reference)) {
            if (vertex.getContent() instanceof SegmentEmpty) {
                vertex.setMutation(new DeletionMutation());
            } else if (vertex.getAbsStart() > vertex.getAbsEnd()) {
                vertex.setMutation(new InsertionMutation());
            } else {
                vertex.setMutation(new PolymorphismMutation());
            }
        }
    }

}
