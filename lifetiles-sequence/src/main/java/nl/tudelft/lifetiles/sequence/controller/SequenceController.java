package nl.tudelft.lifetiles.sequence.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.value.ChangeListener;
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
    private ObservableMap<String, SequenceEntry> sequenceEntries;

    /**
     * The index of the reference sequence entry.
     */
    private String reference;

    /**
     * The listeners for the visible properties.
     */
    private Map<SequenceEntry, ChangeListener<? super Boolean>> visibilityListeners;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        registerShoutListeners();
        initializeTable();

        visibilityListeners = new HashMap<>();
    }

    /**
     * Register the shout listeners.
     */
    private void registerShoutListeners() {
        listen(Message.LOADED, (sender, args) -> {
            assert args[0] instanceof String;
            if (!args[0].equals("sequences")) {
                return;
            }
                        assert (args[1] instanceof Map<?, ?>);

                        Map<String, Sequence> newSequences = (Map<String, Sequence>) args[1];
                        load(newSequences);
                    }
                });

        listen(Message.FILTERED, (sender, args) -> {
            assert args.length == 1;
            assert (args[0] instanceof Set<?>);

            updateVisible((Set<Sequence>) args[0]);
        });
    }

    /**
     * Load in the new sequences.
     *
     * @param sequences
     *            the new sequences
     */
    private void load(final Map<String, Sequence> sequences) {
        this.sequences = sequences;
        this.visibleSequences = new HashSet<>(sequences.values());
        initializeEntries(sequences);
        populateTable();
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
     */
    private void initializeTable() {
        sequenceTable.setEditable(true);

        visibleColumn.setCellFactory(CheckBoxTableCell
                .forTableColumn(visibleColumn));
        visibleColumn.setEditable(true);

        referenceColumn.setCellFactory(CheckBoxTableCell
                .forTableColumn(referenceColumn));
        referenceColumn.setEditable(true);
    }

    /**
     * Generate Sequence entries from sequences and store them.
     *
     * @param sequences
     *            the sequences
     */
    private void initializeEntries(final Map<String, Sequence> sequences) {
        sequenceEntries = FXCollections.observableHashMap();

        for (Sequence sequence : sequences.values()) {
            SequenceEntry sequenceEntry = SequenceEntry.fromSequence(sequence);
            String identifier = sequence.getIdentifier();
            if (identifier.equals(DEFAULT_REFERENCE)) {
                sequenceEntry = SequenceEntry
                        .fromSequence(sequence, true, true);
                reference = identifier;
            } else {
                sequenceEntry = SequenceEntry.fromSequence(sequence);
            }
            addVisibilityListener(sequenceEntry);
            addReferenceListener(sequenceEntry);

            sequenceEntries.put(identifier, sequenceEntry);
        }
    }

    /**
     * Add listener to the visible and reference properties of a sequence
     * entry.
     *
     * @param entry
     *            the sequence entry
     */
    private void addVisibilityListener(final SequenceEntry entry) {
        final ChangeListener<? super Boolean> visibilityListener;
        if (visibilityListeners.containsKey(entry)) {
            visibilityListener = visibilityListeners.get(entry);
        } else {
            visibilityListener = (value, previous, current) -> {
                if (previous != current) {
                    updateVisible(entry, current);
                    shout(Message.FILTERED, visibleSequences);
                }
            };
            visibilityListeners.put(entry, visibilityListener);
        }

        entry.visibleProperty().addListener(visibilityListener);
    }

    /**
     * Remove the visibility listener from the entry.
     *
     * @param entry
     *            the entry
     */
    private void removeVisibilityListener(final SequenceEntry entry) {
        if (!visibilityListeners.containsKey(entry)) {
            throw new IllegalArgumentException("Entry " + entry.getIdentifier()
                    + " has no listener");
        }
        entry.visibleProperty().removeListener(visibilityListeners.get(entry));
    }

    /**
     * Add listener to the sequence entry's reference property.
     *
     * @param entry
     *            the sequence entry.
     */
    private void addReferenceListener(final SequenceEntry entry) {
        entry.referenceProperty().addListener(
                (value, previous, current) -> {
                    if (previous != current && !previous) {
                        SequenceEntry previousRef = sequenceEntries
                                .get(reference);
                        previousRef.setReference(false);
                        String identifier = entry.getIdentifier();
                        reference = identifier;

                        shout(SequenceController.REFERENCE_SET,
                                sequences.get(identifier));
                    }
                });
    }

    /**
     * Update a sequence's visiblity and shout new visible sequences.
     *
     * @param entry
     *            the sequence entry
     * @param visible
     *            whether the sequence became visible
     */
    private void updateVisible(final SequenceEntry entry, final boolean visible) {
        Sequence sequence = this.sequences.get(entry.getIdentifier());
        if (visible) {
            visibleSequences.add(sequence);
        } else {
            visibleSequences.remove(sequence);
        }
    }

    /**
     * Update to the new visibles.
     *
     * @param visibles
     *            the new visibles
     */
    private void updateVisible(final Set<Sequence> visibles) {
        if (!sequences.values().containsAll(visibles)) {
            throw new IllegalArgumentException("Unknown sequences");
        }

        for (Sequence sequence : sequences.values()) {
            boolean visible = visibles.contains(sequence);
            SequenceEntry entry = sequenceEntries.get(sequence.getIdentifier());

            // temporarily stop listening to prevent circular shouting
            removeVisibilityListener(entry);
            entry.visibleProperty().setValue(visible);
            addVisibilityListener(entry);
        }

        visibleSequences = visibles;
    }

}
