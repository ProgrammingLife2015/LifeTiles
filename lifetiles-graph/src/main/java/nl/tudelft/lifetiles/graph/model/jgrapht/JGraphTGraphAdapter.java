package nl.tudelft.lifetiles.graph.model.jgrapht;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import nl.tudelft.lifetiles.graph.model.Edge;
import nl.tudelft.lifetiles.graph.model.Graph;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

/**
 * @author Rutger van den Berg
 *
 * @param <V>
 *            The type of vertex to use.
 */
public class JGraphTGraphAdapter<V extends Comparable<V> & Cloneable>
        implements Graph<V> {
    /**
     * The edgefactory to use to create the edges for this graph.
     */
    private final JGraphTEdgeFactory<V> edgeFact;
    /**
     * This is the actual graph.
     */
    private final DirectedGraph<V, DefaultEdge> internalGraph;

    /**
     * Keep track of all vertices that have no incoming edges.
     */
    private SortedSet<V> sources;
    /**
     * Keep track of all vertices that have no outgoing edges.
     */
    private SortedSet<V> sinks;
    /**
     * List of vertices. Used to be able to identify nodes by ids.
     */
    private final List<V> vertexIdentifiers;

    /**
     * Creates a new Graph.
     *
     * @param edgeFact
     *            The edgefactory to use for this graph.
     */
    public JGraphTGraphAdapter(final JGraphTEdgeFactory<V> edgeFact) {
        internalGraph = new SimpleDirectedGraph<V, DefaultEdge>(
                DefaultEdge.class);
        this.edgeFact = edgeFact;
        sources = new TreeSet<>();
        sinks = new TreeSet<>();
        vertexIdentifiers = new ArrayList<>();
    }

    /**
     * Create a new Subgraph, based on the JGraphT library.
     *
     * @param jgraptGraph
     *            original JGraphT grah
     * @param edgeFact
     *            The edgefactory to use for this graph.
     * @param vertexIds
     *            Vertex identifiers
     */
    protected JGraphTGraphAdapter(
            final DirectedGraph<V, DefaultEdge> jgraptGraph,
            final JGraphTEdgeFactory<V> edgeFact, final List<V> vertexIds) {
        internalGraph = jgraptGraph;
        this.edgeFact = edgeFact;
        vertexIdentifiers = vertexIds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void addEdge(final int source, final int destination) {
        addEdge(vertexIdentifiers.get(source),
                vertexIdentifiers.get(destination));

    }

    /**
     * @param source
     *            The source vertex to use.
     * @param destination
     *            The destination vertex to use.
     * @throws IllegalArgumentException
     *             When the source or destination is not in the graph.
     * @throws IllegalArgumentException
     *             When edge would create a loop in the graph.
     * @return <code>true</code> iff adding succeeded.
     */
    @Override
    public final boolean addEdge(final V source, final V destination)
            throws IllegalArgumentException {
        if (internalGraph.containsVertex(source)
                && internalGraph.containsVertex(destination)) {
            internalGraph.addEdge(source, destination);
            sources.remove(destination);
            sinks.remove(source);
            return true;
        } else {
            throw new IllegalArgumentException(
                    "Source or destination not in graph.");
        }
    }

    /**
     * @param vertex
     *            The vertex to add.
     */
    @Override
    public final void addVertex(final V vertex) {
        internalGraph.addVertex(vertex);
        vertexIdentifiers.add(vertex);
        if (sources == null) {
            getSources();
        }
        sources.add(vertex);
        if (sinks == null) {
            getSinks();
        }
        sinks.add(vertex);
    }

    /**
     * Helper for getIncoming and getOutgoing.
     *
     * @param input
     *            the set to convert.
     * @return The converted set.
     */
    private SortedSet<Edge<V>> convertEdges(final Set<DefaultEdge> input) {
        SortedSet<Edge<V>> output = new TreeSet<>(new EdgeComparatorByVertex());
        for (DefaultEdge e : input) {
            output.add(edgeFact.getEdge(e));
        }
        return output;
    }

    /**
     * @return All edges.
     */
    @Override
    public final SortedSet<Edge<V>> getAllEdges() {
        return convertEdges(internalGraph.edgeSet());
    }

    /**
     * @return All vertices.
     */
    @Override
    public final SortedSet<V> getAllVertices() {
        return new TreeSet<V>(internalGraph.vertexSet());
    }

    /**
     * @param edge
     *            The edge to use.
     * @return The destination <code>vertex</code> for <code>e</code>.
     */
    @Override
    public final V getDestination(final Edge<V> edge) {
        return internalGraph.getEdgeTarget(unpackEdge(edge));
    }

    /**
     * @param vertex
     *            The vertex to use.
     * @return The edges incoming to <code>vertex</code>.
     */
    @Override
    public final SortedSet<Edge<V>> getIncoming(final V vertex) {
        return convertEdges(internalGraph.incomingEdgesOf(vertex));
    }

    /**
     * @param vertex
     *            The vertex to use.
     * @return The edges outgoing from <code>vertex</code>.
     */
    @Override
    public final SortedSet<Edge<V>> getOutgoing(final V vertex) {
        return convertEdges(internalGraph.outgoingEdgesOf(vertex));
    }

    /**
     * @return All vertices that have no incoming edges.
     */
    @Override
    public final SortedSet<V> getSources() {
        if (sources == null) {
            SortedSet<V> foundSources = new TreeSet<V>();

            for (V vertice : getAllVertices()) {
                if (!getIncoming(vertice).isEmpty()) {
                    foundSources.add(vertice);
                }
            }

            sources = foundSources;
        }
        return sources;

    }

    /**
     * @param edge
     *            The edge to use.
     * @return The source <code>Vertex</code> for <code>e</code>.
     */
    @Override
    public final V getSource(final Edge<V> edge) {

        return internalGraph.getEdgeSource(unpackEdge(edge));
    }

    /**
     * Helper method.
     *
     * @param input
     *            An edge.
     * @return the internal edge.
     */
    private DefaultEdge unpackEdge(final Edge<V> input) {
        if (!(input instanceof JGraphTEdgeAdapter<?>)) {
            throw new IllegalArgumentException("Wrong edge type.");
        }
        JGraphTEdgeAdapter<V> edge = (JGraphTEdgeAdapter<V>) input;
        return edge.getInternalEdge();
    }

    /**
     * @return All vertices that have no outgoing edges.
     */
    @Override
    public final SortedSet<V> getSinks() {
        if (sinks == null) {
            SortedSet<V> foundSinks = new TreeSet<V>();

            for (V vertice : getAllVertices()) {
                if (!getOutgoing(vertice).isEmpty()) {
                    foundSinks.add(vertice);
                }
            }

            sinks = foundSinks;
        }
        return sinks;

    }

    /**
     * Splits an edge into two edges with an inserted vertex in the middle.
     *
     * @param edge
     *            Edge to be divided.
     * @param vertex
     *            Vertex to be inserted.
     */
    @Override
    public final void splitEdge(final Edge<V> edge, final V vertex) {
        removeEdge(edge);
        addVertex(vertex);
        addEdge(getSource(edge), vertex);
        addEdge(vertex, getDestination(edge));
    }

    /**
     * @param edge
     *            Edge to be removed.
     */
    private void removeEdge(final Edge<V> edge) {
        internalGraph.removeEdge(unpackEdge(edge));
    }

    /**
     *
     * @return get the JGraphT graph
     */
    protected final DirectedGraph<V, DefaultEdge> getInternalGraph() {
        return internalGraph;
    }

    /**
     *
     * @return the vertexIdentifiers
     */
    protected final List<V> getVertexIdentifiers() {
        return vertexIdentifiers;
    }

    /**
     * @author Rutger van den Berg
     *         Compares two edges by their target vertex.
     */
    class EdgeComparatorByVertex implements Comparator<Edge<V>> {

        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(final Edge<V> left, final Edge<V> right) {
            int candidate = getDestination(left).compareTo(
                    getDestination(right));
            if (candidate == 0) {
                candidate = getSource(left).compareTo(getSource(right));
            }
            return candidate;
        }
    }

}
