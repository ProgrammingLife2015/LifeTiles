package nl.tudelft.lifetiles.graph.traverser;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Iterator;

import nl.tudelft.lifetiles.graph.Edge;
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
    static HashSet<String> s1, s2, s3;
    SequenceSegment v1, v2, v3;
    Graph<SequenceSegment> gr;

    @BeforeClass
    public static void runOnce() {
        fp = new FactoryProducer<SequenceSegment>();
        at = new AlignmentTraverser();
        
        s1 = new HashSet<String>();
        s1.add("reference");
        s1.add("mutation");
        s2 = new HashSet<String>();
        s2.add("reference");
        s3 = new HashSet<String>();
        s3.add("mutation");
    }

    @Before
    public void setUp() throws Exception {
        gf = fp.getFactory("JGraphT");
        v1 = new SequenceSegment(s1, 1, 10, new SegmentString("AAAAAAAAAA"));
        v2 = new SequenceSegment(s3, 11, 20, new SegmentEmpty(10));
        v3 = new SequenceSegment(s1, 21, 30, new SegmentString("AAAAAAAAAA"));
        gr = gf.getGraph();
        gr.addVertex(v1);
        gr.addVertex(v3);
    }
    
    @Test
    public void testTraverseForkGraph() {
        SequenceSegment v4 = new SequenceSegment(null, 1, 10, new SegmentString("AAAAAAAAAA"));
        gr.addVertex(v4);
        gr.addEdge(v1, v3);
        gr.addEdge(v4, v3);
        at.traverseGraph(gr);
        assertEquals(5, gr.getAllVertices().size());
    }
    
    @Test
    public void testTraverseBranchGraph() {
        SequenceSegment v4 = new SequenceSegment(null, 21, 30, new SegmentString("AAAAAAAAAA"));
        gr.addVertex(v4);
        gr.addEdge(v1, v3);
        gr.addEdge(v1, v4);
        at.traverseGraph(gr);
        assertEquals(5, gr.getAllVertices().size());
    }
    
    @Test
    public void testTraverseGapGraph() {
        gr.addEdge(v1, v3);
        at.traverseGraph(gr);
        assertEquals(gr.getDestination(gr.getOutgoing(v1).iterator().next()), gr.getSource(gr.getIncoming(v3).iterator().next()));
    }
    
	@Test
	public void testTraverseBridgeGraph() {
        gr.addVertex(v2);
        gr.addEdge(v1, v2);
        gr.addEdge(v2, v3);
        at.traverseGraph(gr);
        assertEquals(3, at.traverseGraph(gr).getAllVertices().size());
	}
    
    @Test
    public void testTraverseInsertionGraph() {
        SequenceSegment v4 = new SequenceSegment(s2, 11, 20, new SegmentString("AAAAAAAAAA"));
        gr.addVertex(v4);
        gr.addEdge(v1, v3);
        gr.addEdge(v1, v4);
        gr.addEdge(v4, v3);
        at.traverseGraph(gr);
        Iterator<Edge<SequenceSegment>> it = gr.getIncoming(v3).iterator();
        it.next();
        assertEquals(s3, gr.getSource(it.next()).getSources());
    }

}
