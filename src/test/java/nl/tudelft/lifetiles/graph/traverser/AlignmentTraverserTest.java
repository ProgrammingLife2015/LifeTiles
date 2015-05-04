package nl.tudelft.lifetiles.graph.traverser;

import static org.junit.Assert.assertEquals;
import nl.tudelft.lifetiles.graph.FactoryProducer;
import nl.tudelft.lifetiles.graph.Graph;
import nl.tudelft.lifetiles.graph.GraphFactory;
import nl.tudelft.lifetiles.graph.SequenceSegment;

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
        v1 = new SequenceSegment(null, 1, 10, "__________");
        v2 = new SequenceSegment(null, 11, 20, "__________");
        v3 = new SequenceSegment(null, 21, 30, "__________");
        gr = gf.getGraph();
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
