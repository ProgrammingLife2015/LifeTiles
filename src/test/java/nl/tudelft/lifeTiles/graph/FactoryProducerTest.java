package nl.tudelft.lifeTiles.graph;

import nl.tudelft.lifeTiles.graph.jgrapht.JGraphTGraphFactoryImplementation;

import org.junit.Before;
import org.junit.Test;

public class FactoryProducerTest {
    FactoryProducer<String> fp;
    @Before
    public void setUp() throws Exception {
        fp = new FactoryProducer<String>();
    }

    @Test
    public void testGetWithParam() {
        GraphFactory<String> gf = fp.getFactory("JGraphT");
        assert(gf instanceof JGraphTGraphFactoryImplementation<?>);
    }

    @Test
    public void testGet() {
        GraphFactory<String> gf = fp.getFactory();
        assert(gf instanceof JGraphTGraphFactoryImplementation<?>);
    }

}
