package nl.tudelft.lifetiles.core.controller;

import javafx.fxml.Initializable;

/**
 * A controller. Able to register
 *
 * @author Joren Hammudoglu
 *
 */
public abstract class Controller implements Initializable {

    /**
     * Name of the main controller.
     */
    public static final String MAIN = "main";
    /**
     * Name of the menu controller.
     */
    public static final String MENU = "menu";
    /**
     * Name of the window control controller.
     */
    public static final String WINDOW_CONTROL = "windowControl";
    /**
     * Name of the graph controller.
     */
    public static final String GRAPH = "graph";
    /**
     * Name of the tree controller.
     */
    public static final String TREE = "tree";
    /**
     * Name of the sequence controller.
     */
    public static final String SEQUENCE = "sequence";

    /**
     * Register this controller to the controller manager.
     *
     * @param name
     *            the name of this controller.
     */
    protected final void register(final String name) {
        ControllerManager.registerController(name, this);
    }

    /**
     * Retrieve a registered controller.
     *
     * @param name
     *            the name of the controller
     * @return the controller
     */
    protected final Controller getController(final String name) {
        return ControllerManager.getController(name);
    }

    /**
     * Repaint all controllers.
     */
    protected final void repaintAll() {
        ControllerManager.getControllers().stream().forEach(c -> c.repaint());
    }

    /**
     * Repaint the view.
     */
    public void repaint() {
        //noop;
    }

}
