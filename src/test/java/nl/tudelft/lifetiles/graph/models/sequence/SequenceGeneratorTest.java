package nl.tudelft.lifetiles.graph.models.sequence;

import static org.junit.Assert.assertEquals;
import nl.tudelft.lifetiles.graph.models.DefaultGraphParser;
import nl.tudelft.lifetiles.graph.models.FactoryProducer;
import nl.tudelft.lifetiles.graph.models.Graph;
import nl.tudelft.lifetiles.graph.models.GraphFactory;

import org.junit.Before;
import org.junit.Test;

public class SequenceGeneratorTest {
    SequenceGenerator sg;

    @Before
    public void setUp() {
        FactoryProducer<SequenceSegment> fp = new FactoryProducer<SequenceSegment>();
        GraphFactory<SequenceSegment> gf = fp.getFactory("JGraphT");
        DefaultGraphParser parser = new DefaultGraphParser();
        Graph<SequenceSegment> gr = parser.parseFile(
                "data/test_set/simple_graph", gf);
        sg = new SequenceGenerator(gr);
    }

    @Test
    public void generateSequencesTest() {
        assertEquals(2, sg.generateSequences().size());
    }
}
