package nl.tudelft.lifetiles.graph.view;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.scene.paint.Color;
import nl.tudelft.lifetiles.graph.model.sequence.Sequence;

/**
 * Helper class for calculating the color of a sequence.
 *
 * @author Joren Hammudoglu
 *
 */
public final class SequenceColor {

    /**
     * Uninstantiable class.
     */
    private SequenceColor() {
        // noop
    }

    /**
     * Get the color of the sequence based on the sequence identifier's
     * hash-code.
     *
     * @param sequence
     *            the sequence
     * @return the color of the sequence
     */
    public static Color getColor(final Sequence sequence) {
        byte[] hash = md5(sequence);

        final double opacity = 0.5;
        return Color.rgb(ubyteValue(hash[0]), ubyteValue(hash[1]),
                ubyteValue(hash[2]), opacity);
    }

    /**
     * Hashes a string with MD5.
     *
     * @param sequence
     *            the sequence
     * @return the bytes of the hash
     */
    private static byte[] md5(final Sequence sequence) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            return md.digest(sequence.getIdentifier().getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[] {};
        }
    }

    /**
     * Returns the value of an unsigned byte.
     *
     * @param b
     *            the unsigned byte
     * @return the integer value of the unsigned byte
     */
    private static int ubyteValue(final byte b) {
        final int mask = 0xFF;
        final int value = b & mask;
        return value;
    }
}
