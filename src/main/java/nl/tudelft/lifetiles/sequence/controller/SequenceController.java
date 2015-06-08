package nl.tudelft.lifetiles.sequence.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import nl.tudelft.lifetiles.core.controller.AbstractController;
import nl.tudelft.lifetiles.core.util.Message;
import nl.tudelft.lifetiles.graph.controller.GraphController;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceEntry;

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
     * The sequence table.
     */
    @FXML
    private TableView<SequenceEntry> sequenceTable;

    /**
     * The table column of sequence id's.
     */
    @FXML
    private TableColumn<SequenceEntry, String> idColumn;

    /**
     * The table column indiciating sequence visibility.
     */
    @FXML
    private TableColumn<SequenceEntry, Boolean> visibleColumn;

    /**
     * The table column indiciating if a sequence is the reference.
     */
    @FXML
    private TableColumn<SequenceEntry, Boolean> referenceColumn;

    /**
     * The model of sequences.
     */
    private Map<String, Sequence> sequences;

    /**
     * Set containing the currently visible sequences.
     */
    private Set<Sequence> visibleSequences;

    /**
     * The sequence entries for in the table.
     */
    private Map<String, SequenceEntry> sequenceEntries;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        registerListeners();
        initializeTable();
    }

    /**
     * Register the shout listeners.
     */
    private void registerListeners() {
        listen(Message.LOADED,
                (sender, args) -> {
                    if (sender instanceof GraphController) {
                        assert args[0] instanceof Graph;
                        assert (args[1] instanceof Map<?, ?>);
                        Map<String, Sequence> newSequences = (Map<String, Sequence>) args[1];
                        setSequences(newSequences);
                        initializeEntries(newSequences);
                        populateTable();
                    }
                });

        listen(Message.FILTERED, (sender, args) -> {
            assert args.length == 1;
            assert (args[0] instanceof Set<?>);

            setVisible((Set<Sequence>) args[0], false);
        });
    }

    /**
     * Initialize and populate the table.
     * TODO display colors
     */
    private void populateTable() {
        sequenceTable.setItems(FXCollections
                .observableArrayList(sequenceEntries.values()));
    }

    /**
     * Initialize the table.
     * TODO change the 'visible' column to contain checkboxes (see
     * {@link CheckBoxTableCell}).
     * TODO change the 'reference' column to contain choiceboxes (see
     * {@link ChoiceBoxTableCell}).
     */
    private void initializeTable() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue()
                .identifierProperty());
        visibleColumn.setCellValueFactory(cellData -> cellData.getValue()
                .visibleProperty());
        referenceColumn.setCellValueFactory(cellData -> cellData.getValue()
                .referenceProperty());
    }

    /**
     * Generate Sequence entries from sequences and store them.
     *
     * @param sequences
     *            the sequences
     */
    private void initializeEntries(final Map<String, Sequence> sequences) {
        sequenceEntries = new HashMap<>(sequences.size());
        for (Sequence sequence : sequences.values()) {
            SequenceEntry sequenceEntry = SequenceEntry.fromSequence(sequence);
            String identifier = sequence.getIdentifier();
            if (identifier.equals(DEFAULT_REFERENCE)) {
                sequenceEntry = SequenceEntry
                        .fromSequence(sequence, true, true);
            } else {
                sequenceEntry = SequenceEntry.fromSequence(sequence);
            }
            sequenceEntries.put(identifier, sequenceEntry);
        }
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

        if (shout) {
            shout(Message.FILTERED, visible);
        }
        visibleSequences = visible;
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

}
