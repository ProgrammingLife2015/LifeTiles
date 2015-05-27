package nl.tudelft.lifetiles.core.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * The controller of the main view.
 *
 * @author Joren Hammudoglu
 *
 */
public class MainController extends Controller {

    /**
     * The wrapper element.
     */
    @FXML
    private AnchorPane wrapper;

    /**
     * The main grid element.
     */
    @FXML
    private SplitPane mainSplitPane;

    /**
     * The content to be displayed when the data is not yet loaded.
     */
    @FXML
    private HBox splashPane;

    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        mainSplitPane.setVisible(false);

        repaint(true);

        listen(MenuController.FILES_OPENED, (observer, args) -> {
            repaint(false);
        });
    }

    /**
     * Repaint the main view, showing or hiding the splash screen.
     *
     * @param splash show the splash
     */
    private void repaint(final boolean splash) {
        mainSplitPane.setVisible(!splash);
        splashPane.setVisible(splash);
    }

}
