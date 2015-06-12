package nl.tudelft.lifetiles.notification.model;

import javafx.scene.paint.Color;

/**
 * An info notification.
 *
 * @author Joren Hammudoglu
 *
 */
public class InfoNotification extends AbstractNotification {

    /**
     * The prefix of the message.
     */
    private static final String PREFIX = "Info: ";

    /**
     * The priority.
     */
    private static final int PRIORITY = 30;

    /**
     * Create a new info notification.
     *
     * @param message
     *            the info message
     */
    public InfoNotification(final String message) {
        super(PREFIX + message);
        final int defaultDuration = 5;
        setDuration(defaultDuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color getColor() {
        return Color.AQUA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPriority() {
        return PRIORITY;
    }

}
