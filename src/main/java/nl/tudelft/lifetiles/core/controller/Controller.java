package nl.tudelft.lifetiles.core.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;

import javafx.fxml.Initializable;

/**
 * The base controller.
 *
 * @author Joren Hammudoglu
 *
 */
public abstract class Controller implements Initializable {

    /**
     * All registered controllers.
     */
    private static List<Controller> controllers = new CopyOnWriteArrayList<>();

    /**
     * The listeners for messages.
     */
    private Map<String, List<BiConsumer<Controller, Object[]>>> listeners;

    /**
     * Create a new controller and register it.
     */
    public Controller() {
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
    protected final void shout(final String message, final Object... args) {
        for (Controller c : controllers) {
            List<BiConsumer<Controller, Object[]>> listenersOther = c
                    .getListeners(message);
            if (listenersOther != null) {
                listenersOther.stream().forEach(l -> l.accept(this, args));
            }
        }
    }

    /**
     * Add a listener to a message.
     *
     * @param message
     *            the message
     * @param action
     *            the action to perform when recieving the message
     */
    protected final void listen(final String message,
            final BiConsumer<Controller, Object[]> action) {
        if (listeners.containsKey(message)) {
            listeners.get(message).add(action);
        } else {
            List<BiConsumer<Controller, Object[]>> newListeners;
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
    private List<BiConsumer<Controller, Object[]>> getListeners(
            final String message) {
        return listeners.get(message);
    }

}
