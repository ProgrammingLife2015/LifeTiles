package nl.tudelft.lifetiles.graph.models.sequence;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.scene.paint.Color;

/**
 * Helper class for calculating the color of a sequence.
 *
 * @author Joren Hammudoglu
 *
 */
public final class SequenceColor {

    /**
     * The sequence.
     */
    private Sequence sequenceVar;

    /**
     * Create a new SequenceColor object.
     *
     * @param sequence
     *            the sequence
     */
    public SequenceColor(final Sequence sequence) {
        sequenceVar = sequence;
    }

    /**
     * Get the color of the sequence based on the sequence identifier's
     * hash-code.
     *
     * @return the color of the sequence
     */
    public Color getColor() {
        byte[] hash = md5();

        final double opacity = 0.5;
        return Color.rgb(ubyteValue(hash[0]), ubyteValue(hash[1]),
                ubyteValue(hash[2]), opacity);
    }

    /**
     * Hashes a string with MD5.
     *
     * @return the bytes of the hash
     */
    private byte[] md5() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            return md.digest(sequenceVar.getIdentifier().getBytes());
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
