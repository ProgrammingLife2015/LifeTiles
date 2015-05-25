package nl.tudelft.lifetiles.core.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import nl.tudelft.lifetiles.graph.controller.GraphController;

/**
 * The controller of the main view.
 *
 * @author Joren Hammudoglu
 *
 */
public class MainController extends Controller {

    /**
     * The wrapper element.
     */
    @FXML
    private AnchorPane wrapper;

    /**
     * The main grid element.
     */
    @FXML
    private SplitPane mainSplitPane;

    /**
     * The content to be displayed when the data is not yet loaded.
     */
    @FXML
    private HBox splashPane;

    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        super.register(Controller.MAIN);

        mainSplitPane.setVisible(false);
    }

    @Override
    public final void repaint() {
        GraphController graphController = (GraphController) getController(Controller.GRAPH);
        boolean loaded = graphController.isLoaded();

        mainSplitPane.setVisible(loaded);
        splashPane.setVisible(!loaded);
    }

}
