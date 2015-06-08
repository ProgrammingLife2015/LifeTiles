package nl.tudelft.lifetiles.sequence.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import nl.tudelft.lifetiles.graph.view.Mutation;

import org.junit.Before;
import org.junit.Test;

public class SequenceSegmentTest {

    SequenceSegment v1, v2, v3;

    @Before
    public void setUp() throws Exception {
        v1 = new SequenceSegment(new TreeSet<Sequence>(), 1, 10, new SegmentString("AAAAAAAAAA"));
        v1.setUnifiedStart(1);
        v1.setUnifiedEnd(10);
        v1.setSources(new HashSet<>(Arrays.asList(new DefaultSequence("s1"))));

        v2 = new SequenceSegment(new TreeSet<Sequence>(), 11, 20, new SegmentEmpty(10));
        v2.setUnifiedStart(11);
        v2.setUnifiedEnd(20);
        v2.setSources(new HashSet<>(Arrays.asList(new DefaultSequence("s2"))));

        v3 = new SequenceSegment(new TreeSet<Sequence>(), 21, 30, new SegmentString("AAAAANNNNN"));
        v3.setUnifiedStart(21);
        v3.setUnifiedEnd(30);
        v3.setSources(new HashSet<>(Arrays.asList(new DefaultSequence("s3"))));
    }

    @Test
    public void testDistanceToAdjacent() {
        assertEquals(0, v1.distanceTo(v2));
    }

    @Test
    public void testDistanceToPositive() {
        assertEquals(10, v1.distanceTo(v3));
    }

    @Test
    public void testDistanceToNegative() {
        assertEquals(-30, v3.distanceTo(v1));
    }

    @Test
    public void testLengthEmpty() {
        assertEquals(10, v2.getContent().getLength());
    }

    @Test
    public void testLengthString() {
        assertEquals(10, v1.getContent().getLength());
    }

    @Test
    public void testMutation() {
        Mutation insertion = Mutation.INSERTION;
        v1.setMutation(insertion);
        assertEquals(insertion, v1.getMutation());
    }

    @Test
    public void testCompare() {
        assertEquals(-1, v1.compareTo(v2));
    }

    @Test
    public void testCompareModify() {
        Set<SequenceSegment> segments = new HashSet<>();
        segments.add(v1);
        v1.setUnifiedStart(2);
        assertTrue(segments.contains(v1));
    }

    @Test
    public void testInterestingness1() {
        assertTrue(v1.interestingness() > v2.interestingness());
    }

    @Test
    public void testInterestingness2() {
        assertTrue(v1.interestingness() > v3.interestingness());
    }

    @Test
    public void testInterestingness3() {
        assertTrue(v3.interestingness() > v2.interestingness());
    }
}
