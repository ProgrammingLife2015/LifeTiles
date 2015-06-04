package nl.tudelft.lifetiles.graph.model;

import java.util.Set;

import nl.tudelft.lifetiles.graph.model.jgrapht.NotAJGraphTAdapterException;

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
     *            vertices to be included in the subgraph. null will include all
     *            vertices of the base graph.
     * @return a subgraph
     * @throws NotAJGraphTAdapterException
     *             if the base graph is not a JGraphT library
     */
    Graph<V> getSubGraph(Graph<V> base, Set<V> vertexSubSet)
            throws NotAJGraphTAdapterException;

    /**
     * Returns a copy of the graph object where the vertices and edges are not
     * copied from.
     *
     * @param graph
     *            graph to be copied
     * @return copy of the Graph.
     */
    Graph<V> copy(Graph<V> graph);

    /**
     * Returns a new deepcopy of the graph.
     *
     * @param graph
     *            graph to ceate a new deepcopy from
     *
     * @return a deep copy of the graph
     */
    Graph<V> deepcopy(Graph<V> graph);

}
