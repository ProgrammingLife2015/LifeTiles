package nl.tudelft.lifetiles.notification.controller;

import java.net.URL;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import nl.tudelft.lifetiles.core.controller.Controller;
import nl.tudelft.lifetiles.core.util.ColorUtils;
import nl.tudelft.lifetiles.core.util.Message;
import nl.tudelft.lifetiles.notification.model.Notification;

/**
 * The controller for the notification view.
 *
 * @author Joren Hammudoglu
 *
 */
public class NotificationController extends Controller {

    /**
     * Notification shout message.
     */
    public static final Message NOTIFY = Message.create("notification");

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
     * The close label.
     */
    @FXML
    private Button close;

    /**
     * The notifications to display.
     */
    private Queue<Notification> notifications;

    /**
     * A notification is displaying.
     */
    private boolean displaying;

    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        final int initialCapacity = 10;
        this.notifications = new PriorityQueue<>(initialCapacity,
                (n1, n2) -> n1.getPriority() - n2.getPriority());

        hide();

        listen(NOTIFY, (controller, args) -> {
            assert (args.length == 1 && args[0] instanceof Notification);
            Notification notification = (Notification) args[0];
            notifications.add(notification);
            if (!displaying) {
                displayNext();
            }
        });
    }

    /**
     * Close the notification.
     *
     * @param event
     *            the event
     */
    @FXML
    private void closeAction(final MouseEvent event) {
        displayNext();
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
        String color = ColorUtils.webCode(next.getColor());
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

}
