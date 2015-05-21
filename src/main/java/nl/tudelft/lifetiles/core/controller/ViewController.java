package nl.tudelft.lifetiles.core.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import javafx.stage.Stage;
import nl.tudelft.lifetiles.graph.models.DefaultGraphParser;
import nl.tudelft.lifetiles.graph.models.FactoryProducer;
import nl.tudelft.lifetiles.graph.models.Graph;
import nl.tudelft.lifetiles.graph.models.GraphFactory;
import nl.tudelft.lifetiles.graph.models.GraphParser;
import nl.tudelft.lifetiles.graph.models.sequence.Sequence;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;
import nl.tudelft.lifetiles.notification.model.Notification;
import nl.tudelft.lifetiles.notification.model.NotificationFactory;

/**
 * Controls what the view modules display.
 *
 * @author Rutger van den Berg
 * @author Joren Hammudoglu
 */
public final class ViewController extends Observable {

    /**
     * The singelton of ViewController.
     */
    private static ViewController instance = null;

    /**
     * Map of all sequences currently loaded.
     */
    private Map<String, Sequence> sequenceMap;
    /**
     * Set containing the currently visible sequences.
     */
    private Set<Sequence> visibleSequences;
    /**
     * The currently loaded graph.
     */
    private Graph<SequenceSegment> graph;

    /**
     * The main stage.
     */
    private Stage stageVar;

    /**
     * The notification factory.
     */
    private NotificationFactory nf;

    /**
     * Creates a new viewcontroller.
     */
    private ViewController() {
        sequenceMap = new HashMap<>();
        visibleSequences = new HashSet<>();
        nf = new NotificationFactory();
    }

    /**
     * @return A set containing all visible sequences.
     */
    public Set<Sequence> getVisible() {
        return visibleSequences;
    }

    /**
     * Sets the visible sequences in all views to the provided sequences.
     *
     * @param sequences
     *            The sequences to set to visible.
     */
    public void setVisible(final Set<Sequence> sequences) {
        visibleSequences = new HashSet<>(sequences);
        if (visibleSequences.retainAll(getSequences().values())) {
            throw new IllegalArgumentException(
                    "Attempted to set a non-existant sequence to visible");
        }

        notifyChanged();
    }

    /**
     * Get all sequences, wether they are visible or not.
     *
     * @return A Map containing all sequences.
     */
    public Map<String, Sequence> getSequences() {
        if (!isLoaded()) {
            throw new UnsupportedOperationException("Graph not loaded.");
        }
        return sequenceMap;
    }

    /**
     * @return the currently loaded graph.
     */
    public Graph<SequenceSegment> getGraph() {
        if (!isLoaded()) {
            throw new UnsupportedOperationException("Graph not loaded.");
        }
        return graph;
    }

    /**
     * Load a new graph from the specified file.
     *
     * @param vertexfile
     *            The file to get vertices for.
     * @param edgefile
     *            The file to get edges for.
     * @throws IOException
     *             When an IO error occurs while reading one of the files.
     */
    public void loadGraph(final File vertexfile, final File edgefile)
            throws IOException {
        // create the graph
        FactoryProducer<SequenceSegment> fp = new FactoryProducer<>();
        GraphFactory<SequenceSegment> gf = fp.getFactory("JGraphT");
        GraphParser gp = new DefaultGraphParser();
        graph = gp.parseGraph(vertexfile, edgefile, gf);

        // obtain the sequences
        setSequences(gp.getSequences());

        notifyChanged();
    }

    /**
     * Unload the graph and sequences.
     */
    public void unloadGraph() {
        sequenceMap = null;
        visibleSequences = null;
        graph = null;

        notifyChanged();
    }

    /**
     * @return the stage
     */
    public Stage getStage() {
        return stageVar;
    }

    /**
     * @param stage
     *            the stage to set
     */
    public void setStage(final Stage stage) {
        this.stageVar = stage;
    }

    /**
     * Notify the observers of a change.
     */
    public void notifyChanged() {
        setChanged();
        notifyObservers();
    }

    /**
     * Notify the observers of a change.
     *
     * @param arg
     *            any object
     */
    public void notifyChanged(final Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    /**
     * Check if the graph is loaded.
     *
     * @return true if the graph is loaded
     */
    public boolean isLoaded() {
        return graph != null;
    }

    /**
     * Display an error.
     *
     * @param message
     *            the error message
     */
    public void displayError(final String message) {
        Notification error = nf.error(message);
        notifyChanged(error);
    }

    /**
     * Display a warning.
     *
     * @param message
     *            the warning message
     */
    public void displayWarning(final String message) {
        Notification warning = nf.warning(message);
        notifyChanged(warning);
    }

    /**
     * Display info.
     *
     * @param message
     *            the info message
     */
    public void displayInfo(final String message) {
        Notification info = nf.info(message);
        notifyChanged(info);
    }

    /**
     * Set the sequences.
     *
     * @param sequences
     *            the sequences
     */
    private void setSequences(final Map<String, Sequence> sequences) {
        sequenceMap = sequences;
        visibleSequences = new HashSet<>(sequences.values());
    }

    /**
     * Get the instance of ViewController.
     *
     * @return the ViewController
     */
    public static synchronized ViewController getInstance() {
        if (instance == null) {
            instance = new ViewController();
        }
        return instance;
    }

}
