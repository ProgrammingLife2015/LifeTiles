package nl.tudelft.lifetiles.graph.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

/**
 * Container including the stacks with the mutation quantity used for drawing
 * the stacked mutation quantity diagram.
 *
 * @author Jos
 *
 */
public class StackedMutationContainer {

    /**
     * List with the stacked quantity of mutations.
     * [0] -> total bases.
     * [1] -> insertion bases.
     * [2] -> deletion bases.
     * [3] -> polymorphism bases.
     */
    private List<List<Long>> stackedMutations;

    /**
     * Child StackedMutationContainer, one level below this stacked mutation
     * container.
     */
    private StackedMutationContainer child;

    /**
     * Zoom/layer level of this stacked mutation container. Level corresponds to
     * number of columns inversed.
     */
    private int level;

    /**
     * Public constructor for stacked mutation container.
     * Will construct the stacked mutation container by calculating the level in
     * comparison with the bucketCache.
     *
     * @param buckets
     *            BucketCache to be used to generate the stacked mutation
     *            container.
     */
    public StackedMutationContainer(final BucketCache buckets) {
        level = (int) Math.ceil(Math.log(buckets.getNumberBuckets())
                / Math.log(2)) + 1;
        fillStackedMutationContainer(level, buckets);
    }

    /**
     * Private constructor for stacked mutation container.
     * Will construct the stacked mutation container based on the given level by
     * the parent stacked mutation container.
     *
     * @param level
     *            The level of this stacked mutation container.
     * @param buckets
     *            The bucketCache to insert into this stackedMutationContainer.
     */
    private StackedMutationContainer(final int level, final BucketCache buckets) {
        this.level = level;
        fillStackedMutationContainer(this.level, buckets);
    }

    /**
     * Fills this stacked mutation container with the bucketCache or the child
     * stacked mutation container based on the level given.
     *
     * @param level
     *            Level of this stacked mutation container.
     * @param buckets
     *            BucketCache to be inserted into this stacked mutation
     *            container.
     */
    private void fillStackedMutationContainer(final int level,
            final BucketCache buckets) {
        this.level = level;
        if (this.level <= 1) {
            child = null;
            stackedMutations = new ArrayList<List<Long>>();
            insertBuckets(buckets);
        } else {
            child = new StackedMutationContainer(level - 1, buckets);
            stackedMutations = new ArrayList<List<Long>>();
            insertStackedMutationContainers(child);
        }
    }

    /**
     * Insert the child stacked Mutation Container.
     * Merges the layer of the child.
     *
     * @param containers
     *            The child stacked mutation container.
     */
    private void insertStackedMutationContainers(
            final StackedMutationContainer containers) {
        for (int index = 0; index < containers.getStack().size() / 2; index++) {
            stackedMutations.add(insertStackedContainer(containers.getStack()
                    .get(2 * index), containers.getStack().get(2 * index + 1)));
        }
    }

    /**
     * Insert two sections of the child stacked Mutation Container into one
     * section of the stacked Mutation Container.
     *
     * @param left
     *            Left section of the child stacked mutation container.
     * @param right
     *            Right section of the child stacked mutation container.
     * @return merged section for this stacked mutation container.
     */
    private List<Long> insertStackedContainer(final List<Long> left,
            final List<Long> right) {
        ArrayList<Long> stack = new ArrayList<Long>();
        stack.add(left.get(0) + right.get(0));
        stack.add(left.get(1) + right.get(1));
        stack.add(left.get(2) + right.get(2));
        stack.add(left.get(3) + right.get(3));
        return stack;
    }

    /**
     * Returns all columns in the stacked mutation diagram.
     *
     * @return all columns in the stacked mutation diagram.
     */
    public List<List<Long>> getStack() {
        return stackedMutations;
    }

    /**
     * Insert the buckets into the mutation quantity list.
     *
     * @param buckets
     *            BucketCache which needs to be added to the stacked mutation
     *            container.
     */
    private void insertBuckets(final BucketCache buckets) {
        for (SortedSet<SequenceSegment> bucket : buckets.getBuckets()) {
            stackedMutations.add(insertBucket(bucket));
        }
    }

    /**
     * Insert the bucket into the mutation quantity list.
     *
     * @param bucket
     *            Bucket from bucketCache which needs to be added to the stacked
     *            mutation container.
     * @return
     *         Stacked mutation quantity list for this bucket.
     */
    private List<Long> insertBucket(final SortedSet<SequenceSegment> bucket) {
        ArrayList<Long> list = new ArrayList<Long>();
        long total = 0;
        long insertion = 0;
        long deletion = 0;
        long polymorphism = 0;
        for (SequenceSegment segment : bucket) {
            long size = segment.getContent().getLength();
            if (segment.getMutation() != null) {
                switch (segment.getMutation()) {
                case INSERTION:
                    insertion += size;
                    break;
                case DELETION:
                    deletion += size;
                    break;
                case POLYMORPHISM:
                    polymorphism += size;
                    break;
                default: // noop
                    break;
                }
            }
            total += size;
        }
        list.add(total);
        list.add(insertion);
        list.add(deletion);
        list.add(polymorphism);
        return list;
    }

    /**
     * Returns a map from level to stacked container.
     *
     * @return map from level to stacked container.
     */
    public Map<Integer, StackedMutationContainer> mapLevelStackedMutation() {
        Map<Integer, StackedMutationContainer> map;
        if (child == null) {
            map = new HashMap<>();
        } else {
            map = child.mapLevelStackedMutation();
        }
        map.put(level, this);
        return map;

    }

}
