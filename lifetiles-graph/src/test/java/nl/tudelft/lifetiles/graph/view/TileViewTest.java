package nl.tudelft.lifetiles.graph.view;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import nl.tudelft.lifetiles.annotation.model.KnownMutation;
import nl.tudelft.lifetiles.annotation.model.KnownMutationMapper;
import nl.tudelft.lifetiles.annotation.model.KnownMutationParser;
import nl.tudelft.lifetiles.core.util.Settings;
import nl.tudelft.lifetiles.graph.controller.GraphController;
import nl.tudelft.lifetiles.graph.model.BucketCache;
import nl.tudelft.lifetiles.graph.model.FactoryProducer;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.graph.model.GraphFactory;
import nl.tudelft.lifetiles.sequence.Mutation;
import nl.tudelft.lifetiles.sequence.model.DefaultSequence;
import nl.tudelft.lifetiles.sequence.model.SegmentEmpty;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TileViewTest {
    @Mock
    GraphController controller;
    Sequence s1, s2, s3, s4;
    TileView tileview;
    Graph<SequenceSegment> gr;
    BucketCache buckets;

    @Before
    public void setUp() {
        controller = Mockito.mock(GraphController.class);
        tileview = new TileView(controller);
        s1 = new DefaultSequence("TKK-REF");
        s2 = new DefaultSequence("Not TKK-REF");
        s3 = new DefaultSequence("Imposter");
        s4 = new DefaultSequence("4Zftr3H");
    }

    @Test
    public void drawGraphVerticesDrawGenericTest() {
        creategraph();
        buckets = new BucketCache(1, gr);
        Group result = tileview.drawGraph(buckets.getSegments(0, 1), gr, null,
                1);
        assertEquals(4, ((Group) result.getChildrenUnmodifiable().get(0))
                .getChildrenUnmodifiable().size());
    }

    // @Test TODO integrate this in the edge drawing settings
    public void drawGraphEdgesDrawGenericTest() {
        creategraph();
        buckets = new BucketCache(1, gr);
        Group result = tileview.drawGraph(buckets.getSegments(0, 0), gr, null,
                1);
        assertEquals(3, ((Group) result.getChildrenUnmodifiable().get(1))
                .getChildrenUnmodifiable().size());
    }

    @Test
    public void drawVerticeTest() {
        GraphFactory<SequenceSegment> gf = FactoryProducer
                .getFactory("JGraphT");

        Graph<SequenceSegment> graph;

        SequenceSegment v1;
        v1 = new SequenceSegment(new HashSet<Sequence>(Arrays.asList(s1, s2)),
                5, 7, new SegmentEmpty(2));

        v1.setUnifiedStart(0);
        v1.setUnifiedEnd(2);

        graph = gf.getGraph();
        graph.addVertex(v1);

        BucketCache buckets = new BucketCache(1, graph);
        Group result = tileview.drawGraph(buckets.getSegments(0, 1), gr, null,
                1);
        VertexView vView1 = (VertexView) ((Group) result
                .getChildrenUnmodifiable().get(0)).getChildrenUnmodifiable()
                .get(0);

        assertEquals(0, vView1.getLayoutX(), 1e-10);
        assertEquals(0, vView1.getLayoutY(), 1e-10);
        assertEquals(2 * 40 - 2, vView1.getBoundsInParent().getHeight(), 1e-10);
        assertEquals(2 * 11 - 2, vView1.getBoundsInParent().getWidth(), 1e-10);

    }

    private Circle bookmark;
    private VertexView vView1;

    @Test
    public void drawAnnotationTest() throws IOException, InterruptedException {
        GraphFactory<SequenceSegment> gf = FactoryProducer
                .getFactory("JGraphT");

        Graph<SequenceSegment> graph;

        SequenceSegment v1;
        v1 = new SequenceSegment(new HashSet<Sequence>(Arrays.asList(s1, s2)),
                0, 20, new SegmentEmpty(20));

        v1.setUnifiedStart(0);
        v1.setUnifiedEnd(20);

        graph = gf.getGraph();
        graph.addVertex(v1);

        BucketCache buckets = new BucketCache(1, graph);

        File file = new File(
                "./src/test/resources/data/test_annotations/simple_annotations.txt");

        List<KnownMutation> parse = KnownMutationParser
                .parseKnownMutations(file);
        Sequence reference = graph.getSources().iterator().next().getSources()
                .iterator().next();

        Map<SequenceSegment, List<KnownMutation>> annotations = KnownMutationMapper
                .mapAnnotations(graph, parse, reference);

        // Hack so you make a tooltip, javafx toolkit need to be initialised for
        // that
        JFXPanel panel = new JFXPanel();
        Platform.runLater(() -> {

            Group result = tileview.drawGraph(buckets.getSegments(0, 1), graph,
                    annotations, 1);

            bookmark = (Circle) ((Group) result.getChildrenUnmodifiable().get(2))
                    .getChildrenUnmodifiable().get(0);

            vView1 = (VertexView) ((Group) result.getChildrenUnmodifiable()
                    .get(0)).getChildrenUnmodifiable().get(0);

        });

        Thread.sleep(1000);
        assertEquals(bookmark.getRadius(), Double.parseDouble(Settings.get("bookmark_radius")), 1e-10);
    }

    @Test
    public void clickVertexTest() {
        creategraph();
        buckets = new BucketCache(1, gr);
        Group result = tileview.drawGraph(buckets.getSegments(0, 1), gr, null,
                1);
        Event.fireEvent(((Group) result.getChildrenUnmodifiable().get(0))
                .getChildrenUnmodifiable().get(0), new MouseEvent(
                        MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1,
                        true, true, true, true, true, true, true, true, true, true,
                        null));
        Mockito.verify(controller).clicked(Mockito.any());
    }

    @Test
    public void hoveringEnterVertexText() {
        creategraph();
        buckets = new BucketCache(1, gr);
        Group result = tileview.drawGraph(buckets.getSegments(0, 1), gr, null,
                1);
        Event.fireEvent(((Group) result.getChildrenUnmodifiable().get(0))
                .getChildrenUnmodifiable().get(0), new MouseEvent(
                        MouseEvent.MOUSE_ENTERED, 0, 0, 0, 0, MouseButton.PRIMARY, 1,
                        true, true, true, true, true, true, true, true, true, true,
                        null));
        Mockito.verify(controller).hovered(Mockito.any(), Mockito.eq(true));
    }

    @Test
    public void hoveringExitVertexText() {
        creategraph();
        buckets = new BucketCache(1, gr);
        Group result = tileview.drawGraph(buckets.getSegments(0, 1), gr, null,
                1);
        Event.fireEvent(((Group) result.getChildrenUnmodifiable().get(0))
                .getChildrenUnmodifiable().get(0), new MouseEvent(
                        MouseEvent.MOUSE_EXITED, 0, 0, 0, 0, MouseButton.PRIMARY, 1,
                        true, true, true, true, true, true, true, true, true, true,
                        null));
        Mockito.verify(controller).hovered(Mockito.any(), Mockito.eq(false));
    }

    private void creategraph() {
        GraphFactory<SequenceSegment> gf = FactoryProducer
                .getFactory("JGraphT");
        SequenceSegment v1, v2, v3, v4;
        v1 = new SequenceSegment(new HashSet<Sequence>(Arrays.asList(s1, s2)),
                5, 7, new SegmentEmpty(2));
        v2 = new SequenceSegment(new HashSet<Sequence>(Arrays.asList(s1, s2)),
                7, 9, new SegmentEmpty(2));
        v3 = new SequenceSegment(new HashSet<Sequence>(Arrays.asList(s1, s2)),
                0, 2, new SegmentEmpty(2));
        v4 = new SequenceSegment(new HashSet<Sequence>(Arrays.asList(s1, s2)),
                2, 5, new SegmentEmpty(2));
        v1.setUnifiedStart(0);
        v1.setUnifiedEnd(2);
        v2.setUnifiedStart(2);
        v2.setUnifiedEnd(5);
        v3.setUnifiedStart(5);
        v3.setUnifiedEnd(7);
        v4.setUnifiedStart(7);
        v4.setUnifiedEnd(9);
        v2.setMutation(Mutation.DELETION);
        gr = gf.getGraph();
        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addVertex(v3);
        gr.addVertex(v4);
        gr.addEdge(v1, v2);
        gr.addEdge(v2, v3);
        gr.addEdge(v3, v4);
    }

}
