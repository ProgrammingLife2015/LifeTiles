package nl.tudelft.lifetiles.core.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import nl.tudelft.lifetiles.graph.models.DefaultGraphParser;
import nl.tudelft.lifetiles.graph.models.FactoryProducer;
import nl.tudelft.lifetiles.graph.models.Graph;
import nl.tudelft.lifetiles.graph.models.GraphFactory;
import nl.tudelft.lifetiles.graph.models.GraphParser;
import nl.tudelft.lifetiles.graph.models.sequence.Sequence;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceGenerator;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;

/**
 * Controls what the view modules display.
 *
 * @author Rutger van den Berg
 */
public class ViewController extends Observable {

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
     * Creates a new viewcontroller.
     */
    public ViewController() {
        graph = null;
    }

    /**
     * @return A set containing all visible sequences.
     */
    public final Set<Sequence> getVisible() {
        return visibleSequences;
    }

    /**
     * Sets the visible sequences in all views to the provided sequences.
     *
     * @param sequences
     *            The sequences to set to visible.
     */
    public final void setVisible(final Set<Sequence> sequences) {
        visibleSequences = sequences;
        if (visibleSequences.retainAll(sequenceMap.values())) {
            throw new IllegalArgumentException(
                    "Attempted to set a non-existant sequence to visible");
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Get all sequences, wether they are visible or not.
     *
     * @return A Map containing all sequences.
     */
    public final Map<String, Sequence> getSequences() {
        return sequenceMap;
    }

    /**
     * @return the currently loaded graph.
     */
    public final Graph<SequenceSegment> getGraph() {
        return graph;
    }

    /**
     * Load a new graph from the specified file.
     *
     * @param filename
     *            The name of the file to load.
     */
    public final void loadGraph(final String filename) {
        // create the graph
        FactoryProducer<SequenceSegment> fp = new FactoryProducer<>();
        GraphFactory<SequenceSegment> gf = fp.getFactory("JGraphT");
        GraphParser gp = new DefaultGraphParser();
        graph = gp.parseFile(filename, gf);

        // obtain the sequences
        SequenceGenerator sg = new SequenceGenerator(graph);
        setSequences(sg.generateSequences());
    }

    /**
     * Check if the graph is loaded.
     *
     * @return true if the graph is loaded
     */
    public final boolean isLoaded() {
        return graph != null;
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
    public static ViewController getInstance() {
        if (instance == null) {
            instance = new ViewController();
        }
        return instance;
    }

}
