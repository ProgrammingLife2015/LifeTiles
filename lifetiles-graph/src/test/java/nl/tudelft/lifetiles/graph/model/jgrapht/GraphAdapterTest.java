package nl.tudelft.lifetiles.graph.model.jgrapht;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import nl.tudelft.lifetiles.graph.model.Edge;
import nl.tudelft.lifetiles.graph.model.FactoryProducer;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.graph.model.GraphFactory;
import nl.tudelft.lifetiles.sequence.model.SegmentEmpty;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Rutger van den Berg
 *
 */
public class GraphAdapterTest {
    GraphFactory<SequenceSegment> gf;

    SequenceSegment v1, v2, v3, v4;

    Graph<SequenceSegment> gr;
    Graph<SequenceSegment> subgr;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        gf = FactoryProducer.getFactory("JGraphT");
        v1 = new SequenceSegment(new HashSet<Sequence>(), 0, 2,
                new SegmentEmpty(2));
        v2 = new SequenceSegment(new HashSet<Sequence>(), 0, 0,
                new SegmentEmpty(3));
        v3 = new SequenceSegment(new HashSet<Sequence>(), 3, 5,
                new SegmentEmpty(2));
        v4 = new SequenceSegment(new HashSet<Sequence>(), 1, 3,
                new SegmentEmpty(2));
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
        thrown.expect(IllegalArgumentException.class);
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
        Set<SequenceSegment> s = gr.getSources();
        assertTrue(s.contains(v1));
        assertEquals(1, s.size());
    }

    @Test
    public void testGetSink() {
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addEdge(v1, v2);
        Set<SequenceSegment> s = gr.getSinks();
        assertTrue(s.contains(v2));
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
        SequenceSegment v3 = new SequenceSegment(new HashSet<Sequence>(), 0, 3,
                new SegmentEmpty(0));
        gr.splitEdge(inc.iterator().next(), v3);
        assertEquals(v3, gr.getSource(gr.getIncoming(v2).iterator().next()));
        assertEquals(v3, gr
                .getDestination(gr.getOutgoing(v1).iterator().next()));
    }

    @Test
    public void testCopy() {
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addEdge(v1, v2);

        Graph<SequenceSegment> copy = gf.copy(gr);
        assertEquals(2, copy.getAllVertices().size());
        assertEquals(1, copy.getAllEdges().size());
    }

    @Test
    public void testSubGraphCreation() throws NotAJGraphTAdapterException {
        subgr = gf.getSubGraph(gr, null);
        assertEquals(subgr.getAllVertices().size(), gr.getAllVertices().size());
        assertEquals(subgr.getAllEdges().size(), gr.getAllEdges().size());
    }

    @Test
    public void testSubGraphVertices() throws NotAJGraphTAdapterException {
        gr.addVertex(v1);
        gr.addVertex(v2);
        subgr = gf.getSubGraph(gr, null);
        assertEquals(subgr.getAllVertices().size(), gr.getAllVertices().size());
    }

    @Test
    public void testSubGraphEdges() throws NotAJGraphTAdapterException {
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addEdge(v1, v2);
        subgr = gf.getSubGraph(gr, null);
        assertEquals(subgr.getAllEdges().size(), gr.getAllEdges().size());
    }

    @Test
    public void testSubGraphSubsetVertices() throws NotAJGraphTAdapterException {
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addVertex(v3);

        Set<SequenceSegment> cpy = new TreeSet<SequenceSegment>();
        cpy.addAll(gr.getAllVertices());
        cpy.remove(v3);
        cpy.remove(v2);

        subgr = gf.getSubGraph(gr, cpy);

        assertEquals(subgr.getAllVertices().size(),
                gr.getAllVertices().size() - 2);

    }

    @Test
    public void testSubGraphSubsetVerticesEdges()
            throws NotAJGraphTAdapterException {
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addVertex(v3);
        gr.addEdge(v1, v3);
        gr.addEdge(v2, v3);

        Set<SequenceSegment> cpy = new TreeSet<SequenceSegment>();
        cpy.addAll(gr.getAllVertices());
        cpy.remove(v3);

        subgr = gf.getSubGraph(gr, cpy);

        assertEquals(subgr.getAllEdges().size(), gr.getAllEdges().size() - 2);
    }

    @Test
    public void testAddVerticeSubGraph() throws NotAJGraphTAdapterException {
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addVertex(v3);

        Set<SequenceSegment> cpy = new TreeSet<SequenceSegment>();
        cpy.addAll(gr.getAllVertices());
        cpy.remove(v3);

        subgr = gf.getSubGraph(gr, cpy);
        subgr.addVertex(v3);
    }

    @Test
    public void testAddEdgeSubGraph() throws NotAJGraphTAdapterException {
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addVertex(v3);
        gr.addEdge(v1, v3);
        gr.addEdge(v2, v3);

        Set<SequenceSegment> cpy = new TreeSet<SequenceSegment>();
        cpy.addAll(gr.getAllVertices());
        cpy.remove(v3);

        subgr = gf.getSubGraph(gr, cpy);
        subgr.addVertex(v3);
        subgr.addEdge(v2, v3);
    }

    @Test
    public void testDeepCopyVertices() throws NotAJGraphTAdapterException {
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addVertex(v3);

        Set<SequenceSegment> cpy = new TreeSet<SequenceSegment>();
        cpy.addAll(gr.getAllVertices());

        subgr = gf.getSubGraph(gr, cpy);
        subgr = gf.deepcopy(subgr);

        assertTrue(gr.getAllVertices().containsAll(subgr.getAllVertices()));
    }

    @Test
    public void testDeepCopyEdges() throws NotAJGraphTAdapterException {
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addVertex(v3);
        gr.addEdge(v1, v3);
        gr.addEdge(v2, v3);

        Set<SequenceSegment> cpy = new TreeSet<SequenceSegment>();
        cpy.addAll(gr.getAllVertices());
        subgr = gf.getSubGraph(gr, cpy);
        subgr = gf.deepcopy(subgr);

        assertTrue(gr.getAllEdges().containsAll(subgr.getAllEdges()));

    }

}
