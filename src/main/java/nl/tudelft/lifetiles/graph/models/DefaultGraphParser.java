package nl.tudelft.lifetiles.graph.models;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import nl.tudelft.lifetiles.graph.models.sequence.DefaultSequence;
import nl.tudelft.lifetiles.graph.models.sequence.SegmentString;
import nl.tudelft.lifetiles.graph.models.sequence.Sequence;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;

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
     * Map containing all sequences.
     */
    private Map<String, Sequence> sequences;

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
        if (!descriptor.startsWith(">")) {
            throw new IllegalArgumentException();
        }
        String[] desc = descriptor.split("\\|");
        String[] sources = desc[2].split(",");
        Set<Sequence> currentSequences = new HashSet<>();
        for (String sequencename : sources) {
            sequencename = sequencename.trim();
            if (!sequences.containsKey(sequencename)) {
                sequences.put(sequencename, new DefaultSequence(sequencename));
            }
            currentSequences.add(sequences.get(sequencename));
        }

        return new SequenceSegment(currentSequences,
                Integer.parseInt(desc[START_POS].trim()),
                Integer.parseInt(desc[END_POS].trim()), new SegmentString(
                        content.trim()));
    }

    /**
     * @param filename
     *            name of the file to parse.
     * @param graph
     *            The graph to which the edges will be added.
     */
    private void parseEdges(final String filename,
            final Graph<SequenceSegment> graph) {
        try {
            File file = new File(this.getClass()
                    .getResource("/" + filename + ".edge.graph").toURI());
            Iterator<String> it = Files.lines(file.toPath()).iterator();
            String line;
            while (it.hasNext()) {
                line = it.next();
                String[] edge = line.split(" ");
                graph.addEdge(Integer.parseInt(edge[0]),
                        Integer.parseInt(edge[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param filename
     *            The basename of the file to parse.
     * @param gfact
     *            The graph factory to use to produce the graph.
     * @return a new graph containing the parsed information.
     */
    @Override
    public final Graph<SequenceSegment> parseFile(final String filename,
            final GraphFactory<SequenceSegment> gfact) {
        Graph<SequenceSegment> graph = gfact.getGraph();
        parseVertices(filename, graph);
        parseEdges(filename, graph);
        return graph;
    }

    /**
     * @param filename
     *            name of the file to parse.
     * @param graph
     *            The graph to which the edges will be added.
     */
    private void parseVertices(final String filename,
            final Graph<SequenceSegment> graph) {
        try {
            File file = new File(this.getClass()
                    .getResource("/" + filename + ".node.graph").toURI());
            Iterator<String> it = Files.lines(file.toPath()).iterator();
            while (it.hasNext()) {
                graph.addVertex(createSegment(it.next(), it.next()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
