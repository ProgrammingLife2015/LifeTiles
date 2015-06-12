package nl.tudelft.lifetiles.sequence.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        assertTrue(se1.getVisible());
    }

    @Test
    public void getVisibleTest2() {
        assertTrue(se2.getVisible());
    }

    @Test
    public void getReferenceTest1() {
        assertFalse(se1.getReference());
    }

    @Test
    public void getReferenceTest2() {
        assertTrue(se2.getReference());
    }

    @Test
    public void setVisibleTest() {
        se1.setVisible(false);
        assertFalse(se1.getVisible());
        se1.setVisible(true);
    }

    @Test
    public void setReferenceTest() {
        se1.setReference(true);
        assertTrue(se1.getReference());
        se1.setReference(false);
    }
}
