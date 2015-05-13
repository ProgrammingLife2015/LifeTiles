package nl.tudelft.lifetiles.core.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Observer;
import java.util.Set;

import nl.tudelft.lifetiles.graph.models.sequence.DefaultSequence;
import nl.tudelft.lifetiles.graph.models.sequence.Sequence;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ViewControllerTest {
    ViewController controller;
    Observer view;
    Map<String, Sequence> sequences;
    Sequence s1, s2, s3, s4;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        s1 = new DefaultSequence("s1");
        s2 = new DefaultSequence("s2");
        s3 = new DefaultSequence("s3");
        s4 = new DefaultSequence("s4");

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
    public void testSetWrongVisible() {
        Set<Sequence> newSequences = new HashSet<>();
        newSequences.add(s1);
        newSequences.add(s4);
        thrown.expect(UnsupportedOperationException.class);
        controller.setVisible(newSequences);
    }

}
