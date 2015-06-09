package nl.tudelft.lifetiles.graph.traverser;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import nl.tudelft.lifetiles.graph.model.FactoryProducer;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.graph.model.GraphFactory;
import nl.tudelft.lifetiles.sequence.model.DefaultSequence;
import nl.tudelft.lifetiles.sequence.model.SegmentEmpty;
import nl.tudelft.lifetiles.sequence.model.SegmentString;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReferencePositionTraverserTest {
    GraphFactory<SequenceSegment> gf;
    static Set<Sequence> s1, s2, s3;
    SequenceSegment v1, v4;
    Graph<SequenceSegment> gr;
    static Sequence reference;

    @BeforeClass
    public static void runOnce() {
        reference = new DefaultSequence("reference");
        Sequence ss2 = new DefaultSequence("mutation");

        s1 = new HashSet<Sequence>();
        s1.add(reference);
        s1.add(ss2);

        s2 = new HashSet<Sequence>();
        s2.add(reference);

        s3 = new HashSet<Sequence>();
        s3.add(ss2);
    }

    @Before
    public void setUp() throws Exception {
        gf = FactoryProducer.getFactory("JGraphT");
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

        ReferencePositionTraverser.referenceMapGraph(gr, reference);

        assertEquals(1, v1.getReferenceStart());
        assertEquals(10, v1.getReferenceEnd());
        assertEquals(11, v3.getReferenceStart());
        assertEquals(10, v3.getReferenceEnd());
        assertEquals(11, v4.getReferenceStart());
        assertEquals(20, v4.getReferenceEnd());
        assertEquals(11, v6.getReferenceStart());
        assertEquals(10, v6.getReferenceEnd());
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

        ReferencePositionTraverser.referenceMapGraph(gr, reference);

        assertEquals(1, v1.getReferenceStart());
        assertEquals(10, v1.getReferenceEnd());
        assertEquals(11, v2.getReferenceStart());
        assertEquals(20, v2.getReferenceEnd());
        assertEquals(21, v4.getReferenceStart());
        assertEquals(30, v4.getReferenceEnd());
        assertEquals(11, v5.getReferenceStart());
        assertEquals(20, v5.getReferenceEnd());
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

        ReferencePositionTraverser.referenceMapGraph(gr, reference);

        assertEquals(1, v1.getReferenceStart());
        assertEquals(10, v1.getReferenceEnd());
        assertEquals(11, v2.getReferenceStart());
        assertEquals(20, v2.getReferenceEnd());
        assertEquals(21, v4.getReferenceStart());
        assertEquals(30, v4.getReferenceEnd());
        assertEquals(11, v6.getReferenceStart());
        assertEquals(20, v6.getReferenceEnd());
    }

}
