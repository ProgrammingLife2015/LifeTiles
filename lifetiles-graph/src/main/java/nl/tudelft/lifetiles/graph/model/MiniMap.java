package nl.tudelft.lifetiles.graph.model;

import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

/**
 * The model for the minimap.
 *
 * @author Joren Hammudoglu
 *
 */
public final class MiniMap {

    /**
     * The interestingness scores per bucket.
     */
    private ObservableList<Double> scores;

    /**
     * Construct a new minimap.
     *
     * @param bucketCache
     *            the {@link BucketCache} to generate the minimap from.
     */
    public MiniMap(final BucketCache bucketCache) {
        scores = FXCollections.observableArrayList(bucketCache
                .getBuckets()
                .stream()
                .map(bucket -> bucket.stream()
                        .mapToDouble(SequenceSegment::interestingness).sum())
                .collect(Collectors.toList()));
    }

    /**
     * Get the interestingess scores.
     *
     * @return the scores
     */
    public ObservableList<Double> getScores() {
        return scores;
    }

}
