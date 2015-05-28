package nl.tudelft.lifetiles.graph.models.jgrapht;

import java.util.HashSet;

import nl.tudelft.lifetiles.graph.model.GraphFactory;
import nl.tudelft.lifetiles.graph.model.FactoryProducer;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GraphFactoryImplementationTest {
    GraphFactory<SequenceSegment> gf;
    static FactoryProducer<SequenceSegment> fp;
    SequenceSegment v1, v2;

    @BeforeClass
    public static void runOnce() {
        fp = new FactoryProducer<SequenceSegment>();
    }

    @Before
    public void setUp() throws Exception {
        gf = fp.getFactory("JGraphT");
        v1 = new SequenceSegment(new HashSet<Sequence>(), 0, 0,
                new SegmentEmpty(0));
        v2 = new SequenceSegment(new HashSet<Sequence>(), 0, 0,
                new SegmentEmpty(0));
    }

    @Test
    public void testGetGraph() {
        Graph<SequenceSegment> g = gf.getGraph();
        assert (g instanceof JGraphTGraphAdapter);
    }
}