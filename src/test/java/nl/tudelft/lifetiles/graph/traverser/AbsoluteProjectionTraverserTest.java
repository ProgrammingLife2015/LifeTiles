package nl.tudelft.lifetiles.graph.traverser;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import nl.tudelft.lifetiles.graph.FactoryProducer;
import nl.tudelft.lifetiles.graph.Graph;
import nl.tudelft.lifetiles.graph.GraphFactory;
import nl.tudelft.lifetiles.graph.sequence.SegmentEmpty;
import nl.tudelft.lifetiles.graph.sequence.SegmentString;
import nl.tudelft.lifetiles.graph.sequence.SequenceSegment;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AbsoluteProjectionTraverserTest {
    GraphFactory<SequenceSegment> gf;
    static FactoryProducer<SequenceSegment> fp;
	static AbsoluteProjectionTraverser apt;
    static HashSet<String> s1, s2, s3;
    SequenceSegment v1, v2, v3, v4, v5, v6;
    Graph<SequenceSegment> gr;

    @BeforeClass
    public static void runOnce() {
        fp = new FactoryProducer<SequenceSegment>();
        apt = new AbsoluteProjectionTraverser("reference");
        
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
        v2 = new SequenceSegment(s2, 11, 20, new SegmentString("AAAAAAAAAA"));
        v3 = new SequenceSegment(s3, 11, 20, new SegmentEmpty(10));
        v4 = new SequenceSegment(s1, 21, 30, new SegmentString("AAAAAAAAAA"));
        v5 = new SequenceSegment(s2, 11, 20, new SegmentEmpty(10));
        v6 = new SequenceSegment(s3, 11, 20, new SegmentString("AAAAAAAAAA"));
        gr = gf.getGraph();
    }
    
	@Test
	public void testTraverseReferenceGapGraph() {
        gr.addVertex(v1);
        gr.addVertex(v3);
        gr.addVertex(v4);
        gr.addVertex(v6);
        gr.addEdge(v1, v3);
        gr.addEdge(v1, v6);
        gr.addEdge(v3, v4);
        gr.addEdge(v6, v4);
        apt.traverseGraph(gr);

        assertEquals(1, v1.getAbsStart());
        assertEquals(10, v1.getAbsEnd());
        assertEquals(11, v3.getAbsStart());
        assertEquals(10, v3.getAbsEnd());
        assertEquals(11, v4.getAbsStart());
        assertEquals(20, v4.getAbsEnd());
        assertEquals(11, v6.getAbsStart());
        assertEquals(10, v6.getAbsEnd());
	}
    
	@Test
	public void testTraverseGapGraph() {
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addVertex(v4);
        gr.addVertex(v5);
        gr.addEdge(v1, v2);
        gr.addEdge(v1, v5);
        gr.addEdge(v2, v4);
        gr.addEdge(v5, v4);
        apt.traverseGraph(gr);

        assertEquals(1, v1.getAbsStart());
        assertEquals(10, v1.getAbsEnd());
        assertEquals(11, v2.getAbsStart());
        assertEquals(20, v2.getAbsEnd());
        assertEquals(21, v4.getAbsStart());
        assertEquals(30, v4.getAbsEnd());
        assertEquals(11, v5.getAbsStart());
        assertEquals(20, v5.getAbsEnd());
	}
    
	@Test
	public void testTraverseParallelGraph() {
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addVertex(v4);
        gr.addVertex(v6);
        gr.addEdge(v1, v2);
        gr.addEdge(v1, v6);
        gr.addEdge(v2, v4);
        gr.addEdge(v6, v4);
        apt.traverseGraph(gr);

        assertEquals(1, v1.getAbsStart());
        assertEquals(10, v1.getAbsEnd());
        assertEquals(11, v2.getAbsStart());
        assertEquals(20, v2.getAbsEnd());
        assertEquals(21, v4.getAbsStart());
        assertEquals(30, v4.getAbsEnd());
        assertEquals(11, v6.getAbsStart());
        assertEquals(20, v6.getAbsEnd());
	}
	
}
