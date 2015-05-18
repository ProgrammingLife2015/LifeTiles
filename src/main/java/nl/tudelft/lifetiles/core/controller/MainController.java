package nl.tudelft.lifetiles.core.controller;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * The controller of the main view.
 *
 * @author Joren Hammudoglu
 *
 */
public class MainController implements Initializable, Observer {

    /**
     * The wrapper element.
     */
    @FXML
    private VBox wrapper;

    /**
     * The main grid element.
     */
    @FXML
    private GridPane mainGrid;

    /**
     * The content to be displayed when the data is not yet loaded.
     */
    @FXML
    private HBox splashPane;

    /**
     * The view controller.
     */
    private ViewController vc;

    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        vc = ViewController.getInstance();
        vc.addObserver(this);

        mainGrid.setVisible(false);
    }

    @Override
    public final void update(final Observable o, final Object arg) {
        if (vc.isLoaded()) {
            splashPane.setVisible(false);
            mainGrid.setVisible(true);
        }
    }

}
