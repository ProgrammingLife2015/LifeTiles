package nl.tudelft.lifetiles.core.controller;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ControllerTest {

    private Controller stub1, stub2;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AtomicReference<Object> inbox;

    @Before
    public void setUp() {
        stub1 = new ControllerStub();
        stub2 = new ControllerStub();
        inbox = new AtomicReference<>();
    }

    @Test
    public void testShoutSelf() {
        final String message = "Leviathan";
        final String content = "hail santa";

        stub1.listen(message, (controller, args) -> {
            inbox.set(args[0]);
        });
        stub1.shout(message, content);

        assertEquals(content, inbox.get());
    }

    @Test
    public void testShoutOther() {
        final String message = "Behemoth";
        final String content = "hail satan";

        stub2.listen(message, (controller, args) -> {
            System.out.println("Testother shout");
            inbox.set(args[0]);
        });
        stub1.shout(message, content);

        assertEquals(content, inbox.get());
    }
}

final class ControllerStub extends Controller {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // noop
    }
}
