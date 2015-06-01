package nl.tudelft.lifetiles.graph.model;

import java.util.Set;

import nl.tudelft.lifetiles.core.util.Settings;
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
     * The setting key for empty segments.
     */
    private static final String SETTING_EMPTY = "empty_segments";

    /**
     * The setting key for mutation indication.
     */
    private static final String SETTING_MUTATION = "mutations";

    /**
     * The Current graph that this model is holding.
     */
    private Graph<SequenceSegment> graph;

    /**
     * The Current graph that this model is holding in bucket cache form.
     */
    private final BucketCache segmentBuckets;

    /**
     * The amount of buckets the graph is cached in.
     */
    private static final int NUMBER_OF_BUCKETS = 1000;

    /**
     * create a new Tile.
     *
     * @param graph
     *            The initial graph
     */
    public GraphContainer(final Graph<SequenceSegment> graph) {
        this.graph = graph;

        // TODO: Temporary line until sequence selection is implemented.
        Sequence reference = this.graph.getSources().iterator().next()
                .getSources().iterator().next();

        alignGraph();
        findMutations(reference);

        segmentBuckets = new BucketCache(NUMBER_OF_BUCKETS, this.graph);
    }

    /**
     * Align the graph.
     */
    private void alignGraph() {
        UnifiedPositionTraverser upt = new UnifiedPositionTraverser();
        graph = upt.unifyGraph(graph);

        if (Settings.getBoolean(SETTING_EMPTY)) {
            EmptySegmentTraverser est = new EmptySegmentTraverser();
            graph = est.addEmptySegmentsGraph(graph);
        }
    }

    /**
     * Find the mutations on the graph.
     *
     * @param reference
     *            Reference of the graph which is used to indicate mutations.
     */
    private void findMutations(final Sequence reference) {
        if (!Settings.getBoolean(SETTING_MUTATION)) {
            return;
        }
        new ReferencePositionTraverser(reference).referenceMapGraph(graph);
        new MutationIndicationTraverser(reference)
        .indicateGraphMutations(graph);
    }

    /**
     * Get the graph that this model is holding.
     *
     * @param position
     *            bucket position in the scrollPane.
     * @return graph
     */
    public final Set<SequenceSegment> getSegments(final int position) {
        return segmentBuckets.getSegments(position);
    }

    /**
     * Returns the bucketCache to check the current position.
     *
     * @return the bucketCache of the graph.
     */
    public final BucketCache getBucketCache() {
        return segmentBuckets;
    }

}
