package nl.tudelft.lifetiles.graph.traverser;

import nl.tudelft.lifetiles.graph.Graph;
import nl.tudelft.lifetiles.graph.SequenceSegment;

/**
 * Aligns sequences in a graph of sequences, by filling empty space with empty vertices.
 * @author Jos
 */
public class AlignmentTraverser implements GraphTraverser<SequenceSegment> {

	/**
	 * Traverse a graph and return the aligned graph.
	 * @param graph
	 *			The graph that is being aligned.
	 * @return aligned graph.
	 */
	@Override
	public Graph<SequenceSegment> traverseGraph(Graph<SequenceSegment> graph) {
		return graph;
	}

	/**
	 * Traverse a vertex in the graph and return the aligned vertex.
	 * @param graph
	 * @param vertex
	 *			The vertex that is being traversed.
	 * @return aligned vertex.
	 */
	private SequenceSegment traverseVertex(Graph<SequenceSegment> graph, SequenceSegment vertex) {
		return vertex;
	}
}
