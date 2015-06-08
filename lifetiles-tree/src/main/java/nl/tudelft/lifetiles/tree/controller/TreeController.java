package nl.tudelft.lifetiles.tree.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;

import javafx.fxml.FXML;
import nl.tudelft.lifetiles.core.controller.AbstractController;
import nl.tudelft.lifetiles.core.controller.MenuController;
import nl.tudelft.lifetiles.core.util.Logging;
import nl.tudelft.lifetiles.core.util.Message;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;
import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeParser;
import nl.tudelft.lifetiles.tree.view.SunburstView;

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
            assert args[0] instanceof String;
            if (!((String) args[0]).equals("tree")) {
                return;
            }
            final int expectedLength = 2;
            assert args.length == expectedLength;
            assert args[1] instanceof File;
            try {
                loadTree((File) args[1]);
            } catch (FileNotFoundException e) {
                Logging.exception(e);
            }
        });

        listen(Message.LOADED, (controller, args) -> {
            assert args[0] instanceof String;
            if (!((String) args[0]).equals("sequences")) {
                return;
            }
            assert (args[1] instanceof Map<?, ?>);
            sequences = (Map<String, Sequence>) args[1];
            repaint();
        });

        listen(Message.FILTERED, (controller, args) -> {
            // check the message is correct
                assert args.length == 1;
                assert (args[0] instanceof Set<?>);
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

        shout(Message.LOADED, "tree", tree);
    }

    /**
     * Repaints the view.
     */
    private void repaint() {
        if (visibleTree != null) {
            view.setRoot(visibleTree);
        }
    }

    /**
     * Add the sequences to a tree, a sequence will be added to a node if the
     * node's name matches the sequence's id.
     *
     * @param sequences
     *            a map containing the sequences and their identifiers
     * @param node
     *            the root of a phylogenetic tree
     */
    private void linkSequence(final Map<String, Sequence> sequences,
            final PhylogeneticTreeItem node) {
        String ident = node.getName();
        Sequence sequence = sequences.get(ident);
        node.setSequence(sequence);

        for (PhylogeneticTreeItem child : node.getChildren()) {
            linkSequence(sequences, child);
        }
    }

    /**
     * Fills the set containing the sequences that descend from this node. the
     * sequences should already have been added to the tree.
     *
     * @param node
     *            the root of a phylogenetic tree
     */
    private void populateChildSequences(final PhylogeneticTreeItem node) {
        for (PhylogeneticTreeItem child : node.getChildren()) {
            populateChildSequences(child);
        }
        node.setChildSequences();
    }

    /**
     * Sets the sequences in the set to visible, and creates a tree that only
     * contains the visible sequences.
     *
     * @param visible
     *            A set containing all visible sequences
     */
    private void setVisible(final Set<Sequence> visible) {
        visibleSequences = visible;
        visibleTree = subTree(tree);
        repaint();
    }

    /**
     * Informs other controllers the filter changed.
     *
     * @param visible
     *            the set that needs to be visible
     */
    public void shoutVisible(final Set<Sequence> visible) {
        shout(Message.FILTERED, visible);
    }

    /**
     * Creates a new tree that only contains the visible nodes. When a node has
     * only one child, it is removed from the tree and its child is returned
     * instead. When a node has no children, and is not visible, null is
     * returned.
     *
     * @param node
     *            the root of a tree that we want a subtree of
     * @return the new root of a subtree.
     */
    private PhylogeneticTreeItem subTree(final PhylogeneticTreeItem node) {
        // copy the node
        PhylogeneticTreeItem result = new PhylogeneticTreeItem();
        if (visibleSequences.contains(node.getSequence())) {
            result.setName(node.getName());
            result.setDistance(node.getDistance());
        } else if (node.getChildren().isEmpty()) {
            return null;
        }
        // copy the children when they are needed
        for (PhylogeneticTreeItem child : node.getChildren()) {
            // check if this child is needed
            for (Sequence sequence : visibleSequences) {
                if (node.getChildSequences().contains(sequence)) {
                    PhylogeneticTreeItem subtree = subTree(child);
                    if (subtree != null) {
                        subtree.setParent(result);
                    }
                    break;
                }
            }
        }
        // remove useless nodes(nodes with at single child can be removed from
        // the subtree)
        if (result.getChildren().isEmpty() && result.getName() == null) {
            return null;
        }
        if (result.getChildren().size() == 1) {
            result = result.getChildren().get(0);
        }
        // fix sequences
        linkSequence(sequences, result);
        populateChildSequences(result);
        return result;
    }
}
