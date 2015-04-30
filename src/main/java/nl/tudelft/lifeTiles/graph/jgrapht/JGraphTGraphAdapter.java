package nl.tudelft.lifeTiles.graph.jgrapht;

import java.util.Set;

import nl.tudelft.lifeTiles.graph.DirectedEdge;
import nl.tudelft.lifeTiles.graph.Graph;

/**
 * @author Rutger van den Berg
 *
 * @param <V> The type of vertex to use.
 */
public class GraphAdapter<V> implements Graph<V> {

    /**
     * @param vertex
     *            The vertex to add.
     */
    public void addVertex(final V vertex) {
        // TODO Auto-generated method stub
    }

    /**
     * @param source
     *            The source vertex to use.
     * @param destination
     *            The destination vertex to use.
     * @return <code>true</code> iff adding succeeded.
     */
    public final boolean addEdge(final V source, final V destination) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @param vertex
     *            The vertex to use.
     * @return The edges incoming to <code>vertex</code>.
     */
    public final Set<DirectedEdge<V>> getIncoming(final V vertex) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param vertex
     *            The vertex to use.
     * @return The edges outgoing from <code>vertex</code>.
     */
    public final Set<DirectedEdge<V>> getOutgoing(final V vertex) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return All vertices that have no incoming edges.
     */
    public final Set<V> getSource() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return All edges.
     */
    public final Set<DirectedEdge<V>> getAllEdges() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return ALll vertices.
     */
    public final Set<V> getAllVertices() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param e
     *            The edge to use.
     * @return The source <code>Vertex</code> for <code>e</code>.
     */
    public final V getSource(final DirectedEdge<V> e) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param e
     *            The edge to use.
     * @return The destination <code>vertex</code> for <code>e</code>.
     */
    public final V getDestination(final DirectedEdge<V> e) {
        // TODO Auto-generated method stub
        return null;
    }

}
