package nl.tudelft.lifetiles.graph.models.jgrapht;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import nl.tudelft.lifetiles.graph.models.Edge;
import nl.tudelft.lifetiles.graph.models.FactoryProducer;
import nl.tudelft.lifetiles.graph.models.Graph;
import nl.tudelft.lifetiles.graph.models.GraphFactory;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Rutger van den Berg
 *
 */
public class GraphAdapterTest {
    GraphFactory<SequenceSegment> gf;
    static FactoryProducer<SequenceSegment> fp;
    SequenceSegment v1, v2;
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
        gr = gf.getGraph();
    }

    @Test
    public void testAddVertex() {
        assertEquals(0, gr.getAllVertices().size());
        gr.addVertex(v1);
        assertEquals(1, gr.getAllVertices().size());
        gr.addVertex(v2);
        assertEquals(2, gr.getAllVertices().size());
    }

    @Test
    public void testAddEdge() {
        gr.addVertex(v1);
        gr.addVertex(v2);
        assertEquals(0, gr.getAllEdges().size());
        gr.addEdge(v1, v2);
        assertEquals(1, gr.getAllEdges().size());
    }

    @Test
    public void testAddWrongEdge() {
        gr.addVertex(v1);
        assertEquals(0, gr.getAllEdges().size());
        assertFalse(gr.addEdge(v1, v2));
        assertEquals(0, gr.getAllEdges().size());
    }

    @Test
    public void testGetSource() {
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addEdge(v1, v2);
        Set<SequenceSegment> s = gr.getSource();
        assertTrue(s.contains(v1));
        assertEquals(1, s.size());
    }
    
    @Test
    public void testGetSink() {
    	gr.addVertex(v1);
    	gr.addVertex(v2);
    	gr.addEdge(v1, v2);
    	Set<SequenceSegment> s = gr.getSink();
    	assert (s.contains(v2));
    	assertEquals(1, s.size());
    }
    
    @Test
    public void testGetIncoming() {
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addEdge(v1, v2);
        Set<Edge<SequenceSegment>> inc = gr.getIncoming(v2);
        assertEquals(1, inc.size());
        assertEquals(v1, gr.getSource(inc.iterator().next()));
    }

    @Test
    public void testGetOutgoing() {
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addEdge(v1, v2);
        Set<Edge<SequenceSegment>> inc = gr.getOutgoing(v1);
        assertEquals(1, inc.size());
        assertEquals(v2, gr.getDestination(inc.iterator().next()));
    }

    @Test
    public void testGetWrongIncoming() {
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addEdge(v1, v2);
        Set<Edge<SequenceSegment>> inc = gr.getIncoming(v1);
        assertEquals(0, inc.size());
    }

    @Test
    public void testGetEdgeSource() {
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addEdge(v1, v2);
        Set<Edge<SequenceSegment>> inc = gr.getIncoming(v2);
        assertEquals(v1, gr.getSource(inc.iterator().next()));
    }

    @Test
    public void testGetEdgeDestination() {
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addEdge(v1, v2);
        Set<Edge<SequenceSegment>> inc = gr.getIncoming(v2);
        assertEquals(v2, gr.getDestination(inc.iterator().next()));
    }

    @Test
    public void testDivideEdge() {
    	gr.addVertex(v1);
    	gr.addVertex(v2);
    	gr.addEdge(v1, v2);
    	Set<Edge<SequenceSegment>> inc = gr.getIncoming(v2);
    	SequenceSegment v3 = new SequenceSegment(null, 0, 0, null);
    	gr.divideEdge(inc.iterator().next(), v3);
    	assertEquals(v3, gr.getSource(gr.getIncoming(v2).iterator().next()));
    	assertEquals(v3, gr.getDestination(gr.getOutgoing(v1).iterator().next()));
    }

}
