package nl.tudelft.lifeTiles.graph.jgrapht;

import nl.tudelft.lifeTiles.graph.DirectedEdge;
import nl.tudelft.lifeTiles.graph.FactoryProducer;
import nl.tudelft.lifeTiles.graph.Graph;
import nl.tudelft.lifeTiles.graph.GraphFactory;
import nl.tudelft.lifeTiles.graph.SequenceSegment;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GraphFactoryImplementationTest {
    GraphFactory<SequenceSegment> gf;
    FactoryProducer<SequenceSegment> fp;
    SequenceSegment v1, v2;

    @BeforeClass
    public void runOnce() {
        fp = new FactoryProducer<SequenceSegment>();
    }
    @Before
    public void setUp() throws Exception {
        gf = fp.getFactory("JGraphT");
        v1 = new SequenceSegment(null, 0, 0, null);
        v2 = new SequenceSegment(null, 0, 0, null);
    }

    @Test
    public void testGetGraph() {
        Graph<SequenceSegment> g = gf.getGraph();
        assert(g instanceof JGraphTGraphAdapter);
    }

    @Test
    public void testGetEdge() {
        DirectedEdge<SequenceSegment> edge = gf.getEdge(v1, v2);
        assert(edge instanceof JGraphTDirectedEdgeAdapter);
    }
}
