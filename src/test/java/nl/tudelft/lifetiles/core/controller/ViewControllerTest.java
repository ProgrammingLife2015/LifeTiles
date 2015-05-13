package nl.tudelft.lifetiles.core.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Observer;
import java.util.Set;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.tudelft.lifetiles.graph.models.FactoryProducer;
import nl.tudelft.lifetiles.graph.models.Graph;
import nl.tudelft.lifetiles.graph.models.GraphFactory;
import nl.tudelft.lifetiles.graph.models.sequence.DefaultSequence;
import nl.tudelft.lifetiles.graph.models.sequence.Sequence;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.testfx.framework.junit.ApplicationTest;

public class ViewControllerTest extends ApplicationTest {
    ViewController controller;
    Observer view;
    Map<String, Sequence> sequences;
    Sequence s1, s2;
    Stage testStage;

    static final String testGraphFilename = "data/test_graph/test_graph";
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
    public void testSetWrongVisible() {
        ViewController vc = ViewController.getInstance();
        vc.setGraph(emptyGraph());

        Set<Sequence> newSequences = new HashSet<>();
        newSequences.add(s1);
        newSequences.add(s2);
        thrown.expect(IllegalArgumentException.class);
        controller.setVisible(newSequences);
    }

    private Graph<SequenceSegment> emptyGraph() {
        FactoryProducer<SequenceSegment> fp = new FactoryProducer<>();
        GraphFactory<SequenceSegment> gf = fp.getFactory("JGraphT");
        return gf.getGraph();
    }

    @Override
    public void start(Stage stage) throws Exception {
        testStage = stage;

        Scene scene = new Scene(new Group(), windowSize[0], windowSize[1]);
        stage.setScene(scene);
        stage.show();
    }

}
