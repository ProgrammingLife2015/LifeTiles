package nl.tudelft.lifetiles.core.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TimerTest {

    private Timer clock;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        clock = Timer.getAndStart();
    }

    @Test
    public void startTest() {
        thrown.expect(IllegalStateException.class);
        clock.start();
    }

    @Test
    public void stopTest() throws InterruptedException {
        clock.stop();
        long time = clock.getElapsed();
        assertEquals(time, clock.getElapsed());
    }

    @Test
    public void getElapsedTest() throws InterruptedException {
        long time = clock.getElapsed();
        clock.stop();
        assertNotEquals(time, clock.getElapsed());
    }

    @Test
    public void unstartedStopTest() throws InterruptedException {
        thrown.expect(IllegalStateException.class);
        clock.stop();
        clock.stop();
    }

    @Test
    public void stopAndLogTest() {
        clock.stopAndLog("test");
        long time = clock.getElapsed();
        assertEquals(time, clock.getElapsed());
    }

}
