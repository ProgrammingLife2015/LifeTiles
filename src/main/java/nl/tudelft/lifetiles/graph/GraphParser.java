package nl.tudelft.lifetiles.graph;

import nl.tudelft.lifetiles.graph.sequence.SequenceSegment;

/**
 * @author Rutger van den Berg Interface for Graph Parser.
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
