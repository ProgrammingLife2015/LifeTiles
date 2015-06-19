package nl.tudelft.lifetiles.sequence.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SegmentStringCollapsedTest {

    @Test
    public void lengthSmallestTest() {
        SegmentStringCollapsed ssc = new SegmentStringCollapsed(
                new SegmentEmpty(2));
        assertEquals(1, ssc.getLength());
    }

    @Test
    public void lengthSmallTest() {
        SegmentStringCollapsed ssc = new SegmentStringCollapsed(
                new SegmentEmpty(8));
        assertEquals(3, ssc.getLength());
    }

    @Test
    public void lengthBigTest() {
        SegmentStringCollapsed ssc = new SegmentStringCollapsed(
                new SegmentEmpty(1048576));
        assertEquals(20, ssc.getLength());
    }

    @Test
    public void notEmptyTest() {
        SegmentStringCollapsed ssc = new SegmentStringCollapsed(
                new SegmentString("XXX"));
        assertFalse(ssc.isEmpty());
    }

    @Test
    public void emptyTest() {
        SegmentStringCollapsed ssc = new SegmentStringCollapsed(
                new SegmentEmpty(1048576));
        assertTrue(ssc.isEmpty());
    }

    @Test
    public void smallToStringTest() {
        SegmentStringCollapsed ssc = new SegmentStringCollapsed(
                new SegmentEmpty(10));
        assertEquals("<10b>", ssc.toString());
    }

    @Test
    public void bigToStringTest() {
        SegmentStringCollapsed ssc = new SegmentStringCollapsed(
                new SegmentEmpty(3000));
        assertEquals("<3kb>", ssc.toString());
    }
}
