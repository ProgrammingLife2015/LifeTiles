package nl.tudelft.lifetiles.core.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * The controller of the menu bar.
 *
 * @author Joren Hammudoglu
 *
 */
@SuppressWarnings("restriction")
public class MenuController implements Initializable {
    /**
     * The initial x-coordinate of the window.
     */
    private double initialX;

    /**
     * The initial y-coordinate of the window.
     */
    private double initialY;

    /**
     * The menu element.
     */
    @FXML
    private MenuBar menuBar;

    /**
     * Make a node draggable so that when draggin that node, the window moves.
     * Code from http://stackoverflow.com/a/12961943/1627479
     *
     * @param node
     *            the node
     */
    private void addDraggableNode(final Node node) {
        node.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(final MouseEvent me) {
                if (me.getButton() != MouseButton.MIDDLE) {
                    initialX = me.getSceneX();
                    initialY = me.getSceneY();
                }
            }
        });

        node.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent me) {
                if (me.getButton() != MouseButton.MIDDLE) {
                    node.getScene().getWindow()
                            .setX(me.getScreenX() - initialX);
                    node.getScene().getWindow()
                            .setY(me.getScreenY() - initialY);
                }
            }
        });
    }

    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        this.addDraggableNode(menuBar);
    }
}
