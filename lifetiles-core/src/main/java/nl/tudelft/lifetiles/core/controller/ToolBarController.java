package nl.tudelft.lifetiles.core.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import nl.tudelft.lifetiles.core.util.Message;
import nl.tudelft.lifetiles.notification.model.NotificationFactory;

public class ToolBarController extends AbstractController {

    /**
     * The notification factory.
     */
    private NotificationFactory nf;

    @FXML
    private void zoomOut(final ActionEvent event) {
        shout(Message.ZOOM, "", new Integer(-1));
    }

    @FXML
    private void zoomIn(final ActionEvent event) {
        shout(Message.ZOOM, "", new Integer(1));
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        nf = new NotificationFactory();
    }

}
