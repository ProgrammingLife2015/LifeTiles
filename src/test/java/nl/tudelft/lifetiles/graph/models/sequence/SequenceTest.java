package nl.tudelft.lifetiles.graph.models.sequence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;

import nl.tudelft.lifetiles.graph.model.sequence.DefaultSequence;
import nl.tudelft.lifetiles.graph.model.sequence.SegmentEmpty;
import nl.tudelft.lifetiles.graph.model.sequence.Sequence;
import nl.tudelft.lifetiles.graph.model.sequence.SequenceSegment;

import org.junit.Before;
import org.junit.Test;

public class SequenceTest {
    Sequence s1, s2, s3;
    SequenceSegment ss1, ss2, ss3, ss4;

    @Before
    public void setUp() throws Exception {
        ss1 = new SequenceSegment(new HashSet<Sequence>(), 0, 1,
                new SegmentEmpty(1));
        ss2 = new SequenceSegment(new HashSet<Sequence>(), 1, 3,
                new SegmentEmpty(2));
        ss3 = new SequenceSegment(new HashSet<Sequence>(), 4, 6,
                new SegmentEmpty(3));
        ss4 = new SequenceSegment(new HashSet<Sequence>(), 7, 10,
                new SegmentEmpty(4));

    }

    @Test
    public void testConstruct() {
        s1 = new DefaultSequence("s1");
        assertTrue(s1 instanceof Sequence);
    }

    @Test
    public void testEmpty() {
        s1 = new DefaultSequence("s1");
        assertEquals(0, s1.getSegments().size());
    }

    @Test
    public void testAppend() {
        s1 = new DefaultSequence("s1");
        s1.appendSegment(ss1);
        s1.appendSegment(ss2);
        s1.appendSegment(ss3);
        s1.appendSegment(ss4);
        List<SequenceSegment> list = s1.getSegments();
        assertEquals(4, list.size());
        assertTrue(list.contains(ss1));
        assertTrue(list.contains(ss2));
        assertTrue(list.contains(ss3));
        assertTrue(list.contains(ss4));
    }

}
