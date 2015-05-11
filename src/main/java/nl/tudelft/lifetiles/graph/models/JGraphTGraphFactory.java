package nl.tudelft.lifetiles.graph.models;

import nl.tudelft.lifetiles.graph.models.edge.JGraphTEdgeFactory;



/**
 * @author Rutger van den Berg
 *
 * @param <V>
 *            The type of Vertex to use.
 */
public class JGraphTGraphFactory<V> implements GraphFactory<V> {
    /**
     * The edgefactory associated with this graph factory.
     */
    private JGraphTEdgeFactory<V> edgeFact;

    /**
     * Create a new graph factory.
     */
    public JGraphTGraphFactory() {
        edgeFact = new JGraphTEdgeFactory<>();
    }

    /**
     * @return a new empty Graph.
     */
    @Override
    public final Graph<V> getGraph() {
        return new JGraphTGraph<V>(edgeFact);
    }
}
