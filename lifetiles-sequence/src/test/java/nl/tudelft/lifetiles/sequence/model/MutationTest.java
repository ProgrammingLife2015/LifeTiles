package nl.tudelft.lifetiles.sequence.model;

import static org.junit.Assert.assertNotNull;
import nl.tudelft.lifetiles.sequence.Mutation;

import org.junit.Test;

public class MutationTest {

    @Test
    public void getColorTest() {
        assertNotNull(Mutation.INSERTION.getColor());
        assertNotNull(Mutation.DELETION.getColor());
        assertNotNull(Mutation.POLYMORPHISM.getColor());
    }

}
