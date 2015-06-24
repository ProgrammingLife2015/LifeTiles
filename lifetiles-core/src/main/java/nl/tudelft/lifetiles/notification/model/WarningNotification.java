package nl.tudelft.lifetiles.notification.model;

import javafx.scene.paint.Color;

/**
 * A notification of a warning.
 *
 * @author Joren Hammudoglu
 *
 */
public class WarningNotification extends AbstractNotification {

    /**
     * The prefix of the message.
     */
    private static final String PREFIX = "Warning: ";

    /**
     * The priority.
     */
    private static final int PRIORITY = 20;

    /**
     * Create a new warning notification.
     *
     * @param message
     *            the warning message
     */
    public WarningNotification(final String message) {
        super(PREFIX + message);
        setDuration(Integer.MAX_VALUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color getColor() {
        return Color.ORANGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPriority() {
        return PRIORITY;
    }

}
