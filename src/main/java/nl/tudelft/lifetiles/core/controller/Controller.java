package nl.tudelft.lifetiles.core.controller;

import javafx.fxml.Initializable;

/**
 * A controller. Able to register
 *
 * @author Joren Hammudoglu
 * @param <M>
 *            the model type.
 *
 */
public abstract class Controller<M> implements Initializable {

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
     * The model.
     */
    private M model;

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
     * Repaint the view.
     */
    protected void repaint() {
        // noop by default
    }

    /**
     * Repaint all controllers.
     */
    protected final void repaintAll() {
        ControllerManager.getControllers().stream().forEach(c -> c.repaint());
    }

    /**
     * Load the model.
     *
     * @param args
     *            the arguments
     */
    public void loadModel(final Object... args) {
        // noop by default
    }

    /**
     * Unload the model.
     */
    public void unloadModel() {
        if (model == null) {
            throw new IllegalStateException("Model not loaded.");
        }
        model = null;
    }

    /**
     * Check whether the model is loaded.
     *
     * @return <code>true</code> if the model is loaded, <code>false</code>
     *         otherwise
     */
    public boolean isModelLoaded() {
        return model != null;
    }

    /**
     * Set the model.
     *
     * @param newModel
     *            the new model.
     */
    public void setModel(final M newModel) {
        if (model != null) {
            throw new IllegalStateException("Model already loaded.");
        }
        model = newModel;
    }

    /**
     * Retrieve the model.
     *
     * @return the model.
     */
    public M getModel() {
        if (model == null) {
            throw new IllegalStateException("Model not loaded yet.");
        }
        return model;
    }

}
