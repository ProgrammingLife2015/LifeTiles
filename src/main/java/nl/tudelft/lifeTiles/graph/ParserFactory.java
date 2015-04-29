package nl.tudelft.lifeTiles.graph;

/**
 * Interface for ParserFactory. Implementations will parse various files into a
 * graph.
 *
 * @author Rutger van den Berg
 */
public interface ParserFactory {
    /**
     * @param fa
     *            A <code>AbstractGraphLibrary</code> to generate the graph.
     * @return A new graph using the specified graph library.
     */
    Graph<SequenceSegment> parseGraph(AbstractGraphFactory<SequenceSegment> fa);
}
