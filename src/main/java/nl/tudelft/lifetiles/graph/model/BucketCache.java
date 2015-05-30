package nl.tudelft.lifetiles.graph.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import nl.tudelft.lifetiles.graph.model.Graph;
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
    private Graph<SequenceSegment> graphVar;

    /**
     * Number of buckets the graph is divided in.
     */
    private int numberBuckets;

    /**
     * Buckets used to store the sequenceSegments.
     */
    private List<SortedSet<SequenceSegment>> buckets;

    /**
     * Width of a bucket based on the width of the graph and the number of
     * buckets being cached.
     */
    private long bucketWidth;

    /**
     * Max unified end coordinate position in the graph, should be stored
     * because it is called on every view update.
     */
    private long maxUnifiedEnd;

    /**
     * Constructs a bucket cache and divides the graph into n buckets.
     *
     * @param n
     *            Number of buckets the graph is divided in.
     * @param graph
     *            Graph that needs to be divided.
     */
    public BucketCache(final int n, final Graph<SequenceSegment> graph) {
        numberBuckets = n;
        graphVar = graph;
        maxUnifiedEnd = getMaxUnifiedEnd();
        bucketWidth = maxUnifiedEnd / numberBuckets + 1;
        cacheGraph();
    }

    /**
     * Caches the graph that has been inserted into the bucket cache into the
     * number of buckets the graph should be divided in.
     */
    private void cacheGraph() {
        long startTime = Calendar.getInstance().getTimeInMillis();
        buckets = new ArrayList<SortedSet<SequenceSegment>>();
        for (int index = 0; index < numberBuckets; index++) {
            buckets.add(index, new TreeSet<SequenceSegment>());
        }
        for (SequenceSegment vertex : graphVar.getAllVertices()) {
            cacheVertex(vertex);
        }
        System.out.println("Graph caching. Took "
                + (Calendar.getInstance().getTimeInMillis() - startTime)
                + " ms.");
    }

    /**
     * Caches the vertex to the bucket cache, a vertex can be in multiple
     * buckets in the bucket cache.
     *
     * @param vertex
     *            The vertex to cache in the buckets in the cache.
     */
    private void cacheVertex(final SequenceSegment vertex) {
        int startBucket = (int) Math.floor((double) vertex.getUnifiedStart()
                / bucketWidth);
        int endBucket = (int) Math.floor((double) vertex.getUnifiedEnd()
                / bucketWidth);
        for (int bucket = startBucket; bucket <= endBucket; bucket++) {
            buckets.get(bucket).add(vertex);
        }
    }

    /**
     * Get the maximal unified end position based on the sinks of the graph.
     *
     * @return the maximal unified end position.
     */
    private long getMaxUnifiedEnd() {
        long max = 0;
        for (SequenceSegment vertex : graphVar.getSinks()) {
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
     *
     * @param position
     *            Position of the domain.
     * @return set of sequence segments on the domain.
     */
    public final Set<SequenceSegment> getSegments(final int position) {
        return buckets.get(position);
    }

    /**
     * Returns the position in the bucketCache given the percentage position in
     * the GraphController.
     *
     * @param position
     *            Percentage position in the GraphController
     * @return position in the bucketCache.
     */
    public final int bucketPosition(final double position) {
        return (int) Math.floor((position * maxUnifiedEnd) / bucketWidth);
    }
}
