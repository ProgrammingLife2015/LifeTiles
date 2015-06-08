package nl.tudelft.lifetiles.core.util;

/**
 * Utility functions for primitive types.
 *
 * @author Joren Hammudoglu
 */
public final class TypeUtils {

    /**
     * Uninstantiable class.
     */
    private TypeUtils() {
        // noop
    }

    /**
     * Returns the value of an unsigned byte.
     *
     * @param input
     *            the unsigned byte
     * @return the integer value of the unsigned byte
     */
    public static int unsignedByteValue(final byte input) {
        final int mask = 0xFF;
        return input & mask;
    }

}
