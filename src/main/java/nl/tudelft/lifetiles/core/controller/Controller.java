package nl.tudelft.lifetiles.core.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
    private Map<String, BiConsumer<Controller, Object[]>> listeners;

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
            for (Entry<String, BiConsumer<Controller, Object[]>> entry : c
                    .getListeners().entrySet()) {
                if (entry.getKey().equals(message)) {
                    entry.getValue().accept(this, args);
                }
            }
        }
    }

    /**
     * Listen for a shouted message.
     *
     * @param message
     *            the message
     * @param action
     *            the action to perform when recieving the message
     */
    protected final void listen(final String message,
            final BiConsumer<Controller, Object[]> action) {
        listeners.put(message, action);
    }

    /**
     * Retrieve all shout listeners.
     *
     * @return a map of listeners for messages
     */
    private Map<String, BiConsumer<Controller, Object[]>> getListeners() {
        return listeners;
    }

}
