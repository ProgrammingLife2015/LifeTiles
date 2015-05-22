package nl.tudelft.lifetiles.notification.controller;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.PriorityQueue;
import java.util.Queue;
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
     * The notifications to display.
     */
    private Queue<Notification> notifications;

    /**
     * A notification is displaying.
     */
    private boolean displaying;

    /**
     * The view controller.
     */
    private ViewController vc;

    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        vc = ViewController.getInstance();
        vc.addObserver(this);

        final int initialCapacity = 10;
        this.notifications = new PriorityQueue<>(initialCapacity,
                (n1, n2) -> n1.getPriority() - n2.getPriority());

        hide();
    }

    @Override
    public final void update(final Observable o, final Object arg) {
        if (arg instanceof Notification) {
            Notification notification = (Notification) arg;
            notifications.add(notification);
            if (!displaying) {
                displayNext();
            }
        }
    }

    /**
     * Display the next notification if there is any, otherwise hide the view.
     */
    private void displayNext() {
        final Notification next = notifications.poll();
        if (next == null) {
            hide();
            return;
        } else {
            show();
        }

        label.setText(next.getMessage());
        String color = toRGBCode(next.getColor());
        wrapper.setStyle("-fx-background-color: " + color);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(next.getDuration()),
                        event -> displayNext()));
        timeline.play();
    }

    /**
     * Hide the notification.
     */
    private void hide() {
        wrapper.visibleProperty().set(false);
        displaying = false;
    }

    /**
     * Show the notification.
     */
    private void show() {
        displaying = true;
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
