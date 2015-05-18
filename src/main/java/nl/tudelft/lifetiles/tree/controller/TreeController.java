package nl.tudelft.lifetiles.tree.controller;

import java.net.URL;
import java.util.ResourceBundle;

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
        np = new PhylogeneticTreeFactory("(A:0.1,B:0.2,(C:0.3,D:0.4)E:0.5)F;");
        root = np.getRoot();

        view = new SunburstView(root);
        wrapper.setCenter(view);
    }

}
