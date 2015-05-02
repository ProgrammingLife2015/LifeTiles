package nl.tudelft.lifeTiles.graph;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DefaultParserTest {
    GraphParser parser;
    GraphFactory<SequenceSegment> gfact;

    @Before
    public void setUp() throws Exception {
        FactoryProducer<SequenceSegment> fp = new FactoryProducer<>();
        parser = new DefaultGraphParser();
        gfact = fp.getFactory("JGraphT");
    }

    @Test
    public void test() {
        Graph<SequenceSegment> g = parser.parseFile("simple_graph", gfact);
        assertEquals(4755, g.getAllVertices().size());
        assertEquals(6554, g.getAllEdges().size());
    }
}
