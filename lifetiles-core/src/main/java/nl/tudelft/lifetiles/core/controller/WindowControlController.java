package nl.tudelft.lifetiles.core.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;
import nl.tudelft.lifetiles.core.util.Message;

/**
 * The controller of the window controls.
 *
 * @author Joren Hammudoglu
 *
 */
public class WindowControlController extends AbstractController {

    /**
     * The minimize window shout message.
     */
    public static final Message MINIMIZE = Message.create("minimize");

    /**
     * The resize window shout message.
     */
    public static final Message RESIZE = Message.create("resize");

    /**
     * The window close button.
     */
    @FXML
    private Button windowClose;

    /**
     * The window minimize button.
     */
    @FXML
    private Button windowMinimize;

    /**
     * The window resize button.
     */
    @FXML
    private Button windowResize;

    /**
     * Close the window.
     */
    @FXML
    private void closeWindowAction() {
        getStage(windowClose.getScene().getWindow()).close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        // noop
    }

    /**
     * Minimize the window.
     */
    @FXML
    public final void minimizeWindowAction() {
        getStage(windowMinimize.getScene().getWindow()).toBack();
        shout(MINIMIZE);
    }

    /**
     * @param window
     *            The window to cast.
     * @return The old Window as a Stage.
     */
    private Stage getStage(final Window window) {
        if (window instanceof Stage) {
            return (Stage) window;
        } else {
            throw new IllegalStateException("The window was not a Stage");
        }
    }

    /**
     * Resize the window.
     */
    @FXML
    private void resizeWindowAction() {
        Stage stage = getStage(windowResize.getScene().getWindow());
        stage.setMaximized(!stage.isMaximized());
        shout(RESIZE);
    }
}
