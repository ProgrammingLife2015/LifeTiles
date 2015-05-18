package nl.tudelft.lifetiles.tree.controller;

import java.net.URL;
import java.util.ResourceBundle;

import nl.tudelft.lifetiles.core.controller.ViewController;
import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeFactory;
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
public class TreeController implements Initializable {

    /**
     * The wrapper element.
     */
    @FXML
    private BorderPane wrapper;

    /**
     * the diagram.
     */
    private SunburstView view;

    /**
     * the parser to create the tree.
     */
    private PhylogeneticTreeFactory np;

    /**
     * the tree to display.
     */
    private PhylogeneticTreeItem root;

    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        ViewController vc = ViewController.getInstance();
        vc.loadTree("data/10_set/nj_tree_10_strains");
        root = vc.getTree();

        view = new SunburstView(root);
        wrapper.setCenter(view);
    }

}
