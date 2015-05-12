package nl.tudelft.lifetiles.graph.models.jgrapht;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nl.tudelft.lifetiles.graph.models.Edge;
import nl.tudelft.lifetiles.graph.models.Graph;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.DefaultEdge;

public class JGraphTGraphAdapter<V> implements Graph<V> {
	/**
	 * The edgefactory to use to create the edges for this graph.
	 */
	private JGraphTEdgeFactory<V> edgeFact;
	/**
	 * This is the actual graph.
	 */
	private DirectedAcyclicGraph<V, DefaultEdge> internalGraph;
	/**
	 * Keep track of all vertices that have no incoming edges.
	 */
	private Set<V> sources;
	/**
	 * Keep track of all vertices that have no outgoing edges.
	 */
	private Set<V> sinks;
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
	public JGraphTGraphAdapter(final JGraphTEdgeFactory<V> ef) {
		internalGraph = new DirectedAcyclicGraph<V, DefaultEdge>(
				DefaultEdge.class);
		edgeFact = ef;
		sources = new HashSet<>();
		sinks = new HashSet<>();
		vertexIdentifiers = new ArrayList<>();
	}

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
	 * @return <code>true</code> iff adding succeeded.
	 */
	@Override
	public final boolean addEdge(final V source, final V destination) {
		try {
			internalGraph.addEdge(source, destination);
			sources.remove(destination);
			sinks.remove(source);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
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
	private Set<Edge<V>> convertEdges(final Set<DefaultEdge> input) {
		Iterator<DefaultEdge> edgeIt = input.iterator();
		Set<Edge<V>> output = new HashSet<>();
		while (edgeIt.hasNext()) {
			output.add(edgeFact.getEdge(edgeIt.next()));
		}
		return output;
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
	 * @return The destination <code>vertex</code> for <code>e</code>.
	 */
	@Override
	public final V getDestination(final Edge<V> e) {
		return internalGraph.getEdgeTarget(unpackEdge(e));
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
	 * @return All vertices that have no incoming edges.
	 */
	@Override
	public final Set<V> getSource() {
		return sources;
	}

	/**
	 * @return All vertices that have no outgoing edges.
	 */
	@Override
	public Set<V> getSink() {
		return sinks;
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

	/**
	 * Divides an edge into two edges with an inserted vertex in the middle.
	 * 
	 * @param edge
	 *            Edge to be divided.
	 * @param vertex
	 *            Vertex to be inserted.
	 */
	@Override
	public void divideEdge(Edge<V> edge, V vertex) {
		removeEdge(edge);
		addVertex(vertex);
		addEdge(getSource(edge), vertex);
		addEdge(vertex, getDestination(edge));
	}

	/**
	 * @param edge
	 *            Edge to be removed.
	 */
	private void removeEdge(Edge<V> edge) {
		internalGraph.removeEdge(unpackEdge(edge));
	}

}