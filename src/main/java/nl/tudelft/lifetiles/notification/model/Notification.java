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
     * Get the color of this notification.
     *
     * @return the color
     */
    public abstract Color getColor();

}
