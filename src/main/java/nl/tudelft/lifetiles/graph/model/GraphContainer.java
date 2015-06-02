package nl.tudelft.lifetiles.graph.model;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

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
     * The set of currently visible sequencesegments.
     */
    private Set<SequenceSegment> visibles;

    /**
     * The set of visible sequences.
     */
    private Set<Sequence> visibleSequences;

    /**
     * Boolean indicating if the graph in this container is recently changed.
     */
    private boolean changed;

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
        visibles = graph.getAllVertices();

        changed = false;
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
     * Change the graph by selecting the sequences to draw.
     *
     * @param visibleSequences
     *            the sequences to display
     */
    public final void setVisible(final Set<Sequence> visibleSequences) {

        // Find out which vertices are visible now
        Set<SequenceSegment> vertices = new TreeSet<SequenceSegment>();
        for (Sequence seq : visibleSequences) {
            Iterator<SequenceSegment> itSegments = seq.getSegments().iterator();
            while (itSegments.hasNext()) {
                vertices.add(itSegments.next());
            }
        }

        visibles = vertices;
        this.visibleSequences = visibleSequences;

        changed = true;

    }

    /**
     * Get the graph that this model is holding.
     *
     * @param position
     *            bucket position in the scrollPane.
     * @return graph
     */
    public final Set<SequenceSegment> getSegments(final int position) {

        Set<SequenceSegment> copy = new TreeSet<SequenceSegment>();
        for (SequenceSegment seg : segmentBuckets.getSegments(position)) {
            copy.add(seg.clone());
        }
        // Keep only the sequencesegments that are visible
        copy.retainAll(visibles);

        // Set the sources so they only contain the visible sequences
        if (visibleSequences != null) {
            for (SequenceSegment vertex : copy) {
                vertex.getSources().retainAll(visibleSequences);
            }
        }

        return copy;
    }

    /**
     * Get if the graph in this container is recently changed.
     *
     * @return true if recently changed, else false
     */
    public final boolean isChanged() {
        if (changed) {
            changed = false;
            return true;
        }
        return changed;
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
