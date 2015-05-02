package nl.tudelft.lifeTiles.graph.jgrapht;

import nl.tudelft.lifeTiles.graph.Edge;
import nl.tudelft.lifeTiles.graph.EdgeFactory;

import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultEdge;

/**
 * @author Rutger van den Berg
 *
 * @param <V>
 *            The type of vertex to use.
 */
public class JGraphTEdgeFactory<V> implements EdgeFactory<V> {
    /**
     * Internal factory to use.
     */
    private ClassBasedEdgeFactory<V, DefaultEdge> internalFactory;

    /**
     * @return A new edge from <code>v1</code> to <code>v2</code>
     * @param v1
     *            The source vertex.
     * @param v2
     *            The destination vertex.
     */
    public final Edge<V> getEdge(final V v1, final V v2) {
        return new JGraphTEdgeAdapter<V>(internalFactory.createEdge(v1, v2));

    }

    /**
     * @param e
     *            The internal edge to use.
     * @return a new edge.
     */
    public final Edge<V> getEdge(final DefaultEdge e) {
        return new JGraphTEdgeAdapter<V>(e);
    }

}
