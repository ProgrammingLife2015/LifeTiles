package nl.tudelft.lifetiles.graph.traverser;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import nl.tudelft.lifetiles.graph.model.FactoryProducer;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.graph.model.GraphFactory;
import nl.tudelft.lifetiles.graph.view.Mutation;
import nl.tudelft.lifetiles.sequence.model.DefaultSequence;
import nl.tudelft.lifetiles.sequence.model.SegmentEmpty;
import nl.tudelft.lifetiles.sequence.model.SegmentString;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MutationIndicationTraverserTest {
    GraphFactory<SequenceSegment> gf;
    static ReferencePositionTraverser rpt;
    static MutationIndicationTraverser mit;
    static Set<Sequence> s1, s2, s3;
    SequenceSegment v1, v4;
    Graph<SequenceSegment> gr;

    @BeforeClass
    public static void runOnce() {
        Sequence ss1 = new DefaultSequence("reference");
        Sequence ss2 = new DefaultSequence("mutation");

        rpt = new ReferencePositionTraverser(ss1);
        mit = new MutationIndicationTraverser(ss1);

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
        gf = FactoryProducer.getFactory("JGraphT");
        v1 = new SequenceSegment(s1, 1, 11, new SegmentString("AAAAAAAAAA"));
        v4 = new SequenceSegment(s1, 21, 31, new SegmentString("AAAAAAAAAA"));
        gr = gf.getGraph();
        gr.addVertex(v1);
        gr.addVertex(v4);
    }

    @Test
    public void testTraverseInsertionGraph() {
        SequenceSegment v5 = new SequenceSegment(s2, 11, 21, new SegmentEmpty(
                10));
        SequenceSegment v6 = new SequenceSegment(s3, 11, 21, new SegmentString(
                "AAAAAAAAAA"));
        gr.addVertex(v5);
        gr.addVertex(v6);
        gr.addEdge(v1, v5);
        gr.addEdge(v1, v6);
        gr.addEdge(v5, v4);
        gr.addEdge(v6, v4);
        rpt.referenceMapGraph(gr);
        mit.indicateGraphMutations(gr);
        assertNull(v1.getMutation());
        assertNull(v5.getMutation());
        assertNull(v4.getMutation());
        assertTrue(v6.getMutation() == Mutation.INSERTION);
    }

    @Test
    public void testTraverseDeletionGraph() {
        SequenceSegment v2 = new SequenceSegment(s2, 11, 21, new SegmentString(
                "AAAAAAAAAA"));
        SequenceSegment v3 = new SequenceSegment(s3, 11, 21, new SegmentEmpty(
                10));
        gr.addVertex(v2);
        gr.addVertex(v3);
        gr.addEdge(v1, v2);
        gr.addEdge(v1, v3);
        gr.addEdge(v2, v4);
        gr.addEdge(v3, v4);
        rpt.referenceMapGraph(gr);
        mit.indicateGraphMutations(gr);
        assertNull(v1.getMutation());
        assertNull(v2.getMutation());
        assertNull(v4.getMutation());
        assertTrue(v3.getMutation() == Mutation.DELETION);
    }

    @Test
    public void testTraversePolymorphismGraph() {
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
        rpt.referenceMapGraph(gr);
        mit.indicateGraphMutations(gr);
        assertNull(v1.getMutation());
        assertNull(v2.getMutation());
        assertNull(v4.getMutation());
        assertTrue(v6.getMutation() == Mutation.POLYMORPHISM);
    }
}
