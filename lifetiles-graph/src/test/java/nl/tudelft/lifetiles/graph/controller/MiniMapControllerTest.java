package nl.tudelft.lifetiles.graph.controller;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;

import javafx.scene.control.ScrollPane;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import nl.tudelft.lifetiles.core.util.Logging;
import nl.tudelft.lifetiles.graph.model.DefaultGraphParser;
import nl.tudelft.lifetiles.graph.model.FactoryProducer;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.graph.model.GraphContainer;
import nl.tudelft.lifetiles.graph.model.GraphFactory;
import nl.tudelft.lifetiles.sequence.model.DefaultSequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MiniMapControllerTest {

    static final String testGraphFilename = "/data/test_set/simple_graph";

    private MiniMapController controller;
    private ScrollPane scrollPane;

    @BeforeClass
    public static void before() {
        Logging.setLevel(Level.SEVERE);
    }

    @Before
    public void setUp() throws URISyntaxException, IOException {
        GraphFactory<SequenceSegment> factory = FactoryProducer.getFactory();
        DefaultGraphParser parser = new DefaultGraphParser();

        File edgefile = new File(this.getClass()
                .getResource(testGraphFilename + ".edge.graph").toURI());
        File vertexfile = new File(this.getClass()
                .getResource(testGraphFilename + ".node.graph").toURI());

        Graph<SequenceSegment> graph = parser.parseGraph(vertexfile, edgefile,
                factory);
        GraphContainer model = new GraphContainer(graph, new DefaultSequence(
                "reference"));

        scrollPane = new ScrollPane();
        controller = new MiniMapController(scrollPane, model);
    }

    @Test
    public void drawMiniMapTest() {
        controller.drawMiniMap();
        Paint fill = controller.getMiniMap().getBackground().getFills().get(0)
                .getFill();

        assertThat(fill, instanceOf(LinearGradient.class));
    }

}
