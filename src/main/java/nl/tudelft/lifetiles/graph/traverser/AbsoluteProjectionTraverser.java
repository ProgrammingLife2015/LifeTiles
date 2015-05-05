package nl.tudelft.lifetiles.graph.traverser;

import nl.tudelft.lifetiles.graph.Graph;
import nl.tudelft.lifetiles.graph.sequence.SequenceSegment;

/**
 * Projects sequences in a graph of sequences onto the reference,
 * by generating the absolute ranges of the sequences.
 * @author Jos
 */
public class AbsoluteProjectionTraverser implements GraphTraverser<SequenceSegment> {

	/**
	 * Traverse a graph and return the absolute projected graph.
	 * @param graph
	 *			The graph that is being projected.
	 * @return absolute projected graph.
	 */
	@Override
	public Graph<SequenceSegment> traverseGraph(Graph<SequenceSegment> graph) {
		return graph;
	}


}
