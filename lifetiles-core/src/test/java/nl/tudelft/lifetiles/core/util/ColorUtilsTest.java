package nl.tudelft.lifetiles.core.util;

import static org.junit.Assert.assertEquals;
import javafx.scene.paint.Color;

import org.junit.Test;

public class ColorUtilsTest {

    private Color white = Color.WHITE;
    private Color black = Color.BLACK;


    @Test
    public void testRgbaFormat() {
        final String expected = "255,255,255,1.000000";
        assertEquals(expected, ColorUtils.rgbaFormat(white));
    }

    @Test
    public void testWebCode() {
        final String expected = "#000000";
        assertEquals(expected, ColorUtils.webCode(black));
    }

}
