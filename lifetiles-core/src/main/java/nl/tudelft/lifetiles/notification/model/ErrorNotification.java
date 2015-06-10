package nl.tudelft.lifetiles.notification.model;

import javafx.scene.paint.Color;

/**
 * A notification of an error.
 *
 * @author Joren Hammudoglu
 *
 */
public class ErrorNotification extends AbstractNotification {

    /**
     * The prefix of the message.
     */
    private static final String PREFIX = "Error: ";

    /**
     * The priority.
     */
    private static final int PRIORITY = 10;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public final Color getColor() {
        return Color.ORANGERED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getPriority() {
        return PRIORITY;
    }

}
