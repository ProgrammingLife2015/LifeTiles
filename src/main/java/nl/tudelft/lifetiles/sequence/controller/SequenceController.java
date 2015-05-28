package nl.tudelft.lifetiles.sequence.controller;

import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import nl.tudelft.lifetiles.core.controller.Controller;
import nl.tudelft.lifetiles.core.util.ColorUtils;
import nl.tudelft.lifetiles.core.util.Message;
import nl.tudelft.lifetiles.graph.controller.GraphController;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.graph.model.GraphParser;
import nl.tudelft.lifetiles.graph.view.SequenceColor;
import nl.tudelft.lifetiles.sequence.model.Sequence;

/**
 * The controller of the data view.
 *
 * @author Joren Hammudoglu
 *
 */
public class SequenceController extends Controller {

    /**
     * The wrapper element.
     */
    @FXML
    private AnchorPane wrapper;
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

    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        repaint();

        listen(Message.LOADED, (controller, args) -> {
            if (controller instanceof GraphController) {
                assert (args[0] instanceof Graph);
                assert (args[1] instanceof GraphParser);
                GraphParser gp = (GraphParser) args[1];
                setSequences(gp.getSequences());
                repaint();
            }
        });

        listen(Message.FILTERED, (controller, args) -> {
            assert (args.length == 1);
            if (!(args[0] instanceof Set<?>)) {
                throw new IllegalArgumentException(
                        "Argument not of type Set<Sequence>");
            }
            setVisible((Set<Sequence>) args[0], false);
        });
    }

    /**
     * @return A set containing all visible sequences.
     */
    public final Set<Sequence> getVisible() {
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
        if (!visible.containsAll(sequences.values())) {
            throw new IllegalArgumentException(
                    "Attempted to set a non-existant sequence to visible");
        }
        if (shout) {
            shout(Message.FILTERED, visible);
        }
    }

    /**
     * Set the sequences.
     *
     * @param newSequences
     *            the sequences to set
     */
    public final void setSequences(final Map<String, Sequence> newSequences) {
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
            String id = sequence.getIdentifier();
            Label label = new Label(id);
            Color color = SequenceColor.getColor(sequence);

            label.setStyle("-fx-background-color: rgba("
                    + ColorUtils.rgbaFormat(color) + ")");

            String styleClassFilter = "filtered";
            if (getVisible().contains(sequence)) {
                label.getStyleClass().add(styleClassFilter);
            }

            label.setOnMouseClicked((mouseEvent) -> {
                Set<Sequence> visible = getVisible();

                if (label.getStyleClass().contains(styleClassFilter)) {
                    // hide
                    visible.remove(sequence);
                    label.getStyleClass().remove(styleClassFilter);
                } else {
                    // show
                    visible.add(sequence);
                    label.getStyleClass().add(styleClassFilter);
                }

                setVisible(visible, true);
            });

            sequenceItems.add(label);
        }

        return sequenceItems;
    }

}
