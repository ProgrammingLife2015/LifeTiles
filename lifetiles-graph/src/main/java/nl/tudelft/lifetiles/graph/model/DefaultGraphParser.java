package nl.tudelft.lifetiles.graph.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import nl.tudelft.lifetiles.core.util.Timer;
import nl.tudelft.lifetiles.sequence.model.DefaultSequence;
import nl.tudelft.lifetiles.sequence.model.SegmentString;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

/**
 * @author Rutger van den Berg
 *
 */
public class DefaultGraphParser implements GraphParser {
    /**
     * Index of end position in the vertex descriptor.
     */
    private static final int END_POS = 3;
    /**
     * Index of starting position in the vertex descriptor.
     */
    private static final int START_POS = 2;

    /**
     * Index of sources in the vertex descriptor.
     */
    private static final int SOURCES_POS = 1;

    /**
     * Map containing all sequences.
     */
    private final Map<String, Sequence> sequences;

    /**
     * Creates a new graph parser.
     */
    public DefaultGraphParser() {
        sequences = new HashMap<>();
    }

    /**
     * @param descriptor
     *            Description line in the vertex file.
     * @param content
     *            Content line in the vertex file.
     * @return a new SequenceSegment
     */
    private SequenceSegment createSegment(final String descriptor,
            final String content) {
        if (descriptor.charAt(0) != '>') {
            throw new IllegalArgumentException();
        }
        String[] desc = descriptor.split("\\|");
        String[] sources = desc[SOURCES_POS].split(",");
        Set<Sequence> currentSequences = new HashSet<>();
        for (String sequencename : sources) {
            sequencename = sequencename.trim();
            if (!sequences.containsKey(sequencename)) {
                sequences.put(sequencename, new DefaultSequence(sequencename));
            }
            currentSequences.add(sequences.get(sequencename));
        }
        SequenceSegment segment = new SequenceSegment(currentSequences,
                Integer.parseInt(desc[START_POS].trim()),
                Integer.parseInt(desc[END_POS].trim()), new SegmentString(
                        content.trim()));

        for (Sequence s : currentSequences) {
            s.appendSegment(segment);
        }

        return segment;
    }

    /**
     * @return A map of sequences.
     */
    @Override
    public final Map<String, Sequence> getSequences() {
        if (sequences.isEmpty()) {
            throw new UnsupportedOperationException("Graph not parsed yet.");
        }
        return sequences;
    }

    /**
     * @param edgefile
     *            The file to parse
     * @param graph
     *            The graph to which the edges will be added.
     * @throws IOException
     *             When there is an error reading the specified file.
     */
    private void parseEdges(final File edgefile,
            final Graph<SequenceSegment> graph) throws IOException {

        Iterator<String> iterator = Files.lines(edgefile.toPath()).iterator();
        String line;
        while (iterator.hasNext()) {
            line = iterator.next();
            String[] edge = line.split(" ");
            graph.addEdge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]));
        }
    }

    /**
     * @param filename
     *            The basename of the file to parse.
     * @param gfact
     *            The graph factory to use to produce the graph.
     * @return a new graph containing the parsed information.
     * @throws IOException
     *             when there is an error while reading the file.
     * @throws IllegalArgumentException
     *             when one of the files does not exist or cannot be read.
     *
     */
    @Override
    public final Graph<SequenceSegment> parseGraph(final File vertexfile,
            final File edgefile, final GraphFactory<SequenceSegment> gfact)
            throws IOException {
        Timer timer = Timer.getAndStart();

        Graph<SequenceSegment> graph = gfact.getGraph();
        parseVertices(vertexfile, graph);
        parseEdges(edgefile, graph);

        timer.stopAndLog("Graph parsing");

        return graph;
    }

    /**
     * @param vertexfile
     *            The file to parse.
     * @param graph
     *            The graph to which the edges will be added.
     * @throws IOException
     *             When there is an error reading the file.
     */
    private void parseVertices(final File vertexfile,
            final Graph<SequenceSegment> graph) throws IOException {

        Iterator<String> iterator = Files.lines(vertexfile.toPath()).iterator();
        while (iterator.hasNext()) {
            graph.addVertex(createSegment(iterator.next(), iterator.next()));
        }

    }
}
