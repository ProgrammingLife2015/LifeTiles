package nl.tudelft.lifetiles.graph.traverser;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import nl.tudelft.lifetiles.graph.FactoryProducer;
import nl.tudelft.lifetiles.graph.Graph;
import nl.tudelft.lifetiles.graph.GraphFactory;
import nl.tudelft.lifetiles.graph.sequence.SegmentEmpty;
import nl.tudelft.lifetiles.graph.sequence.SegmentString;
import nl.tudelft.lifetiles.graph.sequence.SequenceSegment;
import nl.tudelft.lifetiles.graph.sequence.mutation.DeletionMutation;
import nl.tudelft.lifetiles.graph.sequence.mutation.InsertionMutation;
import nl.tudelft.lifetiles.graph.sequence.mutation.PolymorphismMutation;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MutationTraverserTest {
    GraphFactory<SequenceSegment> gf;
    static FactoryProducer<SequenceSegment> fp;
    static AbsoluteProjectionTraverser apt;
    static MutationTraverser mt;
    static HashSet<String> s1, s2, s3;
    SequenceSegment v1, v4;
    Graph<SequenceSegment> gr;

    @BeforeClass
    public static void runOnce() {
        fp = new FactoryProducer<SequenceSegment>();
        apt = new AbsoluteProjectionTraverser("reference");
        mt = new MutationTraverser("reference");
        
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
        v4 = new SequenceSegment(s1, 21, 30, new SegmentString("AAAAAAAAAA"));
        gr = gf.getGraph();
        gr.addVertex(v1);
        gr.addVertex(v4);
    }
    
    @Test
    public void testTraverseInsertionGraph() {
        SequenceSegment v5 = new SequenceSegment(s2, 11, 20, new SegmentEmpty(10));
        SequenceSegment v6 = new SequenceSegment(s3, 11, 20, new SegmentString("AAAAAAAAAA"));
        gr.addVertex(v5);
        gr.addVertex(v6);
        gr.addEdge(v1, v5);
        gr.addEdge(v1, v6);
        gr.addEdge(v5, v4);
        gr.addEdge(v6, v4);
        apt.traverseGraph(gr);
        mt.traverseGraph(gr);
        assertNull(v1.getMutation());
        assertNull(v5.getMutation());
        assertNull(v4.getMutation());
        assertTrue(v6.getMutation() instanceof InsertionMutation);
    }
    
    @Test
    public void testTraverseDeletionGraph() {
        SequenceSegment v2 = new SequenceSegment(s2, 11, 20, new SegmentString("AAAAAAAAAA"));
        SequenceSegment v3 = new SequenceSegment(s3, 11, 20, new SegmentEmpty(10));
        gr.addVertex(v2);
        gr.addVertex(v3);
        gr.addEdge(v1, v2);
        gr.addEdge(v1, v3);
        gr.addEdge(v2, v4);
        gr.addEdge(v3, v4);
        apt.traverseGraph(gr);
        mt.traverseGraph(gr);
        assertNull(v1.getMutation());
        assertNull(v2.getMutation());
        assertNull(v4.getMutation());
        assertTrue(v3.getMutation() instanceof DeletionMutation);
    }
    
    @Test
    public void testTraversePolymorphismGraph() {
        SequenceSegment v2 = new SequenceSegment(s2, 11, 20, new SegmentString("AAAAAAAAAA"));
        SequenceSegment v6 = new SequenceSegment(s3, 11, 20, new SegmentString("AAAAAAAAAA"));
        gr.addVertex(v2);
        gr.addVertex(v6);
        gr.addEdge(v1, v2);
        gr.addEdge(v1, v6);
        gr.addEdge(v2, v4);
        gr.addEdge(v6, v4);
        apt.traverseGraph(gr);
        mt.traverseGraph(gr);
        assertNull(v1.getMutation());
        assertNull(v2.getMutation());
        assertNull(v4.getMutation());
        assertTrue(v6.getMutation() instanceof PolymorphismMutation);
    }
    
}
