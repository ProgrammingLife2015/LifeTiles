package nl.tudelft.lifeTiles.graph;

/**
 * @author Rutger van den Berg
 * Interface for Graph Parser.
 * @param <V> the type to use as Vertices.
 */
public interface GraphParser<V> {
    /**
     * @param filename The basename for the graph to parse.
     * @param gfact The graph factory to use to produce the graph.
     */
    void parseFile(final String filename, GraphFactory<V> gfact);
}
