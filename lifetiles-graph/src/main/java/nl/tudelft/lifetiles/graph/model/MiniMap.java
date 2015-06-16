package nl.tudelft.lifetiles.graph.model;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

/**
 * The model for the minimap.
 *
 * @author Joren Hammudoglu
 *
 */
public final class MiniMap {

    /**
     * The max score.
     */
    private static final double MAX_SCORE = 16.0;

    /**
     * The interestingness scores per bucket.
     */
    private List<Double> scores;

    /**
     * Construct a new minimap.
     *
     * @param bucketCache
     *            the {@link BucketCache} to generate the minimap from.
     */
    public MiniMap(final BucketCache bucketCache) {
        sumBuckets(bucketCache.getBuckets());
    }

    /**
     * @param buckets
     *            the buckets to sum
     */
    private void sumBuckets(final List<SortedSet<SequenceSegment>> buckets) {
        scores = new ArrayList<>();
        final int biggestBucket = buckets.stream().mapToInt(SortedSet::size)
                .reduce(0, Integer::max);
        for (SortedSet<SequenceSegment> bucket : buckets) {
            double score = bucket.parallelStream()
                    .mapToDouble(SequenceSegment::interestingness).average()
                    .getAsDouble();
            // bigger buckets have higher vertex "density" and consequently are
            // more interesting.
            score *= bucket.size() / (double) biggestBucket;
            scores.add(score);
        }
    }

    /**
     * Create a color from the score in [0,1].
     *
     * @param score
     *            the score
     * @return the color
     */
    private Color colorFromScore(final double score) {
        assert score >= 0 && score <= 1;
        return Color.gray(1.0 - score);
    }

    /**
     * Correct the scores that are bigger than the MAX_SCORE.
     *
     * @return the list of scores without outliers.
     */
    private List<Double> correctScoreOutliers() {
        return scores.stream().map(score -> Math.min(score, MAX_SCORE))
                .collect(Collectors.toList());
    }

    /**
     * Get the colors for buckets.
     *
     * @return a list of colors.
     */
    private List<Color> getColors() {
        return correctScoreOutliers().stream()
                .map(score -> colorFromScore(score / MAX_SCORE))
                .collect(Collectors.toList());
    }

    /**
     * Get the color stops.
     *
     * @return an array of {@link Stop}
     */
    public List<Stop> getStops() {
        List<Color> colors = getColors();
        int numColors = colors.size();

        List<Stop> stops = new ArrayList<>();
        for (int index = 0; index < colors.size(); index++) {
            double correction = (double) index / (double) numColors;
            double offset = (double) (index + correction) / (double) numColors;

            // There is no other way but to create objects in a loop in this case
            @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
            Stop stop = new Stop(offset, colors.get(index));
            stops.add(stop);
        }

        return stops;
    }

}
