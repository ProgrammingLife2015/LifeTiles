package nl.tudelft.lifetiles.core.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class SettingsTest {

    @Test
    public void testGet() {
        final String key = "LT_TEST_1";
        final String value = "true";
        Settings.set(key, value);
        assertEquals(Settings.get(key), value);
    }

    @Test
    public void testGetBoolean() {
        final String key = "LT_TEST_2";
        final String value = "false";
        Settings.set(key, value);
        assertFalse(Settings.getBoolean(key));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testGetException() {
        Settings.get("LT_TEST_3");
    }

}
