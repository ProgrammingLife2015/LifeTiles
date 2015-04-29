package nl.tudelft.lifeTiles.graph;

/**
 * @author Rutger van den Berg Interface for factories related to graphs.
 * @param <V>
 *            The type of vertex to use.
 */
public interface AbstractGraphFactory<V> {
    /**
     * @return A new Graph if this is a GraphFactory, <code>null</code>
     *         otherwise.
     */
    Graph<V> getGraph();

    /**
     * @return A new edge if this is an EdgeFactory, <code>null</code>
     *         otherwise.
     * @param v1 The first vertex to use.
     * @param v2 The second vertex to use.
     */
    DirectedEdge<V> getEdge(V v1, V v2);

    /**
     * @return A new GraphParser if this is a GraphParserFactory,
     *         <code>null</code> otherwise.
     */
    GraphParser<V> getGraphParser();
}
