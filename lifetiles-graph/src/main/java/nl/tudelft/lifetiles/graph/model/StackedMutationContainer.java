package nl.tudelft.lifetiles.graph.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.tudelft.lifetiles.core.util.SetUtils;
import nl.tudelft.lifetiles.sequence.Mutation;
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
     * Mapping value of the insertion mutation.
     */
    private static final Integer INSERTION_MAP = 1;

    /**
     * Mapping value of the deletion mutation.
     */
    private static final Integer DELETION_MAP = 2;

    /**
     * Mapping value of the polymorphism mutation.
     */
    private static final Integer POLYMORPHISM_MAP = 3;

    /**
     * The number of values being counted by the stacked mutation container.
     */
    private static final int NUMBER_OF_VALUES = 4;

    /**
     * List with the stacked quantity of mutations.
     * <dl>
     * <dt>[0]</dt>
     * <dd>total bases.</dd>
     * <dt>[1]</dt>
     * <dd>insertion bases.</dd>
     * <dt>[2]</dt>
     * <dd>deletion bases.</dd>
     * <dt>[3]</dt>
     * <dd>polymorphism bases.</dd>
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
     * Public constructor for stacked mutation container. Will construct the
     * stacked mutation container by calculating the level in comparison with
     * the bucketCache.
     *
     * @param buckets
     *            BucketCache to be used to generate the stacked mutation
     *            container.
     * @param visibleSequences
     *            The visible sequences in the graph controller.
     */
    public StackedMutationContainer(final BucketCache buckets,
            final Set<Sequence> visibleSequences) {
        this.level = (int) Math.round(Math.log(buckets.getNumberBuckets())
                / Math.log(2) + 1);
        fillStackedMutationContainer(this.level, buckets, visibleSequences);
    }

    /**
     * Private constructor for stacked mutation container. Will construct the
     * stacked mutation container based on the given level by the parent stacked
     * mutation container.
     *
     * @param level
     *            The level of this stacked mutation container.
     * @param buckets
     *            The bucketCache to insert into this stackedMutationContainer.
     * @param visibleSequences
     *            The visible sequences in the graph controller.
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
        stackedMutations = new ArrayList<>();
        if (this.level <= 1) {
            child = null;
            insertBuckets(buckets, visibleSequences);
        } else {
            child = new StackedMutationContainer(level - 1, buckets,
                    visibleSequences);
            insertStackedMutationContainers(child);
        }
    }

    /**
     * Insert the child stacked Mutation Container. Merges the layer of the
     * child.
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
        List<Long> stack = new ArrayList<>(left.size());
        for (int index = 0; index < left.size(); index++) {
            stack.add(left.get(index) + right.get(index));
        }
        return stack;
    }

    /**
     * Returns all columns in the stacked mutation diagram.
     *
     * @return all columns in the stacked mutation diagram.
     */
    public final List<List<Long>> getStack() {
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
            final Set<Sequence> visibleSequences) {
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
            final Set<Sequence> visibleSequences) {
        List<Long> list = new ArrayList<>(NUMBER_OF_VALUES);
        for (int index = 0; index < NUMBER_OF_VALUES; index++) {
            list.add((long) 0);
        }

        Map<Mutation, Integer> mutations = new HashMap<>(
                Mutation.values().length);
        mutations.put(Mutation.INSERTION, INSERTION_MAP);
        mutations.put(Mutation.DELETION, DELETION_MAP);
        mutations.put(Mutation.POLYMORPHISM, POLYMORPHISM_MAP);

        for (SequenceSegment segment : bucket) {
            int sourceSize;
            if (visibleSequences == null) {
                sourceSize = segment.getSources().size();
            } else {
                sourceSize = SetUtils.intersectionSize(segment.getSources(),
                        visibleSequences);
            }

            long size = segment.getContent().getLength() * sourceSize;
            if (mutations.containsKey(segment.getMutation())) {
                int index = mutations.get(segment.getMutation());
                list.set(index, list.get(index) + size);
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
    public final Long getMaxMutations() {
        long max = 0;
        for (List<Long> stack : stackedMutations) {
            max = Math.max(
                    max,
                    stack.get(INSERTION_MAP) + stack.get(DELETION_MAP)
                            + stack.get(POLYMORPHISM_MAP));
        }
        return max;
    }

    /**
     * Returns a map from level to stacked container.
     *
     * @return map from level to stacked container.
     */
    public final Map<Integer, StackedMutationContainer> mapLevelStackedMutation() {
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
    public final int getLevel() {
        return level;
    }

}
