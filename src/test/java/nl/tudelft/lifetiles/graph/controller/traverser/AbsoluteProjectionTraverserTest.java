package nl.tudelft.lifetiles.graph.controller.traverser;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nl.tudelft.lifetiles.graph.controller.traverser.AbsoluteProjectionTraverser;
import nl.tudelft.lifetiles.graph.models.FactoryProducer;
import nl.tudelft.lifetiles.graph.models.Graph;
import nl.tudelft.lifetiles.graph.models.GraphFactory;
import nl.tudelft.lifetiles.graph.models.sequence.DefaultSequence;
import nl.tudelft.lifetiles.graph.models.sequence.SegmentEmpty;
import nl.tudelft.lifetiles.graph.models.sequence.SegmentString;
import nl.tudelft.lifetiles.graph.models.sequence.Sequence;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;

public class AbsoluteProjectionTraverserTest {
    GraphFactory<SequenceSegment> gf;
    static FactoryProducer<SequenceSegment> fp;
    static AbsoluteProjectionTraverser apt;
    static Set<Sequence> s1, s2, s3;
    SequenceSegment v1, v4;
    Graph<SequenceSegment> gr;

    @BeforeClass
    public static void runOnce() {
        fp = new FactoryProducer<SequenceSegment>();

        Sequence ss1 = new DefaultSequence("reference");
        Sequence ss2 = new DefaultSequence("mutation");

        apt = new AbsoluteProjectionTraverser(ss1);

        s1 = new HashSet<Sequence>();
        s1.add(ss1);
        s1.add(ss2);

        s2 = new HashSet<Sequence>();
        s2.add(ss1);

        s3 = new HashSet<Sequence>();
        s3.add(ss2);
    }

    @Before
    public void setUp() throws Exception {
        gf = fp.getFactory("JGraphT");
        v1 = new SequenceSegment(s1, 1, 11, new SegmentString("AAAAAAAAAA"));
        v4 = new SequenceSegment(s1, 21, 31, new SegmentString("AAAAAAAAAA"));
        gr = gf.getGraph();
        gr.addVertex(v1);
        gr.addVertex(v4);
    }

    @Test
    public void testTraverseReferenceGapGraph() {
        SequenceSegment v3 = new SequenceSegment(s3, 11, 21, new SegmentEmpty(
                10));
        SequenceSegment v6 = new SequenceSegment(s3, 11, 21, new SegmentString(
                "AAAAAAAAAA"));
        gr.addVertex(v3);
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
        SequenceSegment v2 = new SequenceSegment(s2, 11, 21, new SegmentString(
                "AAAAAAAAAA"));
        SequenceSegment v5 = new SequenceSegment(s2, 11, 21, new SegmentEmpty(
                10));
        gr.addVertex(v2);
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
        SequenceSegment v2 = new SequenceSegment(s2, 11, 21, new SegmentString(
                "AAAAAAAAAA"));
        SequenceSegment v6 = new SequenceSegment(s3, 11, 21, new SegmentString(
                "AAAAAAAAAA"));
        gr.addVertex(v2);
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