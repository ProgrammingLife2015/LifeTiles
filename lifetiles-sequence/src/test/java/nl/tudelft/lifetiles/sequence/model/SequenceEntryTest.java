package nl.tudelft.lifetiles.sequence.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class SequenceEntryTest {

    Sequence s;
    SequenceEntry se1, se2;

    @Before
    public void setUp() {
        s = new DefaultSequence("s");
        se1 = SequenceEntry.fromSequence(s);
        se2 = new SequenceEntry("potato", true, true);
    }

    @Test
    public void getIdentifierTest1() {
        assertEquals(s.getIdentifier(), se1.getIdentifier());
    }

    @Test
    public void getIdentifierTest2() {
        assertEquals("potato", se2.getIdentifier());
    }

    @Test
    public void getVisibleTest1() {
        assertTrue(se1.isVisible());
    }

    @Test
    public void getVisibleTest2() {
        assertTrue(se2.isVisible());
    }

    @Test
    public void getReferenceTest1() {
        assertFalse(se1.isReference());
    }

    @Test
    public void getReferenceTest2() {
        assertTrue(se2.isReference());
    }

    @Test
    public void setVisibleTest() {
        se1.setVisible(false);
        assertFalse(se1.isVisible());
        se1.setVisible(true);
    }

    @Test
    public void setReferenceTest() {
        se1.setReference(true);
        assertTrue(se1.isReference());
        se1.setReference(false);
    }

    @Test
    public void getMetadataTest() {
        HashMap<String, String> metaData = new HashMap<String, String>();
        metaData.put("a", "1");
        metaData.put("b", "2");
        metaData.put("c", "3");
        se1.setMetaData(metaData);
        assertEquals("2", se1.getMeta("b"));
    }

    @Test
    public void getMetaPropertyTest() {
        Map<String, String> metaData = new HashMap<String, String>();
        metaData.put("a", "1");
        metaData.put("b", "2");
        metaData.put("c", "3");
        se1.setMetaData(metaData);
        String value = se1.metaProperty("b").getValue();
        assertEquals("2", value);
    }
}
