package nl.tudelft.lifetiles.graph.model;

import java.util.SortedSet;

import nl.tudelft.lifetiles.bucket.model.BucketCache;
import nl.tudelft.lifetiles.bucket.model.BucketCacheFactory;
import nl.tudelft.lifetiles.graph.traverser.EmptySegmentTraverser;
import nl.tudelft.lifetiles.graph.traverser.MutationIndicationTraverser;
import nl.tudelft.lifetiles.graph.traverser.ReferencePositionTraverser;
import nl.tudelft.lifetiles.graph.traverser.UnifiedPositionTraverser;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

/**
 * The Tile holds the graph and will be transformed to this modelgraph so
 * that the graph can be drawn on the screen.
 *
 */
public class GraphContainer {

    /**
     * The Current graph that this model is holding.
     */
    private Graph<SequenceSegment> graph;

    /**
     * The Current graph that this model is holding in bucket cache form.
     */
    private BucketCache segmentBuckets;

    /**
     * create a new Tile.
     *
     * @param graph
     *            The initial graph
     */
    public GraphContainer(final Graph<SequenceSegment> graph) {
        this.graph = graph;

        // TODO: Temporary line until sequence selection is implemented.
<<<<<<< HEAD:src/main/java/nl/tudelft/lifetiles/graph/model/GraphContainer.java
        Sequence reference = this.graph.getSources().iterator().next()
                .getSources().iterator().next();

        alignGraph();
        // TODO: Improve reference position traversal. Currently disabled for
        // implementing filtering.
        // findMutations(reference);
=======
        Sequence reference = graph.getSources().iterator().next().getSources()
                .iterator().next();

        alignGraph();
        findMutations(reference);

        BucketCacheFactory bcf = new BucketCacheFactory();
        segmentBuckets = bcf.getBucketCache(10000, graph);
>>>>>>> Integrated BucketCache into Tile:src/main/java/nl/tudelft/lifetiles/graph/model/Tile.java
    }

    /**
     * Align the graph.
     */
    private void alignGraph() {
        UnifiedPositionTraverser upt = new UnifiedPositionTraverser();
        EmptySegmentTraverser est = new EmptySegmentTraverser();
        graph = est.addEmptySegmentsGraph(upt.unifyGraph(graph));
    }

    /**
     * Find the mutations on the graph.
     *
     * @param reference
     *            Reference of the graph which is used to indicate mutations.
     */
    private void findMutations(final Sequence reference) {
<<<<<<< HEAD:src/main/java/nl/tudelft/lifetiles/graph/model/GraphContainer.java
        new ReferencePositionTraverser(reference).referenceMapGraph(graph);
        new MutationIndicationTraverser(reference)
                .indicateGraphMutations(graph);
=======
        ReferencePositionTraverser rmt = new ReferencePositionTraverser(
                reference);
        MutationIndicationTraverser mt = new MutationIndicationTraverser(
                reference);
        graph = mt.indicateGraphMutations(rmt.referenceMapGraph(graph));
>>>>>>> Integrated BucketCache into Tile:src/main/java/nl/tudelft/lifetiles/graph/model/Tile.java
    }

    /**
     * Get the graph that this model is holding.
     * 
     * @param position
     *            bucket position in the scrollPane.
     * @return graph
     */
    public final SortedSet<SequenceSegment> getSegments(int position) {
        return segmentBuckets.getSegments(position);
    }

    /**
     * Returns the bucketCache to check the current position.
     * 
     * @return the bucketCache of the graph.
     */
    public BucketCache getBucketCache() {
        return segmentBuckets;
    }

}
