package nl.tudelft.lifetiles.core.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TypeUtilsTest {

    @Test
    public void testUnsignedByteValue() {
        byte testByte = -12;
        int expected = 244;
        assertEquals(expected, TypeUtils.unsignedByteValue(testByte));
    }

}
