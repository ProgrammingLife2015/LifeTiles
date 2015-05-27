package nl.tudelft.lifetiles.graph.model.jgrapht;

import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.graph.model.GraphFactory;

/**
 * @author Rutger van den Berg
 *
 * @param <V>
 *            The type of Vertex to use.
 */
public class JGraphTGraphFactory<V extends Comparable<V>> implements
        GraphFactory<V> {
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
        return new JGraphTGraphAdapter<V>(edgeFact);
    }
}
