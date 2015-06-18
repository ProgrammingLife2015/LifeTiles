package nl.tudelft.lifetiles.sequence.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import nl.tudelft.lifetiles.sequence.Mutation;

import org.junit.Before;
import org.junit.Test;

public class SequenceSegmentTest {

    SequenceSegment v1, v2, v3;

    @Before
    public void setUp() throws Exception {
        v1 = new SequenceSegment(new TreeSet<Sequence>(), 1, 10,
                new SegmentString("AAAAAAAAAA"));
        v1.setUnifiedStart(1);
        v1.setUnifiedEnd(10);
        v1.setSources(new HashSet<>(Arrays.asList(new DefaultSequence("s1"))));

        v2 = new SequenceSegment(new TreeSet<Sequence>(), 11, 20,
                new SegmentEmpty(10));
        v2.setUnifiedStart(11);
        v2.setUnifiedEnd(20);
        v2.setSources(new HashSet<>(Arrays.asList(new DefaultSequence("s2"))));

        v3 = new SequenceSegment(new TreeSet<Sequence>(), 21, 30,
                new SegmentString("AAAAANNNNN"));
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
    public void testCompareNull() {
        assertEquals(0, v1.compareTo(v1));
    }

    @Test
    public void testCompareModify() {
        Set<SequenceSegment> segments = new HashSet<>();
        segments.add(v1);
        v1.setUnifiedStart(2);
        assertTrue(segments.contains(v1));
    }

    @Test
    public void testSegmentStart() {
        assertEquals(1, v1.getStart());
    }

    @Test
    public void testSegmentEnd() {
        assertEquals(10, v1.getEnd());
    }

    @Test
    public void testSegmentSetUnifiedStart() {
        v1.setUnifiedStart(11);
        assertEquals(11, v1.getUnifiedStart());
    }

    @Test
    public void testSegmentSetUnifiedEnd() {
        v1.setUnifiedEnd(20);
        assertEquals(20, v1.getUnifiedEnd());
    }

    @Test
    public void testSegmentSetReferenceStart() {
        v1.setReferenceStart(11);
        assertEquals(11, v1.getReferenceStart());
    }

    @Test
    public void testSegmentSetReferenceEnd() {
        v1.setReferenceEnd(20);
        assertEquals(20, v1.getReferenceEnd());
    }

    @Test
    public void testSegmentSources() {
        assertEquals(new HashSet<>(Arrays.asList(new DefaultSequence("s1"))),
                v1.getSources());
    }

    @Test
    public void testConstructCopy() {
        SequenceSegment vc = new SequenceSegment(v1);
        assertEquals(v1, vc);
    }

    @Test
    public void equalsNullTest() {
        assertFalse(v2.equals(null));
    }

    @Test
    public void equalsOtherTypeTest() {
        assertFalse(v2.equals(10));
    }

    @Test
    public void emptyInterestingnessTest() {
        assertEquals(0, v2.interestingness(), 1E-05);
    }

    @Test
    public void determinePolymorphismMutationTest() {
        assertEquals(Mutation.POLYMORPHISM, v1.determineMutation());
    }

    @Test
    public void determineInsertionMutationTest() {
        v3.setReferenceStart(100);
        v3.setReferenceEnd(0);
        assertEquals(Mutation.INSERTION, v3.determineMutation());
    }

    @Test
    public void determineDeletionMutationTest() {
        assertEquals(Mutation.DELETION, v2.determineMutation());
    }

    @Test
    public void interestingnessOrderTest() {
        assertTrue(v2.interestingness() < v1.interestingness());
        assertTrue(v3.interestingness() < v1.interestingness());
        assertTrue(v3.interestingness() > v2.interestingness());
    }
}
