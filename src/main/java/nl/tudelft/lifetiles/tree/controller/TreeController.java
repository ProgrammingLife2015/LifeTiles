package nl.tudelft.lifetiles.tree.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import nl.tudelft.lifetiles.core.controller.Controller;
import nl.tudelft.lifetiles.core.controller.MenuController;
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
     * The shout message indicating the graph model has been loaded.
     */
    public static final String TREE_LOADED = "treeLoaded";

    /**
     * The wrapper element.
     */
    @FXML
    private BorderPane wrapper;

    /**
     * The diagram.
     */
    @FXML
    private SunburstView view;

    /**
     * The tree model.
     */
    private PhylogeneticTreeItem tree;

    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        view = new SunburstView();

        listen(MenuController.FILES_OPENED, (controller, args) -> {
            assert (controller instanceof MenuController);
            final int expectedArgsLength = 3;
            assert (args.length == expectedArgsLength);
            assert (args[2] instanceof File);
            try {
                loadTree((File) args[2]);
            } catch (Exception e) {
                // TODO: notify the user that the file was not found
                e.printStackTrace();
            }
        });
    }

    /**
     * Loads the tree located in the file.
     *
     * @param file
     *            The .nwk file
     * @throws FileNotFoundException
     *             when the file is not found
     */
    private void loadTree(final File file) throws FileNotFoundException {
        // convert the file to a single string
        String fileString = null;
        try (Scanner sc = new Scanner(file).useDelimiter("\\Z")) {
            fileString = sc.next();
        } catch (FileNotFoundException e) {
            throw e;
        }

        // parse the string into a tree
        tree = PhylogeneticTreeParser.parse(fileString);

        shout(TREE_LOADED, tree);
    }

    /**
     * Repaints the view.
     */
    private void repaint() {
        if (tree != null) {
            view.setRoot(tree);
        }
    }

}
