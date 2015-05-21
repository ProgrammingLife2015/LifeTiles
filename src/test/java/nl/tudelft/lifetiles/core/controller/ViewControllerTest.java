package nl.tudelft.lifetiles.core.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import nl.tudelft.lifetiles.graph.models.sequence.DefaultSequence;
import nl.tudelft.lifetiles.graph.models.sequence.Sequence;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ViewControllerTest {
    ViewController controller;
    Sequence s1, s2;

    static final String testGraphFilename = "data/test_set/simple_graph";
    static final int[] windowSize = new int[] {1280, 720};

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        s1 = new DefaultSequence("s1");
        s2 = new DefaultSequence("s2");

        controller = ViewController.getInstance();
    }

    @Test
    public void testUnloadedGraph() {
        thrown.expect(UnsupportedOperationException.class);
        controller.getGraph();
    }

    @Test
    public void testUnloadedSequences() {
        thrown.expect(UnsupportedOperationException.class);
        controller.getSequences();
    }

    @Test
    public void testGraphLoading() {
        assertFalse(controller.isLoaded());

        controller.loadGraph(testGraphFilename);
        assertTrue(controller.isLoaded());

        controller.unloadGraph();
        assertFalse(controller.isLoaded());
    }

    @Test
    public void testSetVisible() {
        controller.loadGraph(testGraphFilename);

        Collection<Sequence> sequences = controller.getSequences().values();
        Sequence visibleSequence = new ArrayList<>(sequences).get(0);
        Set<Sequence> visibleSequences = new HashSet<>(Arrays.asList(visibleSequence));
        controller.setVisible(visibleSequences);

        assertTrue(controller.getVisible().contains(visibleSequence));

        controller.unloadGraph();
    }

    @Test
    public void testSetWrongVisible() {
        controller.loadGraph(testGraphFilename);

        Set<Sequence> newSequences = new HashSet<>();
        newSequences.add(s1);
        newSequences.add(s2);
        thrown.expect(IllegalArgumentException.class);
        controller.setVisible(newSequences);

        controller.unloadGraph();
    }

}
