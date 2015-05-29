package nl.tudelft.lifetiles.graph.model;

import nl.tudelft.lifetiles.graph.model.jgrapht.JGraphTGraphFactory;

/**
 * Produces the various factories used.
 *
 * @author Rutger van den Berg
 * @param <V>
 *            The type of vertex to use.
 */
public final class FactoryProducer<V extends Comparable<V>> {
    /**
     * The graph library to use when none is specified.
     */
    private static final String DEFAULT_LIBRARY = "JGraphT";

    /**
     * Do not instantiate.
     */
    private FactoryProducer() {

    }

    /**
     * Produce a new GraphFactory using the default library.
     *
     * @param <V>
     *            the type of vertex the new factory should use.
     * @return A new factory using the default library.
     */
    public static <V extends Comparable<V>> GraphFactory<V> getFactory() {
        return getFactory(DEFAULT_LIBRARY);
    }

    /**
     * @param graphLibrary
     *            The graph library to use.
     * @param <V>
     *            the type of vertex the new factory should use.
     * @return A new factory of the desired type.
     */
    public static <V extends Comparable<V>> GraphFactory<V> getFactory(
            final String graphLibrary) {
        if ("JGraphT".equalsIgnoreCase(graphLibrary)) {
            return new JGraphTGraphFactory<V>();
        }
        // Specified an invalid Graph library.
        throw new IllegalArgumentException();
    }

}
