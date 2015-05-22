package nl.tudelft.lifetiles.notification.model;

import javafx.scene.paint.Color;

/**
 * A notification containing a message.
 *
 * @author Joren Hammudoglu
 *
 */
public abstract class Notification {

    /**
     * The message.
     */
    private String messageVar;

    /**
     * Create a new notification.
     *
     * @param message
     *            the message
     */
    Notification(final String message) {
        this.messageVar = message;
    }

    /**
     * Get the notification message.
     *
     * @return the message
     */
    public final String getMessage() {
        return messageVar;
    }

    /**
     * The number of seconds to display the notification.
     *
     * @return the seconds
     */
    public final int displayDuration() {
        final int seconds = 3;
        return seconds;
    }

    /**
     * Get the color of this notification.
     *
     * @return the color
     */
    public abstract Color getColor();

}
