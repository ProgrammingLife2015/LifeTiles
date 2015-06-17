package nl.tudelft.lifetiles.graph.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;

/**
 * The model for the minimap.
 *
 * @author Joren Hammudoglu
 *
 */
public final class MiniMap {

    /**
     * The max number of deviations a score is allowed to be removed from the
     * average.
     */
    private static final double MAX_DEVIATION = 2.0;

    /**
     * The max number of scores.
     */
    private static final int MAX_SCORES = 1024;

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
    private void sumBuckets(final List<Bucket> buckets) {
        scores = buckets.stream().map(bucket -> bucket.interestingness())
                .collect(Collectors.toList());
        reduceNumScores();
    }

    /**
     * Keeps halfing the number of scores until small enough.
     */
    private void reduceNumScores() {
        while (scores.size() > MAX_SCORES) {
            // not an issue since this will only loop a few times
            @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
            List<Double> newScores = new ArrayList<>(scores.size() / 2);

            for (int index = 0; index < scores.size(); index += 2) {
                double newScore = scores.get(index) + scores.get(index + 1);
                newScores.add(newScore);
            }

            scores = newScores;
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
     * @param collection
     *            the collection to take the median of
     * @return the median of the scores
     */
    private static double median(final Collection<Double> collection) {
        List<Double> sorted = new ArrayList<>(collection);
        Collections.sort(sorted);
        return sorted.get(sorted.size() / 2);
    }

    /**
     * @return the median of absolute deviations (MAD) of the scores.
     */
    private double scoreMAD() {
        double median = median(scores);
        List<Double> deviations = scores.stream()
                .map(score -> Math.abs(score - median))
                .collect(Collectors.toList());
        return median(deviations);
    }

    /**
     * Correct the scores that are bigger than the MAX_SCORE.
     *
     * @return the list of scores without outliers.
     */
    private List<Double> correctScoreOutliers() {
        double median = median(scores);
        double delta = MAX_DEVIATION * scoreMAD();
        double boundLeft = median - delta;
        double boundRight = median + delta;
        return scores.parallelStream()
                .map(score -> Math.min(boundRight, Math.max(boundLeft, score)))
                .collect(Collectors.toList());
    }

    /**
     * Get the colors for buckets.
     *
     * @return a list of colors.
     */
    private List<Color> getColors() {
        List<Double> newScores = correctScoreOutliers();
        double max = Collections.max(newScores);
        return newScores.parallelStream()
                .map(score -> colorFromScore(score / max))
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

            // There is no other way but to create objects in a loop in this
            // case
            @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
            Stop stop = new Stop(offset, colors.get(index));
            stops.add(stop);
        }

        return stops;
    }

}
