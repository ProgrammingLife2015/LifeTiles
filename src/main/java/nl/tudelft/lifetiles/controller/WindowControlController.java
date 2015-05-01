package nl.tudelft.lifetiles.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * The controller of the window controls.
 * 
 * @author Joren Hammudoglu
 *
 */
public class WindowControlController implements Initializable {

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
	private void closeWindowAction() {
		Stage stage = (Stage) windowClose.getScene().getWindow();
		stage.close();
	}

	/**
	 * Minimize the window.
	 */
	@FXML
	private void minimizeWindowAction() {
		Stage stage = (Stage) windowMinimize.getScene().getWindow();
		stage.toBack();
	}

	/**
	 * Resize the window.
	 */
	@FXML
	private void resizeWindowAction() {
		Stage stage = (Stage) windowResize.getScene().getWindow();
		stage.setMaximized(!stage.isMaximized());
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		// nothing to do here
	}
}
