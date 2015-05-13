package nl.tudelft.lifetiles.graph.models;

import java.util.Set;

/**
 * Interface for a generic graph.
 *
 * @author Rutger van den Berg
 * @param <V>
 *            The Class to use as vertices.
 */
public interface Graph<V> {
    /**
     * @param source
     *            Id of the source vertex.
     * @param destination
     *            Id of the destination vertex.
     */
    void addEdge(int source, int destination);

    /**
     * Add an edge from source to destination to the graph.
     *
     * @param source
     *            The source vertex.
     * @param destination
     *            The destination vertex.
     * @return true iff adding succeeded.
     */
    boolean addEdge(V source, V destination);

    /**
     * Add the given vertex to the graph.
     *
     * @param vertex
     *            The object to use as vertex.
     */
    void addVertex(V vertex);

    /**
     * @return A Set containing all edges in the graph.
     */
    Set<Edge<V>> getAllEdges();

    /**
     * @return A Set containing all vertices in the graph.
     */
    Set<V> getAllVertices();

    /**
     * @param e
     *            The edge for which the destination is to be retrieved.
     * @return The destination vertex.
     */
    V getDestination(Edge<V> e);

    /**
     * Get all incoming edges connecting to the given vertex.
     *
     * @param vertex
     *            The vertex for which the incoming edges will be retrieved.
     * @return A Set of incoming edges.
     */
    Set<Edge<V>> getIncoming(V vertex);

    /**
     * @param vertex
     *            The vertex for which the outgoing edges will be retrieved.
     * @return A Set of outgoing edges.
     */
    Set<Edge<V>> getOutgoing(V vertex);

    /**
     * @return A Set of source nodes. Source being a node that has no incoming
     *         edges.
     */
    Set<V> getSource();

    /**
     * @param e
     *            THe edge for which the source is to be retrieved.
     * @return The source vertex.
     */
    V getSource(Edge<V> e);

    /**
     * @return A Set of sink nodes. Sink being a node that has no outgoing
     *         edges.
     */
    Set<V> getSink();

    /**
     * Divides an edge into two edges with an inserted vertex in the middle.
     *
     * @param edge
     *            Edge to be divided.
     * @param vertex
     *            Vertex to be inserted.
     */
    void divideEdge(Edge<V> edge, V vertex);
}
