package nl.tudelft.lifetiles.notification.model;

/**
 * A factory for notifications.
 *
 * @author Joren Hammudoglu
 *
 */
public class NotificationFactory {

    /**
     * Notification error type.
     */
    public static final String ERROR = "error";

    /**
     * Notification warning type.
     */
    public static final String WARNING = "warning";

    /**
     * Notification info type.
     */
    public static final String INFO = "info";

    /**
     * Create a new notification factory.
     */
    public NotificationFactory() {
        // noop
    }

    /**
     * Create a notification.
     *
     * @param message
     *            the error message
     * @param type
     *            the notification type
     * @return the notification
     */
    public final Notification getNotification(final String message,
            final String type) {
        Notification res;

        switch (type) {
        case ERROR:
            res = new ErrorNotification(message);
            break;
        case WARNING:
            res = new WarningNotification(message);
            break;
        case INFO:
            res = new InfoNotification(message);
            break;
        default:
            throw new IllegalArgumentException("unknown notification type: "
                    + type);
        }

        return res;
    }

    /**
     * Create a notification.
     *
     * @param e
     *            the exception to create the notification from
     * @return the notification
     */
    public final Notification getNotification(final Exception e) {
        return getNotification(e.getLocalizedMessage(), ERROR);
    }

}
