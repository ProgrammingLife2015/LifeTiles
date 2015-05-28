package nl.tudelft.lifetiles.core.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
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
    public final void closeWindowAction() {
        Stage stage = (Stage) windowClose.getScene().getWindow();
        stage.close();
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
        Stage stage = (Stage) windowMinimize.getScene().getWindow();
        stage.toBack();

        shout(MINIMIZE);
    }

    /**
     * Resize the window.
     */
    @FXML
    public final void resizeWindowAction() {
        Stage stage = (Stage) windowResize.getScene().getWindow();
        stage.setMaximized(!stage.isMaximized());

        shout(RESIZE);
    }
}
