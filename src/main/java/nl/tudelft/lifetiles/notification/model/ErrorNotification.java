package nl.tudelft.lifetiles.notification.model;

import javafx.scene.paint.Color;

/**
 * A notification of an error.
 *
 * @author Joren Hammudoglu
 *
 */
public class ErrorNotification extends Notification {

    /**
     * The prefix of the message.
     */
    private static final String PREFIX = "Error: ";

    /**
     * Create a new error notification.
     *
     * @param message
     *            the error message
     */
    public ErrorNotification(final String message) {
        super(PREFIX + message);
        setDuration(Integer.MAX_VALUE);
    }

    @Override
    public final Color getColor() {
        return Color.ORANGERED;
    }

    @Override
    public final int getPriority() {
        final int priority = 10;
        return priority;
    }

}
