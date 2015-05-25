package nl.tudelft.lifetiles.sequence.controller;

import java.net.URL;
import java.util.HashSet;
import java.util.Locale;
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
import nl.tudelft.lifetiles.graph.controller.GraphController;
import nl.tudelft.lifetiles.graph.model.sequence.Sequence;
import nl.tudelft.lifetiles.graph.view.SequenceColor;

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
     * Map of all sequences currently loaded.
     */
    private Map<String, Sequence> sequenceMap;
    /**
     * Set containing the currently visible sequences.
     */
    private Set<Sequence> visibleSequences;

    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        super.register(Controller.SEQUENCE);
        repaint();
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
     * @param sequences
     *            The sequences to set to visible.
     */
    public final void setVisible(final Set<Sequence> sequences) {
        visibleSequences = new HashSet<>(sequences);
        if (visibleSequences.retainAll(getSequences().values())) {
            throw new IllegalArgumentException(
                    "Attempted to set a non-existant sequence to visible");
        }
    }

    /**
     * Get all sequences, wether they are visible or not.
     *
     * @return A Map containing all sequences.
     */
    public final Map<String, Sequence> getSequences() {
        GraphController graphController = (GraphController) getController(Controller.GRAPH);
        if (!graphController.isLoaded()) {
            throw new UnsupportedOperationException("Graph not loaded.");
        }
        return sequenceMap;
    }

    /**
     * Set the sequences.
     *
     * @param sequences
     *            the sequences to set
     */
    public final void setSequences(final Map<String, Sequence> sequences) {
        if (sequenceMap != null) {
            throw new IllegalStateException("Sequences already set.");
        }
        sequenceMap = sequences;
        visibleSequences = new HashSet<>(sequences.values());
    }

    /**
     * Unload the sequences.
     */
    public final void unloadSequences() {
        if (sequenceMap == null || visibleSequences == null) {
            throw new IllegalStateException("Sequences not set.");
        }
        sequenceMap = null;
        visibleSequences = null;
    }

    /**
     * Check if the sequences are loaded.
     *
     * @return <code>true</code> if the sequences are loaded, otherwise
     *         <code>false</code>.
     */
    public final boolean isLoaded() {
        return sequenceMap != null;
    }

    /**
     * Format the color into r,g,b,a format. TODO: move this somewhere central.
     *
     * @param color
     *            the color
     * @return the web color code of the color
     */
    public static String rgbaFormat(final Color color) {
        final int colorRange = 255;
        return String.format(Locale.ENGLISH, "%d,%d,%d,%f",
                (int) (color.getRed() * colorRange),
                (int) (color.getGreen() * colorRange),
                (int) (color.getBlue() * colorRange), color.getOpacity());
    }

    /**
     * Fills the sequence view and removes the old content.
     */
    @Override
    public final void repaint() {
        if (isLoaded()) {
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
        for (final Sequence sequence : getSequences().values()) {
            String id = sequence.getIdentifier();
            Label label = new Label(id);
            Color color = SequenceColor.getColor(sequence);

            label.setStyle("-fx-background-color: rgba(" + rgbaFormat(color)
                    + ")");

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

                setVisible(visible);
            });

            sequenceItems.add(label);
        }

        return sequenceItems;
    }

}
