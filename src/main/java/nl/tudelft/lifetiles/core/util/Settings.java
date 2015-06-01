package nl.tudelft.lifetiles.core.util;

import java.io.IOException;
import java.util.Properties;

/**
 * The settings utility class.
 *
 * @author Joren Hammudoglu
 *
 */
public final class Settings {

    /**
     * The resource file for the properties.
     */
    private static final String RESOURCE = "settings/lifetiles.conf";

    /**
     * The properties.
     */
    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            PROPERTIES.load(Settings.class.getClassLoader()
                    .getResourceAsStream(RESOURCE));
        } catch (IOException e) {
            Logging.exception(e);
            System.exit(0);
        }
    }

    /**
     * Uninitializable.
     */
    private Settings() {
        // noop
    }

    /**
     * Get a setting.
     *
     * @param setting
     *            the setting key.
     * @return the setting value
     */
    public static String get(final String setting) {
        final String property = PROPERTIES.getProperty(setting);
        if (property == null) {
            throw new IllegalArgumentException("Setting " + setting
                    + " not defined.");
        }
        return property;
    }

    /**
     * Get a boolean setting.
     *
     * @param setting
     *            the setting key.
     * @return the setting value
     */
    public static boolean getBoolean(final String setting) {
        return Boolean.valueOf(get(setting));
    }

}
