package nl.tudelft.lifetiles.core.util;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class SetUtilsTest {

    private Set<Object> a;
    private Set<Object> b;

    @Before
    public void setup() {
        a = new HashSet<>();
        b = new HashSet<>();
    }

    @Test
    public void intersectionEmptyTest() {
        assertEquals(0, SetUtils.intersectionSize(a, b));
    }

    @Test
    public void intersectionLeftTest() {
        a.add("a");
        a.add("b");
        a.add("c");
        a.add("d");

        b.add("c");
        b.add("d");

        assertEquals(2, SetUtils.intersectionSize(a, b));
    }

    @Test
    public void intersectionRightTest() {
        a.add("c");
        a.add("d");

        b.add("c");
        b.add("d");
        b.add("e");
        b.add("f");

        assertEquals(2, SetUtils.intersectionSize(a, b));
    }

}
