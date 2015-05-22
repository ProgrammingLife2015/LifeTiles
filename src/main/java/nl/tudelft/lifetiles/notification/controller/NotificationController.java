package nl.tudelft.lifetiles.notification.controller;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import nl.tudelft.lifetiles.core.controller.ViewController;
import nl.tudelft.lifetiles.notification.model.Notification;

/**
 * The controller for the notification view.
 *
 * @author Joren Hammudoglu
 *
 */
public class NotificationController implements Initializable, Observer {

    /**
     * The wrapper.
     */
    @FXML
    private HBox wrapper;

    /**
     * The text field.
     */
    @FXML
    private Label label;

    /**
     * The view controller.
     */
    private ViewController vc;

    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        vc = ViewController.getInstance();
        vc.addObserver(this);

        hide();
    }

    @Override
    public final void update(final Observable o, final Object arg) {
        if (arg instanceof Notification) {
            displayNotification((Notification) arg);
        }
    }

    /**
     * Display a notification.
     *
     * @param notification
     *            the notification
     */
    public final void displayNotification(final Notification notification) {
        show();

        label.setText(notification.getMessage());
        String color = toRGBCode(notification.getColor());
        wrapper.setStyle("-fx-background-color: " + color);

        hideAfter(notification.getDuration());
    }

    /**
     * Hide the notification view after some seconds.
     *
     * @param seconds
     *            the number of seconds
     */
    public final void hideAfter(final int seconds) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(seconds), event -> hide()));
        timeline.play();
    }

    /**
     * Hide the notification.
     */
    private void hide() {
        wrapper.visibleProperty().set(false);
    }

    /**
     * Show the notification.
     */
    private void show() {
        wrapper.visibleProperty().set(true);
    }

    /**
     * Helper method to get the RGB hex code of a color.
     *
     * @param color
     *            the color
     * @return a string of the hex code
     */
    public static String toRGBCode(final Color color) {
        final int maxByte = 255;
        return String.format("#%02X%02X%02X", (int) (color.getRed() * maxByte),
                (int) (color.getGreen() * maxByte),
                (int) (color.getBlue() * maxByte));
    }

}
