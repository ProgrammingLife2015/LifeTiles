package nl.tudelft.lifetiles.sequence;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.scene.paint.Color;
import nl.tudelft.lifetiles.core.util.Logging;
import nl.tudelft.lifetiles.core.util.TypeUtils;
import nl.tudelft.lifetiles.sequence.model.Sequence;

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
        return Color.rgb(TypeUtils.unsignedByteValue(hash[0]),
                TypeUtils.unsignedByteValue(hash[1]),
                TypeUtils.unsignedByteValue(hash[2]), opacity);
    }

    /**
     * Hashes a string with MD5.
     *
     * @param sequence
     *            the sequence
     * @return the bytes of the hash
     */
    private static byte[] md5(final Sequence sequence) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            return digest.digest(sequence.getIdentifier().getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            Logging.exception(e);
            return new byte[] {};
        }
    }

}
