package nl.tudelft.lifetiles.core.util;

/**
 * A message, used as shout identifier.
 *
 * @author joren
 * @author Rutger van den Berg
 *
 */
public final class Message {

    /**
     * A shout message indicating data has been filtered.
     */
    public static final Message FILTERED = create("filtered");
    /**
     * A shout message indicating data has been opened.
     */
    public static final Message OPENED = create("opened");
    /**
     * A shout message indicating data has been loaded.
     */
    public static final Message LOADED = create("loaded");
    /**
     * A shout message indicating that the filters have been reset.
     */
    public static final Message RESET = create("reset");

    /**
     * The value.
     */
    private final String value;

    /**
     * Create a new message.
     *
     * @param message
     *            the value of the message
     */
    private Message(final String message) {
        value = message;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Factory method.
     *
     * @param message
     *            the message string
     * @return the Message.
     */
    public static Message create(final String message) {
        return new Message(message);
    }

}