package nl.tudelft.lifetiles.notification.controller;

import java.net.URL;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import nl.tudelft.lifetiles.core.controller.AbstractController;
import nl.tudelft.lifetiles.core.util.ColorUtils;
import nl.tudelft.lifetiles.core.util.Message;
import nl.tudelft.lifetiles.notification.model.AbstractNotification;

/**
 * The controller for the notification view.
 *
 * @author Joren Hammudoglu
 *
 */
public class NotificationController extends AbstractController {

    /**
     * AbstractNotification shout message.
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
     * The notifications to display.
     */
    private Queue<AbstractNotification> notifications;

    /**
     * A notification is displaying.
     */
    private boolean displaying;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location,
            final ResourceBundle resources) {
        final int initialCapacity = 10;
        this.notifications = new PriorityQueue<>(initialCapacity, (
                notification1, notification2) -> {
            if (notification1.getPriority() == notification2.getPriority()) {
                return Boolean.compare(notification1.equals(notification2),
                        true);
            } else {
                return notification1.getPriority()
                        - notification2.getPriority();
            }
        });

        hide();

        listen(NOTIFY, (controller, subject, args) -> {
            assert args.length == 1 && args[0] instanceof AbstractNotification;
            AbstractNotification notification = (AbstractNotification) args[0];
            if (!notifications.contains(notification)) {
                notifications.add(notification);
            }
            if (!displaying) {
                displayNext();
            }
        });
    }

    /**
     * Close the notification.
     */
    @FXML
    private void closeAction() {
        displayNext();
    }

    /**
     * Display the next notification if there is any, otherwise hide the view.
     */
    private void displayNext() {
        final AbstractNotification next = notifications.poll();
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
