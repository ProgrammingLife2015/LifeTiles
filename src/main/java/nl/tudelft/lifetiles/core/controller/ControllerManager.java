package nl.tudelft.lifetiles.core.controller;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages the controllers.
 *
 * @author Joren Hammudoglu
 */
public final class ControllerManager {

    /**
     * The singelton of ControllerManager.
     */
    private static ConcurrentHashMap<String, Controller> controllers = new ConcurrentHashMap<>();

    /**
     * Uninstantiable.
     */
    private ControllerManager() {
        // noop
    }

    /**
     * Register a controller.
     *
     * @param name
     *            the name of the controller
     * @param controller
     *            the controller
     */
    public static void registerController(final String name,
            final Controller controller) {
        controllers.putIfAbsent(name, controller);
    }

    /**
     * Get a controller by name.
     *
     * @param name
     *            the name of the controller
     * @return the controller
     */
    public static Controller getController(final String name) {
        Controller controller = controllers.get(name);
        if (controller == null) {
            throw new IllegalArgumentException("Unknow controller " + name);
        }
        return controller;
    }

    /**
     * Get all controllers.
     *
     * @return the controllers.
     */
    public static Collection<Controller> getControllers() {
        return controllers.values();
    }

}
