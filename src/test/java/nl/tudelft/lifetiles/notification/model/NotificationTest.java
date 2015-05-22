package nl.tudelft.lifetiles.notification.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class NotificationTest {

    private NotificationFactory nf;
    private Notification error, warning, info;

    @Before
    public void setUp() {
        this.nf = new NotificationFactory();
        this.error = nf.error("Satan");
        this.warning = nf.warning("Belial");
        this.info = nf.info("Lucifer");
    }

    @Test
    public void testErrorNotificationMessage() {
        assertEquals("Error: Satan", error.getMessage());
    }

    @Test
    public void testWarningNotificationMessage() {
        assertEquals("Warning: Belial", warning.getMessage());
    }

    @Test
    public void testInfoNotificationMessage() {
        assertEquals("Info: Lucifer", info.getMessage());
    }

}
