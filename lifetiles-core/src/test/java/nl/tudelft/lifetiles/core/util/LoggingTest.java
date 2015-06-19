package nl.tudelft.lifetiles.core.util;

import org.junit.Test;

public class LoggingTest {

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
