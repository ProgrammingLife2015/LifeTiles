package nl.tudelft.lifetiles.graph.view;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import javafx.scene.Group;
import nl.tudelft.lifetiles.core.util.Logging;
import nl.tudelft.lifetiles.graph.model.BucketCache;
import nl.tudelft.lifetiles.graph.model.FactoryProducer;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.graph.model.GraphFactory;
import nl.tudelft.lifetiles.graph.model.StackedMutationContainer;
import nl.tudelft.lifetiles.sequence.model.DefaultSequence;
import nl.tudelft.lifetiles.sequence.model.SegmentEmpty;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DiagramViewTest {
    private Group diagram;
    GraphFactory<SequenceSegment> gf;
    SequenceSegment v1, v2;
    Graph<SequenceSegment> gr;

    @BeforeClass
    public static void before() {
        Logging.setLevel(Level.SEVERE);
    }

    @Before
    public void setup() {
        gf = FactoryProducer.getFactory("JGraphT");
        gr = gf.getGraph();

        Set<Sequence> s = new HashSet<Sequence>();
        s.add(new DefaultSequence("reference"));
        v1 = new SequenceSegment(s, 1, 11, new SegmentEmpty(10));
        v1.setUnifiedStart(1);
        v1.setUnifiedEnd(11);

        v2 = new SequenceSegment(s, 31, 41, new SegmentEmpty(10));
        v2.setUnifiedStart(31);
        v2.setUnifiedEnd(41);

        BucketCache b = new BucketCache(1000, gr);
        StackedMutationContainer container = new StackedMutationContainer(b,
                null);
        diagram = (new DiagramView()).drawDiagram(container, 1, 100);
    }

    @Test
    public void childrenTest() {
        assertEquals(1024, diagram.getChildren().size());
    }

    @Test
    public void xLayoutTest() {
        assertEquals(0, diagram.getLayoutX(), 1e-10);
    }

    @Test
    public void yLayoutTest() {
        assertEquals(0, diagram.getLayoutY(), 1e-10);
    }
}
