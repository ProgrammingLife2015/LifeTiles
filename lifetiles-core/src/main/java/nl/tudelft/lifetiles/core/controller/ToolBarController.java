package nl.tudelft.lifetiles.core.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import nl.tudelft.lifetiles.core.util.Message;

/**
 * This Toolbar contains additional controls for the program
 * like zoom buttons.
 *
 * @author AC Langerak
 *
 */
public class ToolBarController extends AbstractController {

    /**
     *
     * Event triggered when clicked on the "-" button.
     */
    @FXML
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void zoomOut() {
        shout(Message.ZOOM, "", Integer.valueOf(-1));
    }

    /**
     *
     * Event triggered when clicked on the "+" button.
     */
    @FXML
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void zoomIn() {
        shout(Message.ZOOM, "", Integer.valueOf(1));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL arg0, final ResourceBundle arg1) {

    }

}
