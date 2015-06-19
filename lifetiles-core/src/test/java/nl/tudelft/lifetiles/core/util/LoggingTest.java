package nl.tudelft.lifetiles.core.util;

import java.util.logging.Level;

import org.junit.BeforeClass;
import org.junit.Test;

public class LoggingTest {

    @BeforeClass
    public static void before() {
        Logging.setLevel(Level.OFF);
    }

    @Test
    public void warningTest() {
        Logging.warning("test warning");
    }

    @Test
    public void infoTest() {
        Logging.info("test info");
    }

    @Test
    public void severeTest() {
        Logging.severe("test severe");
    }

    @Test
    public void exceptionTest() {
        Logging.exception(new Exception());
    }

}
