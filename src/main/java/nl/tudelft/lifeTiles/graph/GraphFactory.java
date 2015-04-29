package nl.tudelft.lifeTiles.graph;

/**
 * @author Rutger van den Berg
 * @param <V> the vertex type.
 * Factory interface for Graphs
 */
public abstract class GraphFactory<V> implements AbstractGraphFactory<V> {
    /**
     * @return a new empty graph.
     */
    public abstract Graph<V> getGraph();

    /**
     * @return <code>null</code>, because this is not an EdgeFactory.
     * @param v1 The first vertex to use.
     * @param v2 The second vertex to use.
     */
    public final DirectedEdge<V> getEdge(final V v1, final V v2) {
        return null;
    }

    /**
     * @return <code>null</code>, because this is not a GraphParserFactory.
     */
    public final GraphParser<V> getGraphParser() {
        return null;
    }
}
