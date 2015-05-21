package nl.tudelft.lifetiles.notification.model;

import javafx.scene.paint.Color;

/**
 * A notification of a warning.
 *
 * @author Joren Hammudoglu
 *
 */
public class WarningNotification extends Notification {

    /**
     * The prefix of the message.
     */
    private static final String PREFIX = "Warning: ";

    /**
     * Create a new warning notification.
     *
     * @param message
     *            the warning message
     */
    public WarningNotification(final String message) {
        super(PREFIX + message);
    }

    @Override
    public final Color getColor() {
        return Color.ORANGE;
    }

}
