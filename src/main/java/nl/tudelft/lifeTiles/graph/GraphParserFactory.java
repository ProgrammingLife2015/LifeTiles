package nl.tudelft.lifeTiles.graph;

/**
 * @author Rutger van den Berg
 * Creates GraphParser
 * @param <V> The type of vertex to use.
 */
public abstract class GraphParserFactory<V> implements AbstractGraphFactory<V> {

    /**
     * @return <code>null</code>, because we're not a GraphFactory.
     */
    public final Graph<V> getGraph() {
        return null;
    }

    /**
     * @return A new edge if this is an EdgeFactory, <code>null</code>
     *         otherwise.
     * @param v1 The first vertex to use.
     * @param v2 The second vertex to use.
     */
    public final DirectedEdge<V> getEdge(final V v1, final V v2) {
        return null;
    }

    /**
     * @return A new GraphParser
     */
    public abstract GraphParser<V> getGraphParser();

}
