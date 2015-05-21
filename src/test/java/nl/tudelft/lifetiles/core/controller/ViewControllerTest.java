package nl.tudelft.lifetiles.core.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.tudelft.lifetiles.graph.models.sequence.DefaultSequence;
import nl.tudelft.lifetiles.graph.models.sequence.Sequence;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.testfx.framework.junit.ApplicationTest;

public class ViewControllerTest extends ApplicationTest {
    ViewController controller;
    Sequence s1, s2;

    static final String testGraphFilename = "/data/test_set/simple_graph";
    static final int[] windowSize = new int[] {
            1280, 720
    };
    File edgefile, vertexfile;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        s1 = new DefaultSequence("s1");
        s2 = new DefaultSequence("s2");

        controller = ViewController.getInstance();
        edgefile = new File(this.getClass()
                .getResource(testGraphFilename + ".edge.graph").toURI());
        vertexfile = new File(this.getClass()
                .getResource(testGraphFilename + ".node.graph").toURI());
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
    public void testGraphLoading() throws Exception {
        assertFalse(controller.isLoaded());

        controller.loadGraph(vertexfile, edgefile);
        assertTrue(controller.isLoaded());

        controller.unloadGraph();
        assertFalse(controller.isLoaded());
    }

    @Test
    public void testSetVisible() throws Exception {
        controller.loadGraph(vertexfile, edgefile);

        Collection<Sequence> sequences = controller.getSequences().values();
        Sequence visibleSequence = new ArrayList<>(sequences).get(0);
        Set<Sequence> visibleSequences = new HashSet<>(
                Arrays.asList(visibleSequence));
        controller.setVisible(visibleSequences);

        assertTrue(controller.getVisible().contains(visibleSequence));

        controller.unloadGraph();
    }

    @Test
    public void testSetWrongVisible() throws Exception {
        controller.loadGraph(vertexfile, edgefile);

        Set<Sequence> newSequences = new HashSet<>();
        newSequences.add(s1);
        newSequences.add(s2);
        thrown.expect(IllegalArgumentException.class);
        controller.setVisible(newSequences);

        controller.unloadGraph();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new Group(), windowSize[0], windowSize[1]);
        stage.setScene(scene);
        stage.show();
        stage.hide();
    }

}
