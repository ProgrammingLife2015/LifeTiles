package nl.tudelft.lifetiles.sequence.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SegmentStringTest {

    private SegmentString s;

    @Before
    public void setup() {
        s = new SegmentString("ATCG");
    }
    
    @Test
    public void segmentLengthTest() {
        assertEquals(4, s.getLength());
    }
    
    @Test
    public void segmentEmptyTest() {
        assertFalse(s.isEmpty());
    }
    
    @Test
    public void segmentToStringTest() {
        assertEquals("ATCG", s.toString());
    }
    
}
