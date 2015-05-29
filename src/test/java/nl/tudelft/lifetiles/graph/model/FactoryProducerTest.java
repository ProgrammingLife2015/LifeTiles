package nl.tudelft.lifetiles.graph.model;

import nl.tudelft.lifetiles.graph.model.jgrapht.JGraphTGraphFactory;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FactoryProducerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGetWithParam() {
        GraphFactory<String> gf = FactoryProducer.getFactory("JGraphT");
        assert (gf instanceof JGraphTGraphFactory<?>);
    }

    @Test
    public void testGetWrongParam() {
        thrown.expect(IllegalArgumentException.class);
        FactoryProducer.getFactory("libfoo");
    }

    @Test
    public void testGet() {
        GraphFactory<String> gf = FactoryProducer.getFactory();
        assert (gf instanceof JGraphTGraphFactory<?>);
    }

}
