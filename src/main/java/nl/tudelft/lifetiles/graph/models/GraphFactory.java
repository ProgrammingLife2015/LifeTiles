package nl.tudelft.lifetiles.graph.models;


/**
 * Factory interface for Graphs.
 *
 * @author Rutger van den Berg
 * @param <V>
 *            the vertex type.
 */
public interface GraphFactory<V extends Comparable<V>> {
    /**
     * @return a new empty graph.
     */
    Graph<V> getGraph();
}
