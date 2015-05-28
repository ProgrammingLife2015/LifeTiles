package nl.tudelft.lifetiles.graph.model.jgrapht;

import java.util.Set;

import nl.tudelft.lifetiles.graph.model.Graph;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedSubgraph;

/**
 *
 * @author AC Langerak
 *
 * @param <V>
 *            type of vertex to use
 */

public class JGraphTSubGraphAdapter<V extends Comparable<V> & Cloneable>
extends JGraphTGraphAdapter<V> {

    /**
     * Create a SubGraph based on the base graph.
     *
     * @param base
     *            graph to make a subgraph from
     * @param vertexSubSet
     *            set of vertices to include in the subgraph
     * @param ef
     *            The edgefactory to use for this graph
     */
    public JGraphTSubGraphAdapter(final Graph<V> base,
            final Set<V> vertexSubSet, final JGraphTEdgeFactory<V> ef) {
        super(ef);

        setInternalGraph(new DirectedSubgraph<V, DefaultEdge>(base
                .getInternalGraph(), vertexSubSet, null));

    }

}
