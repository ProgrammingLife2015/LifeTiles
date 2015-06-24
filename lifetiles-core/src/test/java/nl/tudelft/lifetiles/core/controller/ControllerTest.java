package nl.tudelft.lifetiles.core.controller;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

import nl.tudelft.lifetiles.core.util.Logging;
import nl.tudelft.lifetiles.core.util.Message;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ControllerTest {

    private AbstractController stub1, stub2;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AtomicReference<Object> inbox1, inbox2;

    @BeforeClass
    public static void before() {
        Logging.setLevel(Level.SEVERE);
    }

    @Before
    public void setUp() {
        stub1 = new ControllerStub();
        stub2 = new ControllerStub();
        inbox1 = new AtomicReference<>();
        inbox2 = new AtomicReference<>();
    }

    @Test
    public void testShoutSelf() {
        final Message message = Message.create("Leviathan");
        final String content = "hail santa";

        stub1.listen(message,
                (controller, subject, args) -> inbox1.set(args[0]));
        stub1.shout(message, "", content);

        assertEquals(content, inbox1.get());
    }

    @Test
    public void testShoutOther() {
        final Message message = Message.create("Behemoth");
        final String content = "hail satan";

        stub2.listen(message,
                (controller, subject, args) -> inbox1.set(args[0]));
        stub1.shout(message, "", content);

        assertEquals(content, inbox1.get());
    }

    @Test
    public void testDoubleListener() {
        final Message message = Message.create("Ziz");
        final String content = "abyssum abyssus invocat";

        stub2.listen(message,
                (controller, subject, args) -> inbox1.set(args[0]));
        stub2.listen(message,
                (controller, subject, args) -> inbox2.set(args[0]));
        stub1.shout(message, "", content);

        assertEquals(content, inbox1.get());
        assertEquals(content, inbox2.get());
    }

}

final class ControllerStub extends AbstractController {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // noop
    }
}
