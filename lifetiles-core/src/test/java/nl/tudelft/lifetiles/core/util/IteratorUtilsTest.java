package nl.tudelft.lifetiles.core.util;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

public class IteratorUtilsTest {

    @Test
    public void iterableTest() {
        List<Integer> list = new ArrayList<>();
        list.add(10);
        Iterable<Integer> iterable = IteratorUtils.toIterable(list.iterator());
        assertEquals(list.iterator().next(), iterable.iterator().next());
    }

}
