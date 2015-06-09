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
        Window window = windowClose.getScene().getWindow();
        if (window instanceof Stage) {
            Stage stage = (Stage) window;
            stage.close();
        }
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
        Window window = windowMinimize.getScene().getWindow();
        if (window instanceof Stage) {
            Stage stage = (Stage) window;
            stage.toBack();
        }

        shout(MINIMIZE);
    }

    /**
     * Resize the window.
     */
    @FXML
    private void resizeWindowAction() {
        Window window = windowResize.getScene().getWindow();
        if (window instanceof Stage) {
            Stage stage = (Stage) window;
            stage.setMaximized(!stage.isMaximized());
        }

        shout(RESIZE);
    }
}
