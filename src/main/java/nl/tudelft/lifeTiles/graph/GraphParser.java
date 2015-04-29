package nl.tudelft.lifeTiles.graph;

/**
 * @author Rutger van den Berg
 * Interface for Graph Parser.
 * @param <V> the type to use as Vertices.
 */
public interface GraphParser<V> {
    /**
     * @param filename The filename for the graph to parse.
     * @param graph The Graph in which to store the parsed graph.
     */
    void parseFile(final String filename, Graph<V> graph);
}
