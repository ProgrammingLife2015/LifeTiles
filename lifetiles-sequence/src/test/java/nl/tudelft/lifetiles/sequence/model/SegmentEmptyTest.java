package nl.tudelft.lifetiles.sequence.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SegmentEmptyTest {

    private SegmentEmpty s;

    @Before
    public void setup() {
        s = new SegmentEmpty(4);
    }
    
    @Test
    public void segmentLengthTest() {
        assertEquals(4, s.getLength());
    }
    
    @Test
    public void segmentEmptyTest() {
        assertTrue(s.isEmpty());
    }
    
    @Test
    public void segmentToStringTest() {
        assertEquals("____", s.toString());
    }
    
}
