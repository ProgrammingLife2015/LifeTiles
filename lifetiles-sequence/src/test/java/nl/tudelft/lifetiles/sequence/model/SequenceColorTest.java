package nl.tudelft.lifetiles.sequence.model;

import nl.tudelft.lifetiles.sequence.SequenceColor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class SequenceColorTest {

    private DefaultSequence sequence;

    @Before
    public void setup() {
        sequence = new DefaultSequence("test");
    }

    @Test
    public void sequenceColorTest() {
        assertNotNull(SequenceColor.getColor(sequence));
    }
}
