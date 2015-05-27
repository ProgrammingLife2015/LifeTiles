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
     * @param b
     *            the unsigned byte
     * @return the integer value of the unsigned byte
     */
    public static int unsignedByteValue(final byte b) {
        final int mask = 0xFF;
        final int value = b & mask;
        return value;
    }

}
