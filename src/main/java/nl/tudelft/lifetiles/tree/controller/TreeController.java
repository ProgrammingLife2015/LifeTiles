package nl.tudelft.lifetiles.tree.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import nl.tudelft.lifetiles.core.controller.Controller;
import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;
import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeParser;
import nl.tudelft.lifetiles.tree.view.SunburstView;

/**
 * The controller of the tree view.
 *
 * @author Albert Smit
 *
 */
public class TreeController extends Controller {

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
     * The currently loaded tree.
     */
    private PhylogeneticTreeItem tree;

    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        super.register(Controller.TREE);
        view = new SunburstView();
    }

    /**
     *
     * @return the tree
     */
    public final PhylogeneticTreeItem getTree() {
        if (tree == null) {
            throw new IllegalStateException("Tree not loaded.");
        }
        return tree;
    }

    /**
     * Loads the tree located in the file.
     *
     * @param file
     *            The .nwk file
     * @throws FileNotFoundException
     *             when the file is not found
     */
    public final void loadTree(final File file) throws FileNotFoundException {
        // convert the file to a single string
        String fileString = null;
        try (Scanner sc = new Scanner(file).useDelimiter("\\Z")) {
            fileString = sc.next();
        } catch (FileNotFoundException e) {
            throw e;
        }

        // parse the string into a tree
        tree = PhylogeneticTreeParser.parse(fileString);
    }

    /**
     * Check if the tree is loaded.
     *
     * @return true if the tree is loaded
     */
    public final boolean isLoaded() {
        return tree != null;
    }

    @Override
    public final void repaint() {
        if (isLoaded()) {
            view.setRoot(tree);
        }
    }

}
