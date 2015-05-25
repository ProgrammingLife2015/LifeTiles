package nl.tudelft.lifetiles.graph.model;


/**
 * @author Rutger van den Berg
 * @param <V>
 *            The type of vertex to use.
 */
public interface EdgeFactory<V> {
    /**
     * @return A new edge from <code>v1</code> to <code>v2</code>
     * @param v1
     *            The source vertex.
     * @param v2
     *            The destination vertex.
     */
    Edge<V> getEdge(final V v1, final V v2);
}
