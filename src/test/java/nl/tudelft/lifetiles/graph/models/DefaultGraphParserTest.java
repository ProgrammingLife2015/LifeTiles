package nl.tudelft.lifetiles.graph.models;

import static org.junit.Assert.assertEquals;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;

import org.junit.Test;

public class DefaultGraphParserTest {

    @Test
    public void parseGraphTest() {
        FactoryProducer<SequenceSegment> fp = new FactoryProducer<SequenceSegment>();
        GraphFactory<SequenceSegment> gf = fp.getFactory("JGraphT");
        DefaultGraphParser parser = new DefaultGraphParser();
        Graph<SequenceSegment> gr = parser.parseFile(
                "data/test_set/simple_graph", gf);
        assertEquals(2849, gr.getAllVertices().size());
        assertEquals(3842, gr.getAllEdges().size());
    }

}
