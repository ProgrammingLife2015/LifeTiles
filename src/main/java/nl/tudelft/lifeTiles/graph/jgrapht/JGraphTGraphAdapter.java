package nl.tudelft.lifeTiles.graph.jgrapht;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nl.tudelft.lifeTiles.graph.Edge;
import nl.tudelft.lifeTiles.graph.Graph;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * @author Rutger van den Berg
 *
 * @param <V>
 *            The type of vertex to use.
 */
public class JGraphTGraphAdapter<V> implements Graph<V> {
    /**
     * This is the actual graph.
     */
    private DirectedAcyclicGraph<V, DefaultEdge> internalGraph;
    /**
     * Keep track of all vertices that have no incoming edges.
     */
    private Set<V> sources;
    /**
     * The edgefactory to use to create the edges for this graph.
     */
    private JGraphTEdgeFactory<V> edgeFact;
    /**
     * List of vertices. Used to be able to identify nodes by ids.
     */
    private List<V> vertexIdentifiers;

    /**
     * Creates a new Graph.
     *
     * @param ef
     *            The edgefactory to use for this graph.
     */
    JGraphTGraphAdapter(final JGraphTEdgeFactory<V> ef) {
        internalGraph = new DirectedAcyclicGraph<V, DefaultEdge>(
                DefaultEdge.class);
        edgeFact = ef;
        sources = new HashSet<>();
        vertexIdentifiers = new ArrayList<>();
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
    }

    /**
     * @param source
     *            The source vertex to use.
     * @param destination
     *            The destination vertex to use.
     * @return <code>true</code> iff adding succeeded.
     */
    @Override
    public final boolean addEdge(final V source, final V destination) {
        try {
            internalGraph.addEdge(source, destination);
            sources.remove(destination);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * @param vertex
     *            The vertex to use.
     * @return The edges incoming to <code>vertex</code>.
     */
    @Override
    public final Set<Edge<V>> getIncoming(final V vertex) {
        return convertEdges(internalGraph.incomingEdgesOf(vertex));
    }

    /**
     * @param vertex
     *            The vertex to use.
     * @return The edges outgoing from <code>vertex</code>.
     */
    @Override
    public final Set<Edge<V>> getOutgoing(final V vertex) {
        return convertEdges(internalGraph.outgoingEdgesOf(vertex));
    }

    /**
     * Helper for getIncoming and getOutgoing.
     *
     * @param input
     *            the set to convert.
     * @return The converted set.
     */
    private Set<Edge<V>> convertEdges(final Set<DefaultEdge> input) {
        Iterator<DefaultEdge> edgeIt = input.iterator();
        Set<Edge<V>> output = new HashSet<>();
        while (edgeIt.hasNext()) {
            output.add(edgeFact.getEdge(edgeIt.next()));
        }
        return output;
    }

    /**
     * @return All vertices that have no incoming edges.
     */
    @Override
    public final Set<V> getSource() {
        return sources;
    }

    /**
     * @return All edges.
     */
    @Override
    public final Set<Edge<V>> getAllEdges() {
        return convertEdges(internalGraph.edgeSet());
    }

    /**
     * @return All vertices.
     */
    @Override
    public final Set<V> getAllVertices() {
        return internalGraph.vertexSet();
    }

    /**
     * @param e
     *            The edge to use.
     * @return The source <code>Vertex</code> for <code>e</code>.
     */
    @Override
    public final V getSource(final Edge<V> e) {

        return internalGraph.getEdgeSource(unpackEdge(e));
    }

    /**
     * @param e
     *            The edge to use.
     * @return The destination <code>vertex</code> for <code>e</code>.
     */
    @Override
    public final V getDestination(final Edge<V> e) {
        return internalGraph.getEdgeTarget(unpackEdge(e));
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
            throw new IllegalArgumentException("Wrong edge type!");
        }
        JGraphTEdgeAdapter<V> ed = (JGraphTEdgeAdapter<V>) input;
        return ed.getInternalEdge();
    }

    @Override
    public final void addEdge(final int source, final int destination) {
        addEdge(vertexIdentifiers.get(source),
                vertexIdentifiers.get(destination));

    }

}
