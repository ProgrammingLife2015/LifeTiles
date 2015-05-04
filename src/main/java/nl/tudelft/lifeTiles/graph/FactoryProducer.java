package nl.tudelft.lifetiles.graph;

import nl.tudelft.lifetiles.graph.jgrapht.JGraphTGraphFactory;

/**
 * @author Rutger van den Berg Produces the various factories used.
 * @param <V>
 *            The type of vertex to use.
 */
public class FactoryProducer<V> {
    /**
     * The graph library to use when none is specified.
     */
    private static final String DEFAULT_LIBRARY = "JGraphT";

    /**
     * @param graphLibrary
     *            The graph library to use.
     * @return A new factory of the desired type.
     */
    public final GraphFactory<V> getFactory(final String graphLibrary) {
        if (graphLibrary.equalsIgnoreCase("JGraphT")) {
            return new JGraphTGraphFactory<V>();
        }
        // Specified an invalid Graph library.
        return null;
    }

    /**
     * Produce a new GraphFactory using the
     * {@value FactoryProducer#DEFAULT_LIBRARY} library.
     * 
     * @return A new factory using {@value FactoryProducer#DEFAULT_LIBRARY}.
     */
    public final GraphFactory<V> getFactory() {
        return getFactory(DEFAULT_LIBRARY);
    }

}
