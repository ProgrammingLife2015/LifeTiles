package nl.tudelft.lifetiles.core.controller;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class ControllerTest {

    static final String testGraphFilename = "/data/test_set/simple_graph";
    static final int[] windowSize = new int[] {
        1280, 720
    };
    File edgefile, vertexfile;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testRegistration() {
        Controller stub = Mockito.mock(Controller.class, Mockito.CALLS_REAL_METHODS);
        final String name = "Leviathan";
        stub.register(name);
        assertEquals(stub, ControllerManager.getController(name));
    }

    @Test
    public void testRegistrationFail() {
        final String name = "Behemoth";

        thrown.expect(IllegalArgumentException.class);
        ControllerManager.getController(name);
    }

    @Test
    public void testMultipleRegistration() {
        final int numControllers = 9;
        for (String name = ""; name.length() < numControllers; name += "6") {
            Controller stub = Mockito.mock(Controller.class, Mockito.CALLS_REAL_METHODS);
            stub.register(name);
        }
        assertEquals(numControllers, ControllerManager.getControllers().size());
    }

}
