package nl.tudelft.lifetiles.graph.models.jgrapht;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nl.tudelft.lifetiles.graph.models.Edge;
import nl.tudelft.lifetiles.graph.models.FactoryProducer;
import nl.tudelft.lifetiles.graph.models.Graph;
import nl.tudelft.lifetiles.graph.models.GraphFactory;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;

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
        v1 = new SequenceSegment(null, 0, 0, null);
        v2 = new SequenceSegment(null, 0, 0, null);
        v3 = new SequenceSegment(null, 0, 0, null);
        v4 = new SequenceSegment(null, 0, 0, null);
        gr = gf.getGraph();
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addVertex(v3);
        gr.addVertex(v4);
        gr.addEdge(v1, v2);
        gr.addEdge(v1, v3);
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

}
