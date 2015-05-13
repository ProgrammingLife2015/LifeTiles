package nl.tudelft.lifetiles.graph.models;

import nl.tudelft.lifetiles.graph.models.jgrapht.JGraphTGraphFactory;

/**
 * Produces the various factories used.
 *
 * @author Rutger van den Berg
 * @param <V>
 *            The type of vertex to use.
 */
public class FactoryProducer<V> {
    /**
     * The graph library to use when none is specified.
     */
    private static final String DEFAULT_LIBRARY = "JGraphT";

    /**
     * Produce a new GraphFactory using the
     * {@value FactoryProducer#DEFAULT_LIBRARY} library.
     *
     * @return A new factory using {@value FactoryProducer#DEFAULT_LIBRARY}.
     */
    public final GraphFactory<V> getFactory() {
        return getFactory(DEFAULT_LIBRARY);
    }

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
        throw new IllegalArgumentException();
    }

}
