<<<<<<< HEAD:src/main/java/nl/tudelft/lifetiles/graph/models/jgrapht/JGraphTGraphFactory.java
package nl.tudelft.lifetiles.graph.models.jgrapht;

import nl.tudelft.lifetiles.graph.models.Graph;
import nl.tudelft.lifetiles.graph.models.GraphFactory;
=======
package nl.tudelft.lifetiles.graph.models;

import nl.tudelft.lifetiles.graph.models.edge.JGraphTEdgeFactory;
>>>>>>> removed @SuppressWarnings("restriction"):src/main/java/nl/tudelft/lifetiles/graph/models/JGraphTGraphFactory.java

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
        return new JGraphTGraphAdapter<V>(edgeFact);
    }
}
