package nl.tudelft.lifetiles.graph.model.jgrapht;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import nl.tudelft.lifetiles.core.util.Logging;
import nl.tudelft.lifetiles.graph.model.Edge;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.graph.model.GraphFactory;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedSubgraph;

/**
 * @author Rutger van den Berg
 *
 * @param <V>
 *            The type of Vertex to use.
 */
public class JGraphTGraphFactory<V extends Comparable<V> & Cloneable>
        implements GraphFactory<V> {
    /**
     * The edgefactory associated with this graph factory.
     */
    private final JGraphTEdgeFactory<V> edgeFact;

    /**
     * Create a new graph factory.
     */
    public JGraphTGraphFactory() {
        edgeFact = new JGraphTEdgeFactory<>();
    }

    /**
     * @return a new empty Graph.
     */
    @Override
    public final Graph<V> getGraph() {
        return new JGraphTGraphAdapter<V>(edgeFact);
    }

    /**
     * @param base
     *            the graph to create a subgraph from.
     * @param vertexSubSet
     *            the vertices to include in the subgraph
     * @return a subgraph based on the base graph
     * @throws NotAJGraphTAdapterException
     *             if the base graph is not a JGraphT library
     */
    @Override
    public final Graph<V> getSubGraph(final Graph<V> base,
            final Set<V> vertexSubSet) throws NotAJGraphTAdapterException {

        if (base instanceof JGraphTGraphAdapter) {
            JGraphTGraphAdapter<V> baseGraph = (JGraphTGraphAdapter<V>) base;

            return new JGraphTGraphAdapter<V>(
                    new DirectedSubgraph<V, DefaultEdge>(baseGraph
                            .getInternalGraph(), vertexSubSet, null), edgeFact,
                    baseGraph.getVertexIdentifiers());

        } else {
            throw new NotAJGraphTAdapterException();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Graph<V> deepcopy(final Graph<V> graph) {
        Graph<V> copygraph = new JGraphTGraphAdapter<V>(edgeFact);

        Map<Object, Object> convertVertices = new HashMap<Object, Object>();

        for (V vertex : graph.getAllVertices()) {
            try {
                Method method = vertex.getClass().getMethod("clone");
                Object copy = method.invoke(vertex);
                copygraph.addVertex((V) copy);
                convertVertices.put(vertex, copy);
            } catch (NoSuchMethodException | SecurityException
                    | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException exception) {

                Logging.exception(exception);
            }
        }

        for (Edge<V> edge : graph.getAllEdges()) {
            Object from = convertVertices.get(graph.getSource(edge));
            Object destination = convertVertices
                    .get(graph.getDestination(edge));

            copygraph.addEdge((V) from, (V) destination);
        }

        return copygraph;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Graph<V> copy(final Graph<V> graph) {
        Graph<V> copyGraph = new JGraphTGraphAdapter<V>(edgeFact);
        for (V vertex : graph.getAllVertices()) {
            copyGraph.addVertex(vertex);
        }
        for (Edge<V> edge : graph.getAllEdges()) {
            copyGraph
            .addEdge(graph.getSource(edge), graph.getDestination(edge));
        }
        return copyGraph;
    }

}
