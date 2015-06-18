package nl.tudelft.lifetiles.core.controller;

/**
 * Function interface for shouts.
 *
 * @author Rutger van den Berg
 *
 */
@FunctionalInterface
public interface ShoutCallback {
    /**
     * @param sender
     *            The sending controller.
     * @param subject
     *            The subject of the message.
     * @param args
     *            The optional arguments.
     */
    void accept(AbstractController sender, String subject, Object... args);
}
