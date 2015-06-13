package nl.tudelft.lifetiles.annotation.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class KnownMutationTest {

    private KnownMutation annotation;

    @Before
    public void setUp() {
        annotation = new KnownMutation("a", "b", "c", "d", 0, "e");
    }

    @Test
    public void genomeNameTest() {
        assertEquals("a", annotation.getGeneName());
    }

    @Test
    public void mutationTypeTest() {
        assertEquals("b", annotation.getTypeOfMutation());
    }

    @Test
    public void changeTest() {
        assertEquals("c", annotation.getChange());
    }

    @Test
    public void filterTest() {
        assertEquals("d", annotation.getFilter());
    }

    @Test
    public void genomePositionTest() {
        assertEquals(0, annotation.getGenomePosition());
    }

    @Test
    public void drugResistanceTest() {
        assertEquals("e", annotation.getDrugResistance());
    }

    @Test
    public void toStringTest() {
        assertEquals("Gene Name: a" + System.lineSeparator()
                + "Gene Position: 0" + System.lineSeparator()
                + "Mutation Type: b" + System.lineSeparator() + "Change: c"
                + System.lineSeparator() + "Filter: d" + System.lineSeparator()
                + "Drug Resistance: e", annotation.toString());
    }

}
