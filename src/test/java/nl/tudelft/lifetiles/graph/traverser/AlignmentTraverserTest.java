package nl.tudelft.lifetiles.graph.traverser;

import static org.junit.Assert.assertEquals;
import nl.tudelft.lifetiles.graph.FactoryProducer;
import nl.tudelft.lifetiles.graph.Graph;
import nl.tudelft.lifetiles.graph.GraphFactory;
import nl.tudelft.lifetiles.graph.sequence.SegmentEmpty;
import nl.tudelft.lifetiles.graph.sequence.SegmentString;
import nl.tudelft.lifetiles.graph.sequence.SequenceSegment;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AlignmentTraverserTest {
    GraphFactory<SequenceSegment> gf;
    static FactoryProducer<SequenceSegment> fp;
	static AlignmentTraverser at;
    SequenceSegment v1, v2, v3;
    Graph<SequenceSegment> gr;

    @BeforeClass
    public static void runOnce() {
        fp = new FactoryProducer<SequenceSegment>();
        at = new AlignmentTraverser();
    }

    @Before
    public void setUp() throws Exception {
        gf = fp.getFactory("JGraphT");
        v1 = new SequenceSegment(null, 1, 10, new SegmentString("AAAAAAAAAA"));
        v2 = new SequenceSegment(null, 11, 20, new SegmentEmpty(10));
        v3 = new SequenceSegment(null, 21, 30, new SegmentString("AAAAAAAAAA"));
        gr = gf.getGraph();
    }
    
    @Test
    public void testTraverseForkGraph() {
        SequenceSegment v4 = new SequenceSegment(null, 1, 10, new SegmentString("AAAAAAAAAA"));
        gr.addVertex(v1);
        gr.addVertex(v3);
        gr.addVertex(v4);
        gr.addEdge(v1, v3);
        gr.addEdge(v4, v3);
        at.traverseGraph(gr);
        assertEquals(5, gr.getAllVertices().size());
    }
    
    @Test
    public void testTraverseBranchGraph() {
        SequenceSegment v4 = new SequenceSegment(null, 21, 30, new SegmentString("AAAAAAAAAA"));
        gr.addVertex(v1);
        gr.addVertex(v3);
        gr.addVertex(v4);
        gr.addEdge(v1, v3);
        gr.addEdge(v1, v4);
        at.traverseGraph(gr);
        assertEquals(5, gr.getAllVertices().size());
    }
    
    @Test
    public void testTraverseGapGraph() {
        gr.addVertex(v1);
        gr.addVertex(v3);
        gr.addEdge(v1, v3);
        at.traverseGraph(gr);
        assertEquals(gr.getDestination(gr.getOutgoing(v1).iterator().next()), gr.getSource(gr.getIncoming(v3).iterator().next()));
    }
    
	@Test
	public void testTraverseBridgeGraph() {
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addVertex(v3);
        gr.addEdge(v1, v2);
        gr.addEdge(v2, v3);
        at.traverseGraph(gr);
        assertEquals(3, at.traverseGraph(gr).getAllVertices().size());
	}

}
