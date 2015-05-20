package nl.tudelft.lifetiles.graph.model;

import java.util.Set;

/**
 * Factory interface for Graphs.
 *
 * @author Rutger van den Berg
 * @param <V>
 *            the vertex type.
 */
public interface GraphFactory<V> {
    /**
     * @return a new empty graph.
     */
    Graph<V> getGraph();

    /**
     * Create a subgraph based on a new graph.
     *
     * @param base
     *            the graph to create a new subgraph from
     * @param vertexSubSet
     *            vertices to be included in the subgraph
     * @return a subgraph
     */
    Graph<V> getSubGraph(Graph<V> base, Set<V> vertexSubSet);

}
