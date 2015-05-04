package nl.tudelft.lifetiles.graph;

import static org.junit.Assert.assertNull;
import nl.tudelft.lifetiles.graph.jgrapht.JGraphTGraphFactory;

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
        assert (gf instanceof JGraphTGraphFactory<?>);
    }

    @Test
    public void testGetWrongParam() {
        GraphFactory<String> gf = fp.getFactory("libfoo");
        assertNull(gf);
    }

    @Test
    public void testGet() {
        GraphFactory<String> gf = fp.getFactory();
        assert (gf instanceof JGraphTGraphFactory<?>);
    }

}