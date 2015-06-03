package nl.tudelft.lifetiles.core.util;


/**
 * A message, used as shout identifier.
 *
 * @author joren
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
     * A shout message indicating annotations have been inserted.
     */
    public static final Message ANNOTATIONS = create("annotations");

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
