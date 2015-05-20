package nl.tudelft.lifetiles.traverser.models;

import nl.tudelft.lifetiles.graph.models.Graph;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;

/**
 * Interface which is used to produce traversers with the traverserFactory.
 * Interface defines the traverseGraph method.
 *
 * @author Jos
 *
 */
public interface Traverser {

    /**
     * Traverser the graph.
     *
     * @param graph
     *            Graph to be traversed.
     * @return Traversed graph.
     */
    Graph<SequenceSegment> traverseGraph(Graph<SequenceSegment> graph);
}
