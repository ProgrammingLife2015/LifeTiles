package nl.tudelft.lifetiles.graph.view;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;

import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import nl.tudelft.lifetiles.graph.controller.GraphController;
import nl.tudelft.lifetiles.graph.model.BucketCache;
import nl.tudelft.lifetiles.graph.model.FactoryProducer;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.graph.model.GraphFactory;
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
        Group result = tileview.drawGraph(buckets.getSegments(0), gr);
        assertEquals(4, ((Group) result.getChildrenUnmodifiable().get(0))
                .getChildrenUnmodifiable().size());
    }

    @Test
    public void drawGraphEdgesDrawGenericTest() {
        creategraph();
        buckets = new BucketCache(1, gr);
        Group result = tileview.drawGraph(buckets.getSegments(0), gr);
        assertEquals(3, ((Group) result.getChildrenUnmodifiable().get(1))
                .getChildrenUnmodifiable().size());
    }

    @Test
    public void drawVerticesTest() {
        creategraph();
        buckets = new BucketCache(1, gr);
        Group result = tileview.drawGraph(buckets.getSegments(0), gr);
        VertexView v1 = (VertexView) ((Group) result.getChildrenUnmodifiable()
                .get(0)).getChildrenUnmodifiable().get(0);
        VertexView v2 = (VertexView) ((Group) result.getChildrenUnmodifiable()
                .get(0)).getChildrenUnmodifiable().get(1);
        VertexView v3 = (VertexView) ((Group) result.getChildrenUnmodifiable()
                .get(0)).getChildrenUnmodifiable().get(2);
        VertexView v4 = (VertexView) ((Group) result.getChildrenUnmodifiable()
                .get(0)).getChildrenUnmodifiable().get(3);

        assertEquals(0, v1.getLayoutX(), 1e-10);
        assertEquals(0, v1.getLayoutY(), 1e-10);
        assertEquals(2 * 40 - 2, v1.getBoundsInParent().getHeight(), 1e-10);
        assertEquals(2 * 11 - 2, v1.getBoundsInParent().getWidth(), 1e-10);

        assertEquals(0 + (2 * 11), v2.getLayoutX(), 1e-10);
        assertEquals(0, v2.getLayoutY(), 1e-10);
        assertEquals(2 * 40 - 2, v2.getBoundsInParent().getHeight(), 1e-10);
        assertEquals(2 * 11 - 2, v2.getBoundsInParent().getWidth(), 1e-10);

        assertEquals(55, v3.getLayoutX(), 1e-10);
        assertEquals(0, v3.getLayoutY(), 1e-10);
        assertEquals(2 * 40 - 2, v3.getBoundsInParent().getHeight(), 1e-10);
        assertEquals(2 * 11 - 2, v3.getBoundsInParent().getWidth(), 1e-10);

        assertEquals(0 + (2 * 11) + (3 * 11) + 2 * 11, v4.getLayoutX(), 1e-10);
        assertEquals(0, v4.getLayoutY(), 1e-10);
        assertEquals(2 * 40 - 2, v4.getBoundsInParent().getHeight(), 1e-10);
        assertEquals(2 * 11 - 2, v4.getBoundsInParent().getWidth(), 1e-10);

    }

    @Test
    public void clickVertexTest() {
        creategraph();
        buckets = new BucketCache(1, gr);
        Group result = tileview.drawGraph(buckets.getSegments(0), gr);
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
        Group result = tileview.drawGraph(buckets.getSegments(0), gr);
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
        Group result = tileview.drawGraph(buckets.getSegments(0), gr);
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
