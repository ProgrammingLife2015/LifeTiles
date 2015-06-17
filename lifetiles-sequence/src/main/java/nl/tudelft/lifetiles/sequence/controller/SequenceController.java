package nl.tudelft.lifetiles.sequence.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import nl.tudelft.lifetiles.core.controller.AbstractController;
import nl.tudelft.lifetiles.core.util.Logging;
import nl.tudelft.lifetiles.core.util.Message;
import nl.tudelft.lifetiles.notification.controller.NotificationController;
import nl.tudelft.lifetiles.notification.model.NotificationFactory;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceEntry;
import nl.tudelft.lifetiles.sequence.model.SequenceMetaParser;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * The controller of the data view.
 *
 * @author Joren Hammudoglu
 *
 */
public class SequenceController extends AbstractController {

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
    private Map<SequenceEntry, ChangeListener<? super Boolean>> visibleListeners;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        registerShoutListeners();
        initializeTable();

        visibleListeners = new HashMap<>();
    }

    /**
     * Register the shout listeners.
     */
    private void registerShoutListeners() {
        listen(Message.LOADED, (sender, subject, args) -> {
            if (!"sequences".equals(subject)) {
                return;
            }
            // eclipse and PMD disagree on whether parentheses are
            // needed
                assert (args[0] instanceof Map<?, ?>);

                @SuppressWarnings("unchecked")
                Map<String, Sequence> sequences = (Map<String, Sequence>) args[0];
                load(sequences);

            });

        listen(Message.FILTERED, (sender, subject, args) -> {
            assert args.length == 1;
            assert (args[0] instanceof Set<?>);
            @SuppressWarnings("unchecked")
            Set<Sequence> sequences = (Set<Sequence>) args[0];
            updateVisible(sequences);
        });

        listen(Message.RESET, (sender, subject, args) -> {
            Set<Sequence> visibleSet = new HashSet<>(sequences.values());
            updateVisible(visibleSet);
            shout(Message.FILTERED, "", visibleSet);
        });

        listen(Message.OPENED,
                (sender, subject, args) -> {
                    if (!"meta".equals(subject)) {
                        return;
                    }
                    assert args[0] instanceof File;

                    SequenceMetaParser parser = new SequenceMetaParser();
                    try {
                        parser.parse((File) args[0]);
                        addMetaData(parser.getColumns(), parser.getData());
                    } catch (IOException exception) {
                        Logging.exception(exception);
                        shout(NotificationController.NOTIFY, "",
                                new NotificationFactory()
                                        .getNotification(exception));
                    }
                });
    }

    /**
     * Add the meta data to the appropriate sequence entries.
     *
     * @param columns
     *            The column names
     * @param data
     *            The actual data
     */
    private void addMetaData(final List<String> columns,
            final Map<String, Map<String, String>> data) {
        for (Entry<String, Map<String, String>> sequenceMeta : data.entrySet()) {
            String identifier = sequenceMeta.getKey();
            if (sequenceEntries.containsKey(identifier)) {
                SequenceEntry sequenceEntry = sequenceEntries.get(identifier);
                sequenceEntry.setMetaData(sequenceMeta.getValue());
            }
        }

        addMetaColumns(columns);
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
     * Add the meta data columns to the table.
     *
     * @param columns
     *            the names of the columns
     */
    private void addMetaColumns(final List<String> columns) {
        for (String columnName : columns) {
            // purpose of this method is to create these.
            @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
            TableColumn<SequenceEntry, String> column = new TableColumn<>(
                    columnName);
            column.setCellValueFactory(entry -> entry.getValue().metaProperty(
                    columnName));
            sequenceTable.getColumns().add(column);
        }
    }

    /**
     * Generate Sequence entries from sequences and store them.
     *
     * @param sequences
     *            the sequences
     */
    // suppress a false positive on SequenceEntry
    @SuppressFBWarnings("DLS_DEAD_LOCAL_STORE")
    private void initializeEntries(final Map<String, Sequence> sequences) {
        sequenceEntries = FXCollections.observableHashMap();

        for (Sequence sequence : sequences.values()) {
            SequenceEntry sequenceEntry = SequenceEntry.fromSequence(sequence);
            String identifier = sequence.getIdentifier();
            if (identifier.equals(DEFAULT_REFERENCE)) {
                sequenceEntry = SequenceEntry
                        .fromSequence(sequence, true, true);
                reference = identifier;
                shout(SequenceController.REFERENCE_SET, "", sequence);
            } else {
                sequenceEntry = SequenceEntry.fromSequence(sequence);
            }
            addVisibilityListener(sequenceEntry);
            addReferenceListener(sequenceEntry);

            sequenceEntries.put(identifier, sequenceEntry);
        }
    }

    /**
     * Add listener to the visible and reference properties of a sequence entry.
     *
     * @param entry
     *            the sequence entry
     */
    private void addVisibilityListener(final SequenceEntry entry) {
        final ChangeListener<? super Boolean> listener;
        if (visibleListeners.containsKey(entry)) {
            listener = visibleListeners.get(entry);
        } else {
            listener = (value, previous, current) -> {
                if (previous != current) {
                    updateVisible(entry, current);
                    shout(Message.FILTERED, "", visibleSequences);
                }
            };
            visibleListeners.put(entry, listener);
        }

        entry.visibleProperty().addListener(listener);
    }

    /**
     * Remove the visibility listener from the entry.
     *
     * @param entry
     *            the entry
     */
    private void removeVisibilityListener(final SequenceEntry entry) {
        if (!visibleListeners.containsKey(entry)) {
            throw new IllegalArgumentException("Entry " + entry.getIdentifier()
                    + " has no listener");
        }
        entry.visibleProperty().removeListener(visibleListeners.get(entry));
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

                        shout(SequenceController.REFERENCE_SET, "",
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
