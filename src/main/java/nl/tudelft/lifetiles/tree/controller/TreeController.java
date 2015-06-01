package nl.tudelft.lifetiles.tree.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;

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
     * The visible tree model.
     */
    private PhylogeneticTreeItem visibleTree;

    /**
     * The model of sequences.
     */

    private Map<String, Sequence> sequences;

    /**
     * the visible sequences.
     */

    private Set<Sequence> visibleSequences;

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

        listen(Message.FILTERED, (controller, args) -> {
            // check the message is correct
            assert args.length == 1;
            if (!(args[0] instanceof Set<?>)) {
                throw new IllegalArgumentException(
                        "Argument not of type Set<Sequence>");
            }
            if (!(controller instanceof TreeController)) {
                // create the new tree
                setVisible((Set<Sequence>) args[0]);
            }
        });
        view.setController(this);
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
        linkSequence(sequences, tree);
        populateChildSequences(tree);
        visibleTree = tree;

        repaint();

        shout(Message.LOADED, tree);
    }

    /**
     * Repaints the view.
     */
    private void repaint() {
        if (visibleTree != null) {
            view.setRoot(visibleTree);
        }
    }

    private void linkSequence(Map<String, Sequence> sequences, PhylogeneticTreeItem node) {
        String id = node.getName();
        Sequence sequence = sequences.get(id);
        node.setSequence(sequence);

        for (PhylogeneticTreeItem child: node.getChildren()) {
            linkSequence(sequences, child);
        }
    }

    private void populateChildSequences(PhylogeneticTreeItem node) {
        for (PhylogeneticTreeItem child: node.getChildren()) {
            populateChildSequences(child);
        }
        node.setChildSequences();
    }

    private void setVisible(Set<Sequence> visible){
        visibleSequences = visible;
        visibleTree = subTree(tree);
        repaint();
    }

    private PhylogeneticTreeItem subTree(PhylogeneticTreeItem node){
        // copy the node
        PhylogeneticTreeItem result = new PhylogeneticTreeItem();
        if(visibleSequences.contains(node.getSequence())) {
            result.setName(node.getName());
            result.setDistance(node.getDistance());
        }
        else if(node.getChildren().isEmpty()) {
            return null;
        }
        // copy the children when they are needed
        for( PhylogeneticTreeItem child: node.getChildren()){
            // check if this child is needed
            //TODO loop order?
            for(Sequence sequence : node.getChildSequences()){
                if(visibleSequences.contains(sequence)){
                    PhylogeneticTreeItem subtree = subTree(child);
                    if (subtree != null) {
                        subtree.setParent(result);
                    }
                    break;
                }
            }
        }
        // remove useless nodes(nodes with at single child can be removed from the subtree)
        if (result.getChildren().size() == 1) {
            result = result.getChildren().get(0);
        }
        //fix sequences
        linkSequence(sequences, result);
        populateChildSequences(result);
        return result;
    }
}
