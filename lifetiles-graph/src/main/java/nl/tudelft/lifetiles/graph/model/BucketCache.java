package nl.tudelft.lifetiles.graph.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import nl.tudelft.lifetiles.core.util.Timer;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

/**
 * A bucket cache which adds the sequence segments to buckets for improved
 * graph drawing performance.
 *
 * @author Jos
 *
 */
public class BucketCache {

    /**
     * Graph that has been inserted into the bucket cache.
     */
    private final Graph<SequenceSegment> graph;

    /**
     * Number of buckets the graph is divided in.
     */
    private final int numberBuckets;

    /**
     * Buckets used to store the sequenceSegments.
     */
    private List<SortedSet<SequenceSegment>> buckets;

    /**
     * Width of a bucket based on the width of the graph and the number of
     * buckets being cached.
     */
    private final long bucketWidth;

    /**
     * Max unified end coordinate position in the graph, should be stored
     * because it is called on every view update.
     */
    private final long maxUnifiedEnd;

    /**
     * Constructs a bucket cache and divides the graph into n buckets.
     *
     * @param numberBuckets
     *            Number of buckets the graph is divided in.
     * @param graph
     *            Graph that needs to be divided.
     */
    public BucketCache(final int numberBuckets,
            final Graph<SequenceSegment> graph) {
        // Number of buckets is ceiled to a power of 2. Needed for diagram view.
        this.numberBuckets = (int) Math.round(Math.pow(2, Math.ceil(Math.log(numberBuckets) / Math.log(2))));
        this.graph = graph;
        maxUnifiedEnd = getMaxUnifiedEnd();
        bucketWidth = maxUnifiedEnd / this.numberBuckets;
        cacheGraph();
    }

    /**
     * Caches the graph that has been inserted into the bucket cache into the
     * number of buckets the graph should be divided in.
     */
    private void cacheGraph() {
        Timer timer = Timer.getAndStart();
        buckets = new ArrayList<SortedSet<SequenceSegment>>();
        for (int index = 0; index < numberBuckets; index++) {
            buckets.add(index, new TreeSet<SequenceSegment>());
        }
        for (SequenceSegment vertex : graph.getAllVertices()) {
            cacheVertex(vertex);
        }

        timer.stopAndLog("Graph caching");
    }

    /**
     * Caches the vertex to the bucket cache, a vertex can be in multiple
     * buckets in the bucket cache.
     *
     * @param vertex
     *            The vertex to cache in the buckets in the cache.
     */
    private void cacheVertex(final SequenceSegment vertex) {
        int startBucket = bucketStartPosition(vertex.getUnifiedStart());
        int endBucket = bucketEndPosition(vertex.getUnifiedEnd());
        for (SortedSet<SequenceSegment> bucket : buckets.subList(startBucket,
                endBucket)) {
            bucket.add(vertex);
        }
        if (startBucket == endBucket) {
            buckets.get(startBucket - 1).add(vertex);
        }
    }

    /**
     * Get the maximal unified end position based on the sinks of the graph.
     *
     * @return the maximal unified end position.
     */
    private long getMaxUnifiedEnd() {
        long max = 0;
        for (SequenceSegment vertex : graph.getSinks()) {
            if (max < vertex.getUnifiedEnd()) {
                max = vertex.getUnifiedEnd();
            }
        }
        return max;
    }

    /**
     * Returns number of buckets the graph is divided in.
     *
     * @return number of buckets the graph is divided in.
     */
    public final int getNumberBuckets() {
        return numberBuckets;
    }

    /**
     * Returns the list of the sortedSet of sequence segments.
     *
     * @return graph that has been inserted into the bucket cache.
     */
    public final List<SortedSet<SequenceSegment>> getBuckets() {
        return buckets;
    }

    /**
     * Returns the set of sequence segments on a certain domain.
     * This will all sequence segment from the starting bucket to the ending
     * bucket.
     *
     * @param start
     *            the minimal Bucket to search on the domain
     * @param end
     *            the maximal Bucket to search on the domain
     * @return set of sequence segments on the domain.
     */
    public final Set<SequenceSegment> getSegments(final int start, final int end) {
        Set<SequenceSegment> set = new TreeSet<SequenceSegment>();
        for (Set<SequenceSegment> bucket : buckets.subList(Math.max(0, start), Math.min(numberBuckets, end))) {
            set.addAll(bucket);
        }
        return set;
    }

    /**
     * Returns the position in the bucketCache given the percentage position in
     * the GraphController.
     *
     * @param position
     *            Percentage position in the GraphController
     * @return position in the bucketCache.
     */
    public final int bucketPercentageStartPosition(final double position) {
        return (int) ((position * maxUnifiedEnd) / bucketWidth);
    }

    /**
     * Returns the minimal bucket it can find in the bucketCache, given a
     * position relative to the screen.
     *
     * @param position
     *            relative position on the screen
     * @return position in the bucketCache.
     */
    public final int bucketStartPosition(final double position) {
        return (int) Math.min(numberBuckets, Math
                .max(0, position / bucketWidth));
    }

    /**
     * Returns the maximal bucket it can find in the bucketCache, given a
     * position relative to the screen.
     *
     * @param position
     *            relative position on the screen
     * @return position in the bucketCache.
     */
    public final int bucketEndPosition(final double position) {
        return (int) Math.min(numberBuckets, Math.ceil(Math.max(0, position
                / bucketWidth)));
    }
}
