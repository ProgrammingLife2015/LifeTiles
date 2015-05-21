package nl.tudelft.lifetiles.notification.model;

/**
 * A factory for notifications.
 *
 * @author Joren Hammudoglu
 *
 */
public class NotificationFactory {

    /**
     * Create a new notification factory.
     */
    public NotificationFactory() {
        // noop
    }

    /**
     * Create an error notification.
     *
     * @param message
     *            the error message
     * @return the notification
     */
    public final Notification error(final String message) {
        return new ErrorNotification(message);
    }

    /**
     * Create a warning notification.
     *
     * @param message
     *            the warning message
     * @return the notification
     */
    public final Notification warning(final String message) {
        return new WarningNotification(message);
    }

    /**
     * Create an info notification.
     *
     * @param message
     *            the info message
     * @return the notification
     */
    public final Notification info(final String message) {
        return new InfoNotification(message);
    }

}
