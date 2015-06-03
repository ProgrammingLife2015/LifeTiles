package nl.tudelft.lifetiles.graph.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Iterate the graph from source to sink using true vertical
 * breadth-first search.
 *
 * @author Joren Hammudoglu
 *
 * @param <V>
 *            the vertex type
 */
public class BreadthFirstIterator<V> implements Iterator<V> {

    /**
     * The graph to iterate over.
     */
    private final Graph<V> graph;

    /**
     * The queued vertices.
     */
    private final Queue<V> queue;

    /**
     * The waiting vertices with their remaining count.
     */
    private final Map<V, Integer> waiting;

    /**
     * Whether it should iterate backwards.
     */
    private boolean reverse;

    /**
     * Create a new iterator.
     *
     * @param graph
     *            the graph to iterate over.
     */
    public BreadthFirstIterator(final Graph<V> graph) {
        this(graph, false);
    }

    /**
     * Create a new iterator.
     *
     * @param graph
     *            the graph to iterate over.
     * @param reverse
     *            whether it should iterate backwards
     */
    public BreadthFirstIterator(final Graph<V> graph, final boolean reverse) {
        this.graph = graph;
        this.queue = new LinkedList<>();
        this.waiting = new HashMap<>();
        this.reverse = reverse;

        if (reverse) {
            initializeBackwards();
        } else {
            initializeForwards();
        }
    }

    /**
     * Initialize for forwards iteration.
     */
    private void initializeForwards() {
        for (V source : graph.getSources()) {
            queue.add(source);
        }
    }

    /**
     * Initialize for backwards iteration.
     */
    private void initializeBackwards() {
        for (V sink : graph.getSinks()) {
            queue.add(sink);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean hasNext() {
        return !queue.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final V next() {
        V vertex = queue.poll();

        if (vertex == null) {
            return null;
        }

        Set<Edge<V>> outgoingVertices;
        if (reverse) {
            outgoingVertices = graph.getIncoming(vertex);
        } else {
            outgoingVertices = graph.getOutgoing(vertex);
        }

        if (waitForVertex(vertex)) {
            return next();
        }

        for (Edge<V> edge : outgoingVertices) {
            V destination;
            if (reverse) {
                destination = graph.getSource(edge);
            } else {
                destination = graph.getDestination(edge);
            }
            queue.add(destination);
        }

        return vertex;
    }

    /**
     * Check if the iterator has to wait for a vertex.
     *
     * @param vertex
     *            the vertex to wait for
     * @return whether to wait
     */
    private boolean waitForVertex(final V vertex) {
        int inLinks;
        if (reverse) {
            inLinks = graph.getOutgoing(vertex).size();
        } else {
            inLinks = graph.getIncoming(vertex).size();
        }

        if (waiting.containsKey(vertex)) {
            int newVal = waiting.get(vertex) - 1;
            if (newVal < 1) {
                // not waiting for the vertex anymore, continue traversal
                waiting.remove(vertex);
            } else {
                waiting.put(vertex, newVal);
                return true;
            }
        } else if (inLinks > 1) {
            waiting.put(vertex, inLinks - 1);
            return true;
        }
        return false;
    }

}
