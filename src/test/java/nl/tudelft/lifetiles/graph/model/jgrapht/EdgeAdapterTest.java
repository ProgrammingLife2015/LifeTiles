package nl.tudelft.lifetiles.graph.model.jgrapht;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.SortedSet;

import nl.tudelft.lifetiles.graph.model.Edge;
import nl.tudelft.lifetiles.graph.model.FactoryProducer;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.graph.model.GraphFactory;
import nl.tudelft.lifetiles.sequence.model.SegmentEmpty;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EdgeAdapterTest {

    GraphFactory<SequenceSegment> gf;
    static FactoryProducer<SequenceSegment> fp;
    SequenceSegment v1, v2, v3, v4;
    Graph<SequenceSegment> gr;

    @BeforeClass
    public static void runOnce() {
        fp = new FactoryProducer<SequenceSegment>();
    }

    @Before
    public void setUp() throws Exception {
        gf = fp.getFactory("JGraphT");
        v1 = new SequenceSegment(new HashSet<Sequence>(), 0, 1,
                new SegmentEmpty(0));
        v2 = new SequenceSegment(new HashSet<Sequence>(), 1, 2,
                new SegmentEmpty(0));
        v3 = new SequenceSegment(new HashSet<Sequence>(), 2, 3,
                new SegmentEmpty(0));
        v4 = new SequenceSegment(new HashSet<Sequence>(), 0, 4,
                new SegmentEmpty(0));
        gr = gf.getGraph();
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addVertex(v3);
        gr.addVertex(v4);
        gr.addEdge(v1, v3);
        gr.addEdge(v1, v2);
        gr.addEdge(v2, v4);
    }

    @Test
    public void testEqualsFalse() {
        Edge<SequenceSegment> e1 = gr.getIncoming(v2).iterator().next();
        Edge<SequenceSegment> e2 = gr.getIncoming(v4).iterator().next();
        assertFalse(e1.equals(e2));
    }

    @Test
    public void testEqualsTrue() {
        Edge<SequenceSegment> e1 = gr.getIncoming(v2).iterator().next();

        assertTrue(e1.equals(e1));
    }

    @Test
    public void testSorted() {
        SortedSet<Edge<SequenceSegment>> outgoing = gr.getOutgoing(v1);
        assertEquals(-1, v2.compareTo(v3));
        assertEquals(2, outgoing.size());
        assertEquals(v2, gr.getDestination(outgoing.iterator().next()));
    }
}
