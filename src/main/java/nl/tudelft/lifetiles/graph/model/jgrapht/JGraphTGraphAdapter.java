package nl.tudelft.lifetiles.graph.model.jgrapht;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import nl.tudelft.lifetiles.graph.model.Edge;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.graph.model.GraphFactory;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedSubgraph;
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
    private DirectedGraph<V, DefaultEdge> internalGraph;

    /**
     * Keep track of all vertices that have no incoming edges.
     */
    private final SortedSet<V> sources;
    /**
     * Keep track of all vertices that have no outgoing edges.
     */
    private final SortedSet<V> sinks;
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
     * Create a new graph based on the base graph. It will take the vertexset
     * and create a graph only containing those vertices and the corresponding
     * edges.
     *
     * @param base
     *            the graph to base a new graph on
     * @param vertexSubSet
     *            the vertices that must be in the new graph. If set to null,
     *            all vertices will be included.
     *
     * @param ef
     *            The edgefactory to use for this graph
     */
    public JGraphTGraphAdapter(final Graph<V> base, final Set<V> vertexSubSet,
            final JGraphTEdgeFactory<V> ef) {
        internalGraph = new DirectedSubgraph<V, DefaultEdge>(base
                .getInternalGraph(), vertexSubSet, null);
        edgeFact = ef;
        sources = new TreeSet<>();
        sinks = new TreeSet<>();
        vertexIdentifiers = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void addEdge(final int source, final int destination) {
        addEdge(vertexIdentifiers.get(source), vertexIdentifiers
                .get(destination));

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
        sources.add(vertex);
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

    @Override
    public final DirectedGraph<V, DefaultEdge> getInternalGraph() {
        return internalGraph;
    }

    /**
     * Returns a copy of the graph including edges and vertices.
     *
     * @param gfact
     *            Factory which is used to copy the graph.
     * @return copy of the Graph.
     */
    @Override
    public final Graph<V> copy(final GraphFactory<V> gfact) {
        Graph<V> graph = gfact.getGraph();
        for (V vertex : getAllVertices()) {
            graph.addVertex(vertex);
        }
        for (Edge<V> edge : getAllEdges()) {
            graph.addEdge(getSource(edge), getDestination(edge));
        }
        return graph;
    }

    @Override
    public final Graph<V> deepcopy(final GraphFactory<V> gfact) {
        Graph<V> copygraph = gfact.getGraph();

        Map<Object, Object> convertVertices = new HashMap<Object, Object>();

        for (V vertex : this.getAllVertices()) {
            try {
                Method method;
                method = vertex.getClass().getMethod("clone");
                Object copy = method.invoke(vertex);
                copygraph.addVertex((V) copy);
                convertVertices.put(vertex, copy);

            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        for (Edge<V> edge : this.getAllEdges()) {
            Object from = convertVertices.get(this.getSource(edge));
            Object destination = convertVertices.get(this.getDestination(edge));

            copygraph.addEdge((V) from, (V) destination);
        }

        return copygraph;
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
