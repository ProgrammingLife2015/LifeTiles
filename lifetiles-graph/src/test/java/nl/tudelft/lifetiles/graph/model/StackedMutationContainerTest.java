package nl.tudelft.lifetiles.graph.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import nl.tudelft.lifetiles.sequence.model.DefaultSequence;
import nl.tudelft.lifetiles.sequence.model.SegmentEmpty;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

import org.junit.Before;
import org.junit.Test;

public class StackedMutationContainerTest {
    GraphFactory<SequenceSegment> gf;
    SequenceSegment v1, v2;
    Graph<SequenceSegment> gr;

    @Before
    public void setUp() {
        gf = FactoryProducer.getFactory("JGraphT");
        gr = gf.getGraph();

        Set<Sequence> s  = new HashSet<Sequence>();
        s.add(new DefaultSequence("reference"));

        v1 = new SequenceSegment(s, 1, 11,
                new SegmentEmpty(10));
        v1.setUnifiedStart(1);
        v1.setUnifiedEnd(11);

        v2 = new SequenceSegment(s, 31, 41,
                new SegmentEmpty(10));
        v2.setUnifiedStart(31);
        v2.setUnifiedEnd(41);
    }

    @Test
    public void exampleContentBucketTest() {
        BucketCache b = new BucketCache(8, gr);
        StackedMutationContainer s = new StackedMutationContainer(b, null);
        assertEquals(8, s.mapLevelStackedMutation().get(1).getStack().size());
        assertEquals(4, s.mapLevelStackedMutation().get(2).getStack().size());
        assertEquals(2, s.mapLevelStackedMutation().get(3).getStack().size());
        assertEquals(1, s.mapLevelStackedMutation().get(4).getStack().size());
        assertEquals(1, s.getStack().size());
    }

    @Test
    public void singleBucketSingleContentTest() {
        gr.addVertex(v1);
        BucketCache b = new BucketCache(1, gr);
        StackedMutationContainer s = new StackedMutationContainer(b, null);
        ArrayList<Long> stack = new ArrayList<Long>();
        stack.add((long) 10);
        stack.add((long) 0);
        stack.add((long) 0);
        stack.add((long) 0);
        assertEquals(stack, s.getStack().get(0));
    }

    @Test
    public void singleBucketMultipleContentTest() {
        gr.addVertex(v1);
        gr.addVertex(v2);
        BucketCache b = new BucketCache(1, gr);
        StackedMutationContainer s = new StackedMutationContainer(b, null);
        ArrayList<Long> stack = new ArrayList<Long>();
        stack.add((long) 20);
        stack.add((long) 0);
        stack.add((long) 0);
        stack.add((long) 0);
        assertEquals(stack, s.getStack().get(0));
    }

    @Test
    public void doubleBucketMultipleContentTest() {
        gr.addVertex(v1);
        gr.addVertex(v2);
        BucketCache b = new BucketCache(2, gr);
        StackedMutationContainer s = new StackedMutationContainer(b, null);

        ArrayList<Long> stack = new ArrayList<Long>();
        stack.add((long) 10);
        stack.add((long) 0);
        stack.add((long) 0);
        stack.add((long) 0);
        assertEquals(stack, s.mapLevelStackedMutation().get(1).getStack()
                .get(0));
        assertEquals(stack, s.mapLevelStackedMutation().get(1).getStack()
                .get(1));

        stack = new ArrayList<Long>();
        stack.add((long) 20);
        stack.add((long) 0);
        stack.add((long) 0);
        stack.add((long) 0);
        assertEquals(stack, s.getStack().get(0));
    }

    @Test
    public void multipleBucketMultipleContentTest() {
        gr.addVertex(v1);
        gr.addVertex(v2);
        BucketCache b = new BucketCache(1000, gr);
        StackedMutationContainer s = new StackedMutationContainer(b, null);

        ArrayList<Long> stack = new ArrayList<Long>();
        stack.add((long) 20);
        stack.add((long) 0);
        stack.add((long) 0);
        stack.add((long) 0);
        assertEquals(stack, s.getStack().get(0));
    }

    @Test
    public void emptyBucketTest() {
        BucketCache b = new BucketCache(1, gr);
        StackedMutationContainer s = new StackedMutationContainer(b, null);
        assertEquals(1, s.mapLevelStackedMutation().size());
    }

    @Test
    public void emptyDoubleBucketTest() {
        BucketCache b = new BucketCache(2, gr);
        StackedMutationContainer s = new StackedMutationContainer(b, null);
        assertEquals(2, s.mapLevelStackedMutation().size());
    }

    @Test
    public void emptyMultipleBucketTest() {
        BucketCache b = new BucketCache(1024, gr);
        StackedMutationContainer s = new StackedMutationContainer(b, null);
        assertEquals(11, s.mapLevelStackedMutation().size());
    }

}
