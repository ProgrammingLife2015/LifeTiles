package nl.tudelft.lifetiles.graph.model.jgrapht;

/**
 * Exception when JGraphtAdapter was expected, but the class was not a
 * JGraphtAdapter.
 *
 * @author AC Langerak
 *
 */
public class NotAJGraphTAdapterException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -4003651909659005253L;

    /**
     *
     */
    public NotAJGraphTAdapterException() {
        super();
    }

    /**
     * Expection with a message.
     *
     * @param message
     *            message
     */
    public NotAJGraphTAdapterException(final String message) {
        super(message);
    }

    /**
     * Expection with a message and what caused it.
     *
     * @param message
     *            message
     * @param cause
     *            cause
     */
    public NotAJGraphTAdapterException(final String message,
            final Throwable cause) {
        super(message, cause);
    }

    /**
     * Expection with what caused it.
     *
     * @param cause
     *            cause
     */
    public NotAJGraphTAdapterException(final Throwable cause) {
        super(cause);
    }
}
