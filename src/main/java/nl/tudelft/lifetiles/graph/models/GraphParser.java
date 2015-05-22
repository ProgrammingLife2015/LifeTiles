package nl.tudelft.lifetiles.graph.models;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import nl.tudelft.lifetiles.graph.models.sequence.Sequence;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;

/**
 * Interface for Graph Parser.
 *
 * @author Rutger van den Berg
 */
public interface GraphParser {
    /**
     * @param vertexfile
     *            The file to retrieve vertices from.
     * @param edgefile
     *            The file to retrieve edges from.
     * @param gfact
     *            The graph factory to use to produce the graph.
     * @return A new graph containing the parsed vertices and edges.
     * @throws IOException
     *             When there is an error reading one of the specified files.
     */
    Graph<SequenceSegment> parseGraph(final File vertexfile,
            final File edgefile, GraphFactory<SequenceSegment> gfact)
            throws IOException;

    /**
     * @return A mapping of sequencenames to sequences.
     */
    Map<String, Sequence> getSequences();
}
