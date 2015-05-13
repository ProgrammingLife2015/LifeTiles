package nl.tudelft.lifetiles.graph.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nl.tudelft.lifetiles.graph.models.DefaultGraphParser;
import nl.tudelft.lifetiles.graph.models.FactoryProducer;
import nl.tudelft.lifetiles.graph.models.Graph;
import nl.tudelft.lifetiles.graph.models.GraphFactory;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;

public class DefaultGraphParserTest {

    @Test
    public void parseGraphTest() {
        FactoryProducer<SequenceSegment> fp = new FactoryProducer<SequenceSegment>();
        GraphFactory<SequenceSegment> gf = fp.getFactory("JGraphT");
        DefaultGraphParser parser = new DefaultGraphParser();
        Graph<SequenceSegment> gr = parser.parseFile(
                "data/test_graph/test_graph", gf);
        assertEquals(2849, gr.getAllVertices().size());
        assertEquals(3842, gr.getAllEdges().size());
    }

}
