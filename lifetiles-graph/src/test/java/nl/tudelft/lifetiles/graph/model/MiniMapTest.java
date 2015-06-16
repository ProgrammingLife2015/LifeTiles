package nl.tudelft.lifetiles.graph.model;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javafx.embed.swing.JFXPanel;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

import org.junit.Before;
import org.junit.Test;

public class MiniMapTest {

    static final String testGraphFilename = "/data/test_set/simple_graph";

    private MiniMap miniMap;
    private BucketCache bucketCache;

    @Before
    public void setUp() throws URISyntaxException, IOException {
        new JFXPanel(); // force to initialize Toolkit

        GraphFactory<SequenceSegment> factory = FactoryProducer.getFactory();
        DefaultGraphParser parser = new DefaultGraphParser();

        File edgefile = new File(this.getClass()
                .getResource(testGraphFilename + ".edge.graph").toURI());
        File vertexfile = new File(this.getClass()
                .getResource(testGraphFilename + ".node.graph").toURI());

        bucketCache = new BucketCache(16, parser.parseGraph(vertexfile, edgefile, factory));
        miniMap = new MiniMap(bucketCache);
    }

    @Test
    public void getStopsTest() {
        assertEquals(bucketCache.getNumberBuckets(), miniMap.getStops().size());
    }

}
