package nl.tudelft.lifetiles.core.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.logging.Level;

import javafx.fxml.Initializable;
import nl.tudelft.lifetiles.core.util.Logging;
import nl.tudelft.lifetiles.core.util.Message;

/**
 * The base controller.
 *
 * @author Joren Hammudoglu
 *
 */
public abstract class AbstractController implements Initializable {

    /**
     * All registered controllers.
     */
    private static List<AbstractController> controllers = new CopyOnWriteArrayList<>();

    /**
     * The listeners for messages.
     */
    private final Map<Message, List<BiConsumer<AbstractController, Object[]>>> listeners;

    /**
     * Create a new controller and register it.
     */
    public AbstractController() {
        listeners = new HashMap<>();
        controllers.add(this);
    }

    /**
     * Inform all controllers of a message.
     *
     * @param message
     *            the message
     * @param args
     *            the arguments of the message
     */
    protected final void shout(final Message message, final Object... args) {
        for (AbstractController c : controllers) {
            List<BiConsumer<AbstractController, Object[]>> listenersOther = c
                    .getListeners(message);
            if (listenersOther != null) {
                listenersOther.stream().forEach(
                        listener -> listener.accept(this, args));
            }
        }

        Logging.getLogger().log(ShoutLevel.SHOUT, message.getValue(), args);
    }

    /**
     * Add a listener to a message.
     *
     * @param message
     *            the message
     * @param action
     *            the action to perform when recieving the message
     */
    protected final void listen(final Message message,
            final BiConsumer<AbstractController, Object[]> action) {
        if (listeners.containsKey(message)) {
            listeners.get(message).add(action);
        } else {
            List<BiConsumer<AbstractController, Object[]>> newListeners;
            newListeners = new ArrayList<>();
            newListeners.add(action);
            listeners.put(message, newListeners);
        }
    }

    /**
     * Retrieve all shout listeners for a message.
     *
     * @param message
     *            the message
     * @return a map of listeners for messages
     */
    private List<BiConsumer<AbstractController, Object[]>> getListeners(
            final Message message) {
        return listeners.get(message);
    }

}

/**
 * The custom shout level. Needed because the constructor of {@link Level} is
 * not public.
 *
 * @author Joren Hammudoglu
 *
 */
class ShoutLevel extends Level {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = -8731795030477848078L;

    /**
     * The shout level.
     */
    public static final Level SHOUT = new ShoutLevel("SHOUT",
            Level.INFO.intValue() + 1);

    /**
     * Create a new shout level.
     *
     * @param name
     *            the level name
     * @param value
     *            the value
     */
    public ShoutLevel(final String name, final int value) {
        super(name, value);
    }
}
