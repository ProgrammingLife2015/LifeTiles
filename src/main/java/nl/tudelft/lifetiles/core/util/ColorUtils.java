package nl.tudelft.lifetiles.core.util;

import java.util.Locale;

import javafx.scene.paint.Color;

/**
 * Utility functions for Color.
 *
 * @author Joren Hammudoglu
 *
 */
public final class ColorUtils {

    /**
     * Uninitializable.
     */
    private ColorUtils() {
        // noop
    }

    /**
     * Format the color into r,g,b,a format.
     *
     * @param color
     *            the color
     * @return the web color code of the color
     */
    public static String rgbaFormat(final Color color) {
        final int colorRange = 255;
        return String.format(Locale.ENGLISH, "%d,%d,%d,%f",
                (int) (color.getRed() * colorRange),
                (int) (color.getGreen() * colorRange),
                (int) (color.getBlue() * colorRange), color.getOpacity());
    }

}
