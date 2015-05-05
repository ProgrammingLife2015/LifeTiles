package nl.tudelft.lifetiles.graph.traverser;

import nl.tudelft.lifetiles.graph.Graph;
import nl.tudelft.lifetiles.graph.sequence.SequenceSegment;

/**
 * Position mutation annotations on sequences in a graph of sequences,
 * by using the type of sequence content and absolute coördinates.
 * @author Jos
 */
public class MutationTraverser implements GraphTraverser<SequenceSegment> {

    private String reference;

    /**
     * Construct a MutationTraverser with the given reference.
     * @param reference
     *              Reference used to keep track of the reference segments.
     */
	public MutationTraverser(String reference) {
	    this.reference = reference;
    }

    /**
	 * Traverse a graph and return the mutation annotated graph.
	 * @param graph
	 *			The graph that is being mutation annotated.
	 * @return mutation annotated graph.
	 */
	@Override
	public Graph<SequenceSegment> traverseGraph(Graph<SequenceSegment> graph) {
		return graph;
	}

}
