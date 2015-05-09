package nl.tudelft.lifetiles.graph.traverser;

import nl.tudelft.lifetiles.graph.Graph;

/**
 * Interface for a generic graph traversal on a generic graph.
 * @author Jos
 * @param <V>
 *            The Class to use as vertices.
 */
public interface GraphTraverser<V> {

	/**
	 * Traverse a graph and return the modified graph.
	 * @param graph
	 *			The graph that is being traversed.
	 * @return (modified) graph.
	 */
	Graph<V> traverseGraph(Graph<V> graph);
}