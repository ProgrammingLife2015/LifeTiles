package nl.tudelft.lifetiles.graph.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashSet;
import java.util.Set;

import nl.tudelft.lifetiles.core.util.Settings;
import nl.tudelft.lifetiles.sequence.model.DefaultSequence;
import nl.tudelft.lifetiles.sequence.model.SegmentString;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GraphContainerTest {
    GraphFactory<SequenceSegment> gf;
    static Set<Sequence> s1, s2, s3;
    static Sequence ss1;
    SequenceSegment v1, v2, v3, v4;
    Graph<SequenceSegment> gr;
    private GraphContainer gc;

    @BeforeClass
    public static void runOnce() {
        ss1 = new DefaultSequence("reference");
        Sequence ss2 = new DefaultSequence("mutation");

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
        v2 = new SequenceSegment(s2, 11, 21, new SegmentString("AAAAAAAAAA"));
        v3 = new SequenceSegment(s3, 11, 21, new SegmentString("AAAAAAAAAA"));
        v4 = new SequenceSegment(s1, 21, 31, new SegmentString("AAAAAAAAAA"));
        gr = gf.getGraph();
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addVertex(v3);
        gr.addVertex(v4);
        gr.addEdge(v1, v2);
        gr.addEdge(v1, v3);
        gr.addEdge(v2, v4);
        gr.addEdge(v3, v4);
        Settings.set("empty_segments", "false");
        Settings.set("mutations", "true");
    }

    @Test
    public void emptySegmentsTest() {
        SequenceSegment v5 = new SequenceSegment(s2, 31, 41, new SegmentString("AAAAAAAAAA"));
        SequenceSegment v6 = new SequenceSegment(s1, 41, 51, new SegmentString("AAAAAAAAAA"));

        gr.addVertex(v5);
        gr.addVertex(v6);
        gr.addEdge(v4, v5);
        gr.addEdge(v4, v6);
        gr.addEdge(v5, v6);
        
        Settings.set("empty_segments", "true");
        gc = new GraphContainer(gr, ss1);
        assertEquals(7, gc.getVisibleSegments(0, 1).size());
    }

    @Test
    public void noMutationsTest() {
        Settings.set("mutations", "false");
        gc = new GraphContainer(gr, ss1);
        assertNull(v3.getMutation());
    }

    @Test
    public void noReferenceTest() {
        gc = new GraphContainer(gr, null);
        assertNull(v3.getMutation());
    }
    
    @Test
    public void numberOfBucketsTest() {
        gc = new GraphContainer(gr, ss1);
        assertEquals(1, gc.getBucketCache().getBuckets().size());
    }
    
    @Test
    public void allVisibleSegmentsTest() {
        gc = new GraphContainer(gr, ss1);
        assertEquals(4, gc.getVisibleSegments(0, 1).size());
        assertNotNull(v3.getMutation());
    }
    
    @Test
    public void nullAllVisibleSegmentsTest() {
        gc = new GraphContainer(gr, ss1);
        gc.setVisible(null);
        assertEquals(4, gc.getVisibleSegments(0, 1).size());
    }
    
    @Test
    public void subselectionVisibleSegmentsTest() {
        gc = new GraphContainer(gr, ss1);
        gc.setVisible(s2);
        assertEquals(3, gc.getVisibleSegments(0, 1).size());
    }
    
    @Test
    public void emptySubselectionVisibleSegmentsTest() {
        gc = new GraphContainer(gr, ss1);
        gc.setVisible(new HashSet<>());
        assertEquals(0, gc.getVisibleSegments(0, 1).size());
    }
}
