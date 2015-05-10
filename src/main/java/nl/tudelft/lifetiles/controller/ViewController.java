package nl.tudelft.lifetiles.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import nl.tudelft.lifetiles.graph.Sequence;

/**
 * Controls what the view modules display.
 *
 * @author Rutger van den Berg
 */
public class ViewController extends Observable {
    /**
     * Set containing all sequences currently loaded.
     */
    private Set<Sequence> allSequences;
    /**
     * Map of all sequences currently loaded.
     */
    private Map<String, Sequence> sequenceMap;
    /**
     * Set containing the currently visible sequences.
     */
    private Set<Sequence> visibleSequences;

    /**
     * Creates a new viewcontroller.
     *
     * @param sequences
     *            The current sequences.
     */
    public ViewController(final Map<String, Sequence> sequences) {
        sequenceMap = sequences;
        allSequences = new HashSet<>();

        for (Map.Entry<String, Sequence> map : sequenceMap.entrySet()) {
            Sequence seq = map.getValue();
            allSequences.add(seq);
        }
        visibleSequences = new HashSet<>(allSequences);
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
        if (visibleSequences.retainAll(allSequences)) {
            throw new IllegalArgumentException(
                    "Attempted to set a non-existant sequence to visible");
        }
        setChanged();
        notifyObservers();
    }

}
