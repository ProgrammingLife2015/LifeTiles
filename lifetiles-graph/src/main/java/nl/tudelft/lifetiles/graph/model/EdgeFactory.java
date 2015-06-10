package nl.tudelft.lifetiles.graph.model;


/**
 * @author Rutger van den Berg
 * @param <V>
 *            The type of vertex to use.
 */
public interface EdgeFactory<V> {
    /**
     * @return A new edge from <code>v1</code> to <code>v2</code>
     * @param source
     *            The source vertex.
     * @param destination
     *            The destination vertex.
     */
    Edge<V> getEdge(final V source, final V destination);
}
