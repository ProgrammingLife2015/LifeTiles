package nl.tudelft.lifetiles.graph.model.jgrapht;

import java.util.Set;

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
    private final JGraphTEdgeFactory<V> edgeFact;

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

    /**
     * @param base
     *            the graph to create a subgraph from.
     * @param vertexSubSet
     *            the vertices to include in the subgraph
     * @return a subgraph based on the base graph
     */
    @Override
    public final Graph<V> getSubGraph(final Graph<V> base,
            final Set<V> vertexSubSet) {
        return new JGraphTSubGraphAdapter<V>(base, vertexSubSet, edgeFact);
    }

}
