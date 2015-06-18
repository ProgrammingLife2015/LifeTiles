package nl.tudelft.lifetiles.core.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The logging utility class, manages the LOGGER.
 *
 * @author Joren Hammudoglu
 *
 */
public final class Logging {

    /**
     * The file to log to.
     */
    private static final String LOG_FILE = "lifetiles.log";

    /**
     * The logger instance.
     */
    private static final Logger LOGGER = Logger.getLogger(Logging.class
            .getName());

    /**
     * The file handler.
     */
    private static FileHandler filehandler;

    /**
     * Uninstantiable.
     */
    private Logging() {
        // noop
    }

    /**
     * Initialize the LOGGER.
     */
    static {
        try {
            filehandler = new FileHandler(LOG_FILE, false);
        } catch (SecurityException | IOException e) {
            exception(e);
        }

        getLogger().addHandler(filehandler);
        setLevel(Level.INFO);
    }

    /**
     * Set the logging level.
     *
     * @param level
     *            The new level.
     */
    public static void setLevel(final Level level) {
        getLogger().setLevel(level);
    }

    /**
     * Log an info message.
     *
     * @param message
     *            the log message
     */
    public static void info(final String message) {
        if (getLogger().isLoggable(Level.INFO)) {
            getLogger().info(message);
        }

    }

    /**
     * Log a warning.
     *
     * @param message
     *            the log message
     */
    public static void warning(final String message) {
        if (getLogger().isLoggable(Level.WARNING)) {
            getLogger().warning(message);
        }
    }

    /**
     * Log an error.
     *
     * @param message
     *            the log message
     */
    public static void severe(final String message) {
        if (getLogger().isLoggable(Level.SEVERE)) {
            getLogger().severe(message);
        }
    }

    /**
     * Log an exception.
     *
     * @param exception
     *            the log message
     */
    public static void exception(final Exception exception) {
        getLogger().log(Level.SEVERE, "An exception was caught.", exception);
    }

    /**
     * Get the logger instance.
     *
     * @return the Logger.
     */
    public static Logger getLogger() {
        return LOGGER;
    }

}
