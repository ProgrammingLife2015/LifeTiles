package nl.tudelft.lifetiles.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * The controller of the menu bar.
 * 
 * @author Joren Hammudoglu
 *
 */
public class MenuController implements Initializable {
	/**
	 * The menu element.
	 */
	@FXML
	private MenuBar menuBar;

	protected double initialX;
	protected double initialY;

	/**
	 * Handle action related to "About" menu item.
	 * 
	 * @param event
	 *            Event on "About" menu item.
	 */
	@FXML
	private void handleAboutAction(final ActionEvent event) {
		provideAboutFunctionality();
	}

	/**
	 * Handle action related to input (in this case specifically only responds
	 * to keyboard event CTRL-A).
	 * 
	 * @param event
	 *            Input event.
	 */
	@FXML
	private void handleKeyInput(final InputEvent event) {
		if (event instanceof KeyEvent) {
			final KeyEvent keyEvent = (KeyEvent) event;
			if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.A) {
				provideAboutFunctionality();
			}
		}
	}

	/**
	 * Make a node draggable so that when draggin that node, the window moves.
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

	/**
	 * Perform functionality associated with "About" menu selection or CTRL-A.
	 */
	private void provideAboutFunctionality() {
		System.out.println("You clicked on About!");
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		this.addDraggableNode(menuBar);
	}
}
