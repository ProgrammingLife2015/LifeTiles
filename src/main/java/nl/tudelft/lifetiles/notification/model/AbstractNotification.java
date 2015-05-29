package nl.tudelft.lifetiles.notification.model;

import javafx.scene.paint.Color;

/**
 * A notification containing a message.
 *
 * @author Joren Hammudoglu
 *
 */
public abstract class AbstractNotification {

    /**
     * The message.
     */
    private final String message;

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
    AbstractNotification(final String message) {
        this.message = message;
    }

    /**
     * Get the notification message.
     *
     * @return the message
     */
    public final String getMessage() {
        return message;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1 + prime;
        if (message == null) {
            result += 0;
        } else {
            result += message.hashCode();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractNotification)) {
            return false;
        }
        AbstractNotification other = (AbstractNotification) obj;
        if (message == null) {
            if (other.message != null) {
                return false;
            }
        } else if (!message.equals(other.message)) {
            return false;
        }
        return true;
    }

}
