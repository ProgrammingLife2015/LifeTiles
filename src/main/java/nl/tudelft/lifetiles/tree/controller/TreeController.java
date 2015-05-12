package nl.tudelft.lifetiles.tree.controller;

import java.net.URL;
import java.util.ResourceBundle;

import nl.tudelft.lifetiles.tree.view.SunburstView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

/**
 * The controller of the tree view.
 *
 * @author Joren Hammudoglu
 *
 */
public class TreeController implements Initializable {

    /**
     * The wrapper element.
     */
    @FXML
    private BorderPane wrapper;
    
    private SunburstView view;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        view = new SunburstView();
    }

}
