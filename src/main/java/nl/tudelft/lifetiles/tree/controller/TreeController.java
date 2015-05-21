package nl.tudelft.lifetiles.tree.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Scanner;

import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;
import nl.tudelft.lifetiles.tree.view.SunburstView;
import javafx.fxml.FXML;
import nl.tudelft.lifetiles.core.controller.AbstractController;
import nl.tudelft.lifetiles.core.controller.MenuController;
import nl.tudelft.lifetiles.core.util.Logging;
import nl.tudelft.lifetiles.core.util.Message;
import nl.tudelft.lifetiles.graph.controller.GraphController;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeParser;

/**
 * The controller of the tree view.
 *
 * @author Albert Smit
 *
 */
public class TreeController extends AbstractController {

    /**
     * The diagram.
     */
    @FXML
    private SunburstView view;

    /**
     * The tree model.
     */
    private PhylogeneticTreeItem tree;


    /**
     * The model of sequences.
     */
    private Map<String, Sequence> sequences;

    /**
     * {@inheritDoc}
     */
    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        // load the tree when the files are opened
        listen(Message.OPENED, (controller, args) -> {
            assert controller instanceof MenuController;
            final int expectedLength = 3;
            assert args.length == expectedLength;
            assert args[2] instanceof File;
            try {
                loadTree((File) args[2]);
            } catch (FileNotFoundException e) {
                Logging.exception(e);
            }
        });

        listen(Message.LOADED, (controller, args) -> {
            if (controller instanceof GraphController) {
                assert (args[0] instanceof Graph);
                assert (args[1] instanceof Map<?, ?>);
                sequences = (Map<String, Sequence>) args[1];
                repaint();
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
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("\\Z");
        fileString = scanner.next();
        scanner.close();

        // parse the string into a tree
        tree = PhylogeneticTreeParser.parse(fileString);

        repaint();

        shout(Message.LOADED, tree);
    }

    /**
     * Repaints the view.
     */
    private void repaint() {
        if (tree != null) {
            view.setRoot(tree);
        }
    }

    private void linkSequences() {
            linkSequence(sequences, tree);
    }

    private void linkSequence(Map<String, Sequence> sequences, PhylogeneticTreeItem node){
        String id = node.getName();
        Sequence sequence = sequences.get(id);
        node.setSequence(sequence);

        for (PhylogeneticTreeItem child: node.getChildren()) {
            linkSequence(sequences, child);
        }
    }

}
