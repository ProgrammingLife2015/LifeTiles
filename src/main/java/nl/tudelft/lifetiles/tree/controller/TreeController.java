package nl.tudelft.lifetiles.tree.controller;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import nl.tudelft.lifetiles.core.controller.ViewController;
import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;
import nl.tudelft.lifetiles.tree.view.SunburstView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

/**
 * The controller of the tree view.
 *
 * @author Albert Smit
 *
 */
public class TreeController implements Initializable , Observer{

    /**
     * The wrapper element.
     */
    @FXML
    private BorderPane wrapper;

    /**
     * the diagram.
     *
     */
    @FXML
    private SunburstView view;

    /**
     * the tree to display.
     */
    private PhylogeneticTreeItem root;

    /**
     *
     */
    private ViewController controller;

    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        controller = ViewController.getInstance();
        controller.addObserver(this);
    }

    /**
     * update the SunburstView.
     */
    @Override
    public final void update(final Observable o, final Object arg) {
        if (controller.treeIsLoaded()) {
            repaint();
        }
    }

    /**
     * Redraw the tree.
     */
    private void repaint() {
        root = controller.getTree();
        view.setRoot(root);
        //view = new SunburstView(root);
    }

}
