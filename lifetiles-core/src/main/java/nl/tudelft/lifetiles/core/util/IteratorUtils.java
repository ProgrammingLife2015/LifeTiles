package nl.tudelft.lifetiles.core.util;

import java.util.Iterator;

/**
 * Utilities for iterators and iterables.
 *
 * @author Joren Hammudoglu
 */
public final class IteratorUtils {

    /**
     * Uninstantiable.
     */
    private IteratorUtils() {
        // noop
    }

    /**
     * Create an {@link Iterable} from an {@link Iterator}.
     *
     * @param iterator
     *            the iterator
     * @param <T>
     *            the type
     * @return the iterable
     */
    public static <T> Iterable<T> toIterable(final Iterator<T> iterator) {
        return () -> iterator;
    }

}
