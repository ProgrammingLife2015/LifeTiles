package nl.tudelft.lifetiles.graph.traverser;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import nl.tudelft.lifetiles.core.util.Logging;
import nl.tudelft.lifetiles.graph.model.FactoryProducer;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.graph.model.GraphFactory;
import nl.tudelft.lifetiles.sequence.model.DefaultSequence;
import nl.tudelft.lifetiles.sequence.model.SegmentString;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UnifiedPositionTraverserTest {
    GraphFactory<SequenceSegment> gf;
    static Set<Sequence> s1;
    SequenceSegment v1, v2, v3;
    Graph<SequenceSegment> gr;

    @BeforeClass
    public static void runOnce() {
        Logging.setLevel(Level.SEVERE);

        Sequence ss1 = new DefaultSequence("reference");

        s1 = new HashSet<Sequence>();
        s1.add(ss1);
    }

    @Before
    public void setUp() throws Exception {
        gf = FactoryProducer.getFactory("JGraphT");
        v1 = new SequenceSegment(s1, 1, 11, new SegmentString("AAAAAAAAAA"));
        v2 = new SequenceSegment(s1, 21, 31, new SegmentString("AAAAAAAAAA"));
        v3 = new SequenceSegment(s1, 11, 21, new SegmentString("AAAAAAAAAA"));

        gr = gf.getGraph();
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addEdge(v1, v2);
    }

    @Test
    public void traverseGapTest() {
        UnifiedPositionTraverser.unifyGraph(gr);
        assertEquals(1, v1.getUnifiedStart());
        assertEquals(11, v1.getUnifiedEnd());
        assertEquals(11, v2.getUnifiedStart());
        assertEquals(21, v2.getUnifiedEnd());
    }

    public void traverseOutOfOrderTest() {
        gr.addVertex(v3);
        gr.addEdge(v1, v3);
        gr.addEdge(v3, v2);
        UnifiedPositionTraverser.unifyGraph(gr);
        assertEquals(11, v3.getUnifiedStart());
        assertEquals(21, v3.getUnifiedEnd());
        assertEquals(21, v2.getUnifiedStart());
        assertEquals(31, v2.getUnifiedEnd());
    }

}
