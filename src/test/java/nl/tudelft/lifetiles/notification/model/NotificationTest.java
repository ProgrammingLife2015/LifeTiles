package nl.tudelft.lifetiles.notification.model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class NotificationTest {

    private NotificationFactory nf;
    private AbstractNotification error, warning, info, exception;

    @Before
    public void setUp() {
        this.nf = new NotificationFactory();
        this.error = nf.getNotification("Satan", NotificationFactory.ERROR);
        this.warning = nf.getNotification("Belial", NotificationFactory.WARNING);
        this.info = nf.getNotification("Lucifer", NotificationFactory.INFO);
        this.exception = nf.getNotification(new IOException("Beelzebub"));
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

    @Test
    public void testExceptionNotificationMessage() {
        assertEquals("Error: Beelzebub", exception.getMessage());
    }

}
