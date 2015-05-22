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
     * The number of seconds to display the notification.
     */
    private int duration;

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
    public final int getDuration() {
        return duration;
    }

    /**
     * Set the notification duration time in seconds.
     *
     * @param seconds
     *            the duration time.
     */
    public final void setDuration(final int seconds) {
        this.duration = seconds;
    }

    /**
     * Get the color of this notification.
     *
     * @return the color
     */
    public abstract Color getColor();

    /**
     * Get the priority of this notification (smaller priority is higher). Used
     * to determine the order in which to display the notifications.
     *
     * @return the priority.
     */
    public abstract int getPriority();

}
