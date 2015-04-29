package nl.tudelft.lifeTiles.graph;

/**
 * @author Rutger van den Berg
 * @param <V> the vertex type.
 * Factory interface for Graphs
 */
public interface GraphFactory<V> {
    /**
     * @return a new empty graph.
     */
    Graph<V> getGraph();

    /**
     * @return A new edge from <code>v1</code> to <code>v2</code>
     * @param v1 The source vertex.
     * @param v2 The destination vertex.
     */
    DirectedEdge<V> getEdge(final V v1, final V v2);
}
