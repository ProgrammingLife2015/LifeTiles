package nl.tudelft.lifetiles.sequence.controller;

import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import nl.tudelft.lifetiles.core.controller.AbstractController;
import nl.tudelft.lifetiles.core.util.ColorUtils;
import nl.tudelft.lifetiles.core.util.Message;
import nl.tudelft.lifetiles.graph.controller.GraphController;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.graph.traverser.MutationIndicationTraverser;
import nl.tudelft.lifetiles.graph.traverser.ReferencePositionTraverser;
import nl.tudelft.lifetiles.notification.controller.NotificationController;
import nl.tudelft.lifetiles.notification.model.AbstractNotification;
import nl.tudelft.lifetiles.notification.model.NotificationFactory;
import nl.tudelft.lifetiles.sequence.SequenceColor;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

/**
 * The controller of the data view.
 *
 * @author Joren Hammudoglu
 *
 */
public final class SequenceController extends AbstractController {

    /**
     * Shout message indicating a sequence has been set as reference.
     */
    public static final Message REFERENCE_SET = Message.create("referenceSet");

    /**
     * The default reference sequence name.
     */
    private static final String DEFAULT_REFERENCE = "TKK_REF";

    /**
     * Contains the sequences.
     */
    @FXML
    private ListView<Label> sequenceList;

    /**
     * The model of sequences.
     */
    private Map<String, Sequence> sequences;

    /**
     * Set containing the currently visible sequences.
     */
    private Set<Sequence> visibleSequences;

    /**
     * The reference sequence.
     */
    private Sequence reference;

    /**
     * The loaded graph.
     */
    private Graph<SequenceSegment> graph;

    /**
     * Notification factory.
     */
    private final NotificationFactory notFact = new NotificationFactory();

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        repaint();

        listen(Message.LOADED, (sender, args) -> {
            if (sender instanceof GraphController) {
                assert args[0] instanceof Graph;
                assert (args[1] instanceof Map<?, ?>);
                graph = (Graph<SequenceSegment>) args[0];
                setSequences((Map<String, Sequence>) args[1]);
                setReference();
                repaint();
            }
        });

        listen(Message.FILTERED, (sender, args) -> {
            assert args.length == 1;
            assert (args[0] instanceof Set<?>);

            setVisible((Set<Sequence>) args[0], false);
        });
    }

    /**
     * @return A set containing all visible sequences.
     */
    public Set<Sequence> getVisible() {
        if (visibleSequences == null) {
            throw new IllegalStateException("Sequences not loaded.");
        }
        return visibleSequences;
    }

    /**
     * Sets the visible sequences in all views to the provided sequences.
     *
     * @param visible
     *            The sequences to set to visible.
     * @param shout
     *            shout that the seqeunces have been filtered
     */
    private void setVisible(final Set<Sequence> visible, final boolean shout) {
        if (!sequences.values().containsAll(visible)) {
            throw new IllegalArgumentException(
                    "Attempted to set a non-existant sequence to visible");
        }
        // Limit the visible segquences of this class to the visible set given
        // from someone
        getVisible().retainAll(visible);
        repaint();

        if (shout) {
            shout(Message.FILTERED, visible);
        }
        visibleSequences = visible;
        repaint();
    }

    /**
     * Set the sequences.
     *
     * @param newSequences
     *            the sequences to set
     */
    public void setSequences(final Map<String, Sequence> newSequences) {
        sequences = newSequences;
        visibleSequences = new HashSet<>(sequences.values());
    }

    /**
     * Fills the sequence view and removes the old content.
     */
    private void repaint() {
        if (sequences != null) {
            sequenceList.setItems(generateLabels());
        }
    }

    /**
     * Generates the sequence labels.
     *
     * @return a list of the labels
     */
    private ObservableList<Label> generateLabels() {
        ObservableList<Label> sequenceItems = FXCollections
                .observableArrayList();
        for (final Sequence sequence : sequences.values()) {
            String identifier = sequence.getIdentifier();

            if (sequence.equals(reference)) {
                identifier += "*";
            }

            Label label = new Label(identifier);
            Color color = SequenceColor.getColor(sequence);

            label.setStyle("-fx-background-color: rgba("
                    + ColorUtils.rgbaFormat(color) + ")");

            addMouseEventListener(sequence, label);

            sequenceItems.add(label);
        }

        return sequenceItems;
    }

    /**
     * Adds mouse event listeners to a sequence label.
     *
     * @param sequence
     *            the sequence
     * @param label
     *            the label
     */
    public void addMouseEventListener(final Sequence sequence, final Label label) {
        String styleClassFilter = "filtered";
        ObservableList<String> styleClass = label.getStyleClass();
        if (getVisible().contains(sequence)) {
            styleClass.add(styleClassFilter);
        }

        label.setOnMouseClicked(mouseEvent -> {
            Set<Sequence> visible = getVisible();

            if (styleClass.contains(styleClassFilter)) {
                // hide
                visible.remove(sequence);
                styleClass.remove(styleClassFilter);
            } else {
                // show
                visible.add(sequence);
                styleClass.add(styleClassFilter);
            }

            setVisible(visible, true);
        });

        label.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isSecondaryButtonDown()) {
                getContextMenu(sequence).show(label, mouseEvent.getScreenX(),
                        mouseEvent.getScreenY());
            }
        });
    }

    /**
     * Get the sequence context menu.
     *
     * @param sequence
     *            the sequence
     * @return the context menu
     */
    private ContextMenu getContextMenu(final Sequence sequence) {
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem setRef = new MenuItem("Set as reference.");
        contextMenu.getItems().add(setRef);
        setRef.setOnAction(actionEvent -> {
            setReference(sequence);
            repaint();
        });
        return contextMenu;
    }

    /**
     * Set the default reference as reference.
     */
    private void setReference() {
        Sequence defaultReference = null;
        for (Sequence other : sequences.values()) {
            if (other.getIdentifier().equals(DEFAULT_REFERENCE)) {
                defaultReference = other;
                break;
            }
        }

        if (defaultReference == null) {
            String message = "The default reference sequence "
                    + DEFAULT_REFERENCE + " was not found";
            AbstractNotification notification = notFact.getNotification(
                    message, NotificationFactory.WARNING);
            shout(NotificationController.NOTIFY, notification);
        }

        this.reference = defaultReference;
    }

    /**
     * Set a sequence as reference.
     *
     * @param sequence
     *            the new reference sequence
     */
    private void setReference(final Sequence sequence) {
        this.reference = sequence;
        shout(SequenceController.REFERENCE_SET, sequence);

        new ReferencePositionTraverser(sequence).referenceMapGraph(graph);
        new MutationIndicationTraverser(sequence).indicateGraphMutations(graph);
    }

    /**
     * Get the reference.
     *
     * @return the reference
     */
    private Sequence getReference() {
        return reference;
    }

}
