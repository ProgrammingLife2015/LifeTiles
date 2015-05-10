package nl.tudelft.lifetiles.graph.models;

import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;


/**
 * Interface for Graph Parser.
 *
 * @author Rutger van den Berg
 */
public interface GraphParser {
    /**
     * @param filename
     *            The basename for the graph to parse.
     * @param gfact
     *            The graph factory to use to produce the graph.
     * @return A new graph containing the parsed vertices and edges.
     */
    Graph<SequenceSegment> parseFile(final String filename,
            GraphFactory<SequenceSegment> gfact);
}
