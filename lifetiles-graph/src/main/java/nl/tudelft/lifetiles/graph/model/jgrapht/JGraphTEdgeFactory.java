package nl.tudelft.lifetiles.graph.model.jgrapht;

import nl.tudelft.lifetiles.graph.model.Edge;
import nl.tudelft.lifetiles.graph.model.EdgeFactory;

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
    private final ClassBasedEdgeFactory<V, DefaultEdge> internalFactory;

    /**
     * Creates a new edge factory.
     */
    public JGraphTEdgeFactory() {
        internalFactory = new ClassBasedEdgeFactory<V, DefaultEdge>(
                DefaultEdge.class);
    }

    /**
     * @param edge
     *            The internal edge to use.
     * @return a new edge.
     */
    public Edge<V> getEdge(final DefaultEdge edge) {
        return new JGraphTEdgeAdapter<V>(edge);
    }

    /**
     * @return A new edge from <code>v1</code> to <code>v2</code>
     * @param source
     *            The source vertex.
     * @param destination
     *            The destination vertex.
     */
    @Override
    public Edge<V> getEdge(final V source, final V destination) {
        return new JGraphTEdgeAdapter<V>(internalFactory.createEdge(source, destination));

    }

}
