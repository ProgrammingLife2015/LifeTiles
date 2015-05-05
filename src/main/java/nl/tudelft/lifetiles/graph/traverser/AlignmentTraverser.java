package nl.tudelft.lifetiles.graph.traverser;

import nl.tudelft.lifetiles.graph.Edge;
import nl.tudelft.lifetiles.graph.Graph;
import nl.tudelft.lifetiles.graph.sequence.SegmentEmpty;
import nl.tudelft.lifetiles.graph.sequence.SequenceSegment;

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
		for (SequenceSegment source : graph.getSource()) {
			traverseVertex(graph, source);
		}
		return graph;
	}

	/**
	 * Traverse a vertex in the graph.
	 * @param graph
	 *			The graph that can be modified on.
	 * @param vertex
	 *			The vertex that is being traversed.
	 */
	private void traverseVertex(Graph<SequenceSegment> graph, SequenceSegment vertex) {
		for (Edge<SequenceSegment> edge : graph.getOutgoing(vertex)) {
			SequenceSegment destination = graph.getDestination(edge);
			if (vertex.distanceTo(destination) > 1) {
				graph.divideEdge(edge, bridgeSequence(vertex, destination));
			}
		}
	}
	
	/**
	 * Creates a bridge sequence segment between a source and destination segment.
	 * @param source
	 *			The vertex that is the source segment.
	 * @param destination
	 *			The vertex that is the destination segment.
	 * @return sequence segment between source and destination segment
	 */
	private SequenceSegment bridgeSequence(SequenceSegment source, SequenceSegment destination) {
		return new SequenceSegment(
			destination.getSources(),
			source.getEnd() + 1,
			destination.getStart() - 1,
			new SegmentEmpty(source.distanceTo(destination))
		);
	}
}
