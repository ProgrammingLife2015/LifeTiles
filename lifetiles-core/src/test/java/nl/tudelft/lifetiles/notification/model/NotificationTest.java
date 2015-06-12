package nl.tudelft.lifetiles.notification.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class NotificationTest {

    private NotificationFactory nf;
    private AbstractNotification error, error2, warning, info, exception,
            null1, null2;

    @Before
    public void setUp() {
        this.nf = new NotificationFactory();
        this.error = nf.getNotification("Satan", NotificationFactory.ERROR);
        this.error2 = nf.getNotification("Satan", NotificationFactory.ERROR);
        this.warning = nf
                .getNotification("Belial", NotificationFactory.WARNING);
        this.info = nf.getNotification("Lucifer", NotificationFactory.INFO);
        this.exception = nf.getNotification(new IOException("Beelzebub"));
        this.null1 = nf.getNotification(null, NotificationFactory.INFO);
        this.null2 = nf.getNotification(null, NotificationFactory.INFO);
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

    @Test
    public void testNotificationDuration() {
        final int duration = 666;
        error.setDuration(duration);
        assertEquals(duration, error.getDuration());
    }

    @Test
    public void testNotificationHashCode() {
        assertEquals(error2.hashCode(), error.hashCode());
    }

    @Test
    public void testNotificationHashCodeUnequal() {
        AbstractNotification error3 = nf.getNotification("Satan",
                NotificationFactory.ERROR);
        error3.setDuration(616);
        assertEquals(error.hashCode(), error3.hashCode());
    }

    @Test
    public void testNotificationEquals() {
        assertEquals(error, error2);
    }

    @Test
    public void testNotificationEqualsNull() {

        assertEquals(null1, null2);
    }

    @Test
    public void testNotificationEqualsSelf() {
        assertEquals(info, info);
    }

    @Test
    public void testNotificationNotEquals1() {
        AbstractNotification warning2 = nf.getNotification("Jezus",
                NotificationFactory.WARNING);
        assertNotEquals(warning, warning2);
    }

    @Test
    public void testNotificationNotEquals2() {
        assertNotEquals(exception, "Beelzebob");
    }

    @Test
    public void testNotificationNotEquals3() {
        assertNotEquals(info, null);
    }

}
