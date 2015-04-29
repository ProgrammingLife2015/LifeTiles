package nl.tudelft.lifeTiles.graph.JGraphT;

import nl.tudelft.lifeTiles.graph.AbstractGraphFactory;
import nl.tudelft.lifeTiles.graph.DirectedEdge;
import nl.tudelft.lifeTiles.graph.Graph;
import nl.tudelft.lifeTiles.graph.GraphParser;

/**
 * @author Rutger van den Berg
 *
 * @param <V>
 *            The type of Vertex to use.
 */
public class JGraphTGraphFactory<V> implements AbstractGraphFactory<V> {

    /**
     * @return a new empty Graph.
     */
    public final Graph<V> getGraph() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return A new edge from <code>v1</code> to <code>v2</code>
     * @param v1 The source vertex.
     * @param v2 The destination vertex.
     */
    public final DirectedEdge<V> getEdge(final V v1, final V v2) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return A new GraphParser.
     */
    public final GraphParser<V> getGraphParser() {
        // TODO Auto-generated method stub
        return null;
    }

}
