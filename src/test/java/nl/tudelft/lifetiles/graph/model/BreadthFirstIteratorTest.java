package nl.tudelft.lifetiles.graph.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import nl.tudelft.lifetiles.graph.traverser.UnifiedPositionTraverser;
import nl.tudelft.lifetiles.sequence.model.DefaultSequence;
import nl.tudelft.lifetiles.sequence.model.SegmentString;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

import org.junit.Before;
import org.junit.Test;

public class BreadthFirstIteratorTest {

    Graph<SequenceSegment> graph;
    HashSet<Sequence> s1, s2, s3;
    List<SequenceSegment> vertices;

    @Before
    public void setUp() throws Exception {
        GraphFactory<SequenceSegment> fact = FactoryProducer.getFactory();
        this.graph = fact.getGraph();

        Sequence ss1 = new DefaultSequence("reference");
        Sequence ss2 = new DefaultSequence("mutation");

        s1 = new HashSet<Sequence>();
        s1.add(ss1);
        s1.add(ss2);

        s2 = new HashSet<Sequence>();
        s2.add(ss1);

        s3 = new HashSet<Sequence>();
        s3.add(ss2);

        SegmentString content = new SegmentString("AC");

        vertices = Arrays.asList(
                new SequenceSegment(s1, 1, 11, content),
                new SequenceSegment(s2, 11, 21, content),
                new SequenceSegment(s2, 21, 31, content),
                new SequenceSegment(s2, 31, 41, content),
                new SequenceSegment(s3, 11, 41, content),
                new SequenceSegment(s2, 41, 51, content));

        vertices.forEach(graph::addVertex);

        graph.addEdge(vertices.get(0), vertices.get(1));
        graph.addEdge(vertices.get(1), vertices.get(2));
        graph.addEdge(vertices.get(2), vertices.get(3));
        graph.addEdge(vertices.get(3), vertices.get(5));
        graph.addEdge(vertices.get(0), vertices.get(4));
        graph.addEdge(vertices.get(4), vertices.get(5));

        UnifiedPositionTraverser traverser = new UnifiedPositionTraverser();
        traverser.unifyGraph(graph);
    }

    @Test
    public void testHasNext() {
        BreadthFirstIterator<SequenceSegment> it = new BreadthFirstIterator<>(
                graph);
        assertTrue(it.hasNext());
    }

    @Test
    public void testOrder() {
        BreadthFirstIterator<SequenceSegment> it = new BreadthFirstIterator<>(
                graph);
        List<SequenceSegment> expected = Arrays.asList(
                vertices.get(0), vertices.get(1), vertices.get(4),
                vertices.get(2), vertices.get(3), vertices.get(5));
        List<SequenceSegment> actual = Arrays.asList(it.next(), it.next(),
                it.next(), it.next(), it.next(), it.next());
        assertEquals(expected, actual);
    }

    @Test
    public void testReversedOrder() {
        BreadthFirstIterator<SequenceSegment> it = new BreadthFirstIterator<>(
                graph, true);
        List<SequenceSegment> expected = Arrays.asList(
                vertices.get(5), vertices.get(4), vertices.get(3),
                vertices.get(2), vertices.get(1), vertices.get(0));
        List<SequenceSegment> actual = Arrays.asList(it.next(), it.next(),
                it.next(), it.next(), it.next(), it.next());
        assertEquals(expected, actual);
    }

}
