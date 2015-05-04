package nl.tudelft.lifetiles.graph;

/**
 * @author Rutger van den Berg
 * @param <V>
 *            the vertex type. Factory interface for Graphs
 */
public interface GraphFactory<V> {
    /**
     * @return a new empty graph.
     */
    Graph<V> getGraph();
}
