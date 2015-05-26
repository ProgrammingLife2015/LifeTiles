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
public class TreeController extends Controller<PhylogeneticTreeItem> {

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

    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        super.register(Controller.TREE);
        view = new SunburstView();
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
        setModel(PhylogeneticTreeParser.parse(fileString));
    }

    @Override
    public final void repaint() {
        if (isModelLoaded()) {
            view.setRoot(getModel());
        }
    }

    @Override
    public final void loadModel(final Object... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected 1 argument");
        }
        if (!(args[0] instanceof File)) {
            throw new IllegalArgumentException("Wrong argument type.");
        }
        try {
            loadTree((File) args[0]);
        } catch (FileNotFoundException e) {
            // TODO Display error in the GUI
            e.printStackTrace();
        }
    }

}
