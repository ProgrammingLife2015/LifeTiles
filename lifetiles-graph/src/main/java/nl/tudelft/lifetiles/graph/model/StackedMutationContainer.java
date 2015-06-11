package nl.tudelft.lifetiles.graph.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.tudelft.lifetiles.sequence.model.Sequence;
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
     * <dl>
     * <dt>[0] -> total bases.</dt>
     * <dt>[1] -> insertion bases.</dt>
     * <dt>[2] -> deletion bases.</dt>
     * <dt>[3] -> polymorphism bases.</dt>
     * </dl>
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
    public StackedMutationContainer(final BucketCache buckets,
            final Set<Sequence> visibleSequences) {
        this.level = (int) (Math.log(buckets.getNumberBuckets()) / Math.log(2) + 1);
        fillStackedMutationContainer(this.level, buckets, visibleSequences);
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
    private StackedMutationContainer(final int level,
            final BucketCache buckets, final Set<Sequence> visibleSequences) {
        this.level = level;
        fillStackedMutationContainer(this.level, buckets, visibleSequences);
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
     * @param visibleSequences
     *            The visible sequences in the graph.
     */
    private void fillStackedMutationContainer(final int level,
            final BucketCache buckets, final Set<Sequence> visibleSequences) {
        this.level = level;
        if (this.level <= 1) {
            child = null;
            stackedMutations = new ArrayList<List<Long>>();
            insertBuckets(buckets, visibleSequences);
        } else {
            child = new StackedMutationContainer(level - 1, buckets,
                    visibleSequences);
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
        List<Long> stack = new ArrayList<Long>();
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
     * @param visibleSequences
     *            The visible sequences in the graph.
     */
    private void insertBuckets(final BucketCache buckets,
            Set<Sequence> visibleSequences) {
        for (Set<SequenceSegment> bucket : buckets.getBuckets()) {
            stackedMutations.add(insertBucket(bucket, visibleSequences));
        }
    }

    /**
     * Insert the bucket into the mutation quantity list.
     *
     * @param bucket
     *            Bucket from bucketCache which needs to be added to the stacked
     *            mutation container.
     * @param visibleSequences
     *            The visible sequences in the graph.
     * @return stacked mutation quantity list for this bucket.
     */
    private List<Long> insertBucket(final Set<SequenceSegment> bucket,
            Set<Sequence> visibleSequences) {
        List<Long> list = new ArrayList<Long>(4);
        list.add((long) 0);
        list.add((long) 0);
        list.add((long) 0);
        list.add((long) 0);

        for (SequenceSegment segment : bucket) {
            Set<Sequence> sources = new HashSet<>(segment.getSources());
            if (visibleSequences != null) {
                sources.retainAll(visibleSequences);
            }
            long size = segment.getContent().getLength() * sources.size();
            if (segment.getMutation() != null) {
                switch (segment.getMutation()) {
                case INSERTION:
                    list.set(1, list.get(1) + size);
                    break;
                case DELETION:
                    list.set(2, list.get(2) + size);
                    break;
                case POLYMORPHISM:
                    list.set(3, list.get(3) + size);
                    break;
                default: // noop
                    break;
                }
            }
            list.set(0, list.get(0) + size);
        }
        return list;
    }

    /**
     * Return the maximum number of mutations in one of the stacks.
     *
     * @return the maximum number of mutations in one of the stacks.
     */
    public Long getMaxMutations() {
        long max = 0;
        for (List<Long> stack : stackedMutations) {
            max = Math.max(max, stack.get(1) + stack.get(2) + stack.get(3));
        }
        return max;
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
        map.put(getLevel(), this);
        return map;

    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

}
