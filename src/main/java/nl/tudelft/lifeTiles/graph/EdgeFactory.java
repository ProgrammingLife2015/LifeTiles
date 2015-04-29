package nl.tudelft.lifeTiles.graph;

/**
 * @author Rutger van den Berg
 *
 * @param <V>
 *            The type of vertex to use.
 */
public abstract class EdgeFactory<V> implements AbstractGraphFactory<V> {
    /**
     * @return <code>null</code>, because this is not a GraphFactory.
     */
    public final Graph<V> getGraph() {
        return null;
    }

    /**
     * @return A new edge if this is an EdgeFactory, <code>null</code>
     *         otherwise.
     * @param v1
     *            The first vertex to use.
     * @param v2
     *            The second vertex to use.
     */
    public abstract DirectedEdge<V> getEdge(V v1, V v2);

    /**
     * @return <code>null</code>, because this is not a GraphParserFactory.
     */
    public final GraphParser<V> getGraphParser() {
        return null;
    }
}
