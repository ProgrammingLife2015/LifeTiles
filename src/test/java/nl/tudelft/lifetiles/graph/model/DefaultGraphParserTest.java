package nl.tudelft.lifetiles.graph.model;

import static org.junit.Assert.assertEquals;

import java.io.File;

import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

import org.junit.Test;

public class DefaultGraphParserTest {
    static final String testGraphFilename = "/data/test_set/simple_graph";

    @Test
    public void parseGraphTest() throws Exception {
        GraphFactory<SequenceSegment> gf = FactoryProducer
                .getFactory("JGraphT");
        DefaultGraphParser parser = new DefaultGraphParser();

        Graph<SequenceSegment> gr;

        File edgefile = new File(this.getClass()
                .getResource(testGraphFilename + ".edge.graph").toURI());
        File vertexfile = new File(this.getClass()
                .getResource(testGraphFilename + ".node.graph").toURI());
        gr = parser.parseGraph(vertexfile, edgefile, gf);
        assertEquals(2849, gr.getAllVertices().size());
        assertEquals(3842, gr.getAllEdges().size());

    }

}
