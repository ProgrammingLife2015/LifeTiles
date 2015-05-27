package nl.tudelft.lifetiles.bucket.model;

import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

/**
 * A factory to produce a bucket cache.
 *
 * @author Jos
 *
 */
public class BucketCacheFactory {

    /**
     * Constructs a bucket cache.
     *
     * @param n
     *            Number of buckets to divide the graph in.
     * @param graph
     *            Graph to insert into the bucket cache.
     * @return constructed bucket cache with cached graph in n buckets.
     */
    public final BucketCache getBucketCache(final int n,
            final Graph<SequenceSegment> graph) {
        return new BucketCache(n, graph);
    }

}
