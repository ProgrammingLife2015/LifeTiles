package nl.tudelft.lifetiles.graph.models;

import nl.tudelft.lifetiles.graph.models.jgrapht.JGraphTGraphFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FactoryProducerTest {
    FactoryProducer<String> fp;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
        thrown.expect(IllegalArgumentException.class);
        fp.getFactory("libfoo");
    }

    @Test
    public void testGet() {
        GraphFactory<String> gf = fp.getFactory();
        assert (gf instanceof JGraphTGraphFactory<?>);
    }

}
