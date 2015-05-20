package nl.tudelft.lifetiles.graph.model.jgrapht;

import java.util.HashSet;

import nl.tudelft.lifetiles.graph.model.FactoryProducer;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.graph.model.GraphFactory;
import nl.tudelft.lifetiles.sequence.model.SegmentEmpty;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

import org.junit.Before;
import org.junit.Test;

public class GraphFactoryImplementationTest {
    GraphFactory<SequenceSegment> gf;
    SequenceSegment v1, v2;

    @Before
    public void setUp() throws Exception {
        gf = FactoryProducer.getFactory("JGraphT");
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

    @Test
    public void testGetSubGraph() {
        Graph<SequenceSegment> g = gf.getSubGraph(gf.getGraph(), null);
        assert (g instanceof JGraphTGraphAdapter);
    }
}
