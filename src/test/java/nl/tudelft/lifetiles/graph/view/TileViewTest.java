package nl.tudelft.lifetiles.graph.view;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;

import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import nl.tudelft.lifetiles.graph.controller.GraphController;
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

        Group result = tileview.drawGraph(gr);

        assertEquals(4, ((Group) result.getChildrenUnmodifiable().get(0))
                .getChildrenUnmodifiable().size());

    }

    @Test
    public void drawGraphEdgesDrawGenericTest() {
        creategraph();
        Group result = tileview.drawGraph(gr);

        assertEquals(3, ((Group) result.getChildrenUnmodifiable().get(1))
                .getChildrenUnmodifiable().size());

    }

    @Test
    public void drawChainVerticeTest() {
        creategraph();
        Group result = tileview.drawGraph(gr);

        VertexView v1 = (VertexView) ((Group) result.getChildrenUnmodifiable()
                .get(0)).getChildrenUnmodifiable().get(0);

        VertexView v2 = (VertexView) ((Group) result.getChildrenUnmodifiable()
                .get(0)).getChildrenUnmodifiable().get(1);

        VertexView v3 = (VertexView) ((Group) result.getChildrenUnmodifiable()
                .get(0)).getChildrenUnmodifiable().get(2);
        VertexView v4 = (VertexView) ((Group) result.getChildrenUnmodifiable()
                .get(0)).getChildrenUnmodifiable().get(3);

        // V3 is first element, must be drawned at the beginning
        assertEquals(0, v3.getLayoutX(), 1e-10);
        assertEquals(0, v3.getLayoutY(), 1e-10);
        // V3 contains two sequences, so height = 2 * VERTICALSCALE - SPACING
        assertEquals(2 * 40 - 2, v3.getBoundsInParent().getHeight(), 1e-10);
        // v3 is "--", so width = 2 * HORIZONTALSCALE -SPACINGS
        assertEquals(2 * 11 - 2, v3.getBoundsInParent().getWidth(), 1e-10);

        // V4 is second element, must be drawned after the first
        assertEquals(0 + (2 * 11), v4.getLayoutX(), 1e-10);
        assertEquals(0, v4.getLayoutY(), 1e-10);
        // V4 contains two sequences, so height = 2 * VERTICALSCALE - SPACING
        assertEquals(2 * 40 - 2, v4.getBoundsInParent().getHeight(), 1e-10);
        // v4 is "--", so width = 2 * HORIZONTALSCALE -SPACINGS
        assertEquals(2 * 11 - 2, v4.getBoundsInParent().getWidth(), 1e-10);

        // V1 is third element, must be drawned after the second
        assertEquals(0 + 2 * 11 + 3 * 11, v1.getLayoutX(), 1e-10);
        assertEquals(0, v1.getLayoutY(), 1e-10);
        // V1 contains two sequences, so height = 2 * VERTICALSCALE - SPACING
        assertEquals(2 * 40 - 2, v1.getBoundsInParent().getHeight(), 1e-10);
        // v1 is "--", so width = 2 * HORIZONTALSCALE -SPACINGS
        assertEquals(2 * 11 - 2, v1.getBoundsInParent().getWidth(), 1e-10);

        // V2 is fourth element, must be drawned after the third
        assertEquals(0 + (2 * 11) + (3 * 11) + (2 * 11), v2.getLayoutX(), 1e-10);
        assertEquals(0, v2.getLayoutY(), 1e-10);
        // V2 contains two sequences, so height = 2 * VERTICALSCALE - SPACING
        assertEquals(2 * 40 - 2, v2.getBoundsInParent().getHeight(), 1e-10);
        // v2 is "--", so width = 2 * HORIZONTALSCALE -SPACINGS
        assertEquals(2 * 11 - 2, v2.getBoundsInParent().getWidth(), 1e-10);

    }

    @Test
    public void drawSplitLengthVerticeTest() {
        createSplitGraph();
        Group result = tileview.drawGraph(gr);

        VertexView v1 = (VertexView) ((Group) result.getChildrenUnmodifiable()
                .get(0)).getChildrenUnmodifiable().get(0);

        VertexView v2 = (VertexView) ((Group) result.getChildrenUnmodifiable()
                .get(0)).getChildrenUnmodifiable().get(1);

        VertexView v3 = (VertexView) ((Group) result.getChildrenUnmodifiable()
                .get(0)).getChildrenUnmodifiable().get(2);
        VertexView v4 = (VertexView) ((Group) result.getChildrenUnmodifiable()
                .get(0)).getChildrenUnmodifiable().get(3);

        // V1 is first element, must be drawned at the beginning
        assertEquals(0, v1.getLayoutX(), 1e-10);
        assertEquals(0, v1.getLayoutY(), 1e-10);
        // V1 contains two sequences, so height = 2 * VERTICALSCALE - SPACING
        assertEquals(2 * 40 - 2, v1.getBoundsInParent().getHeight(), 1e-10);
        // v1 is "--", so width = 2 * HORIZONTALSCALE -SPACINGS
        assertEquals(2 * 11 - 2, v1.getBoundsInParent().getWidth(), 1e-10);

        // V2 is second element, must be drawned after the first
        assertEquals(0 + (2 * 11), v2.getLayoutX(), 1e-10);
        // V2 is the lower element
        assertEquals(0 + 40, v2.getLayoutY(), 1e-10);
        // V2 contains ome sequence, so height = VERTICALSCALE - SPACING
        assertEquals(40 - 2, v2.getBoundsInParent().getHeight(), 1e-10);
        // v2 is "---", so width = 3 * HORIZONTALSCALE -SPACINGS
        assertEquals(3 * 11 - 2, v2.getBoundsInParent().getWidth(), 1e-10);

        // V3 is second element, must be drawned after the first
        assertEquals(0 + (2 * 11), v3.getLayoutX(), 1e-10);
        // V3 is the upper element
        assertEquals(0, v3.getLayoutY(), 1e-10);
        // V3 contains one sequence, so height = VERTICALSCALE - SPACING
        assertEquals(40 - 2, v3.getBoundsInParent().getHeight(), 1e-10);
        // v3 is "--", so width = 2 * HORIZONTALSCALE -SPACINGS
        assertEquals(2 * 11 - 2, v3.getBoundsInParent().getWidth(), 1e-10);

        // V4 is third element, must be drawned after the second
        assertEquals(0 + (2 * 11) + (3 * 11), v4.getLayoutX(), 1e-10);
        assertEquals(0, v4.getLayoutY(), 1e-10);
        // V4 contains two sequences, so height = 2 * VERTICALSCALE - SPACING
        assertEquals(2 * 40 - 2, v4.getBoundsInParent().getHeight(), 1e-10);
        // v4 is "--", so width = 2 * HORIZONTALSCALE -SPACINGS
        assertEquals(2 * 11 - 2, v4.getBoundsInParent().getWidth(), 1e-10);
    }

    @Test
    public void drawCrossedLengthVerticeTest() {
        createCrossGraph();
        Group result = tileview.drawGraph(gr);

        VertexView v1 = (VertexView) ((Group) result.getChildrenUnmodifiable()
                .get(0)).getChildrenUnmodifiable().get(0);

        VertexView v2 = (VertexView) ((Group) result.getChildrenUnmodifiable()
                .get(0)).getChildrenUnmodifiable().get(1);

        VertexView v3 = (VertexView) ((Group) result.getChildrenUnmodifiable()
                .get(0)).getChildrenUnmodifiable().get(2);
        VertexView v4 = (VertexView) ((Group) result.getChildrenUnmodifiable()
                .get(0)).getChildrenUnmodifiable().get(3);

        // V1 is first element, must be drawned at the beginning
        assertEquals(0, v1.getLayoutX(), 1e-10);
        assertEquals(0, v1.getLayoutY(), 1e-10);
        // V1 contains two sequences, so height = 2 * VERTICALSCALE - SPACING
        assertEquals(2 * 40 - 2, v1.getBoundsInParent().getHeight(), 1e-10);
        // v1 is "--", so width = 2 * HORIZONTALSCALE -SPACINGS
        assertEquals(2 * 11 - 2, v1.getBoundsInParent().getWidth(), 1e-10);

        // V2 is first element, must be drawned at the beginning
        assertEquals(0, v2.getLayoutX(), 1e-10);
        // V2 is the lower element
        assertEquals(0 + 80, v2.getLayoutY(), 1e-10);
        // V2 contains two sequences, so height = 2 * VERTICALSCALE - SPACING
        assertEquals(2 * 40 - 2, v2.getBoundsInParent().getHeight(), 1e-10);
        // v2 is "---", so width = 3 * HORIZONTALSCALE -SPACINGS
        assertEquals(3 * 11 - 2, v2.getBoundsInParent().getWidth(), 1e-10);

        // V3 is second element, must be drawned after the first
        assertEquals(0 + (3 * 11), v3.getLayoutX(), 1e-10);
        // V3 is the lower element
        assertEquals(0 + 80, v3.getLayoutY(), 1e-10);
        // V3 contains two sequences, so height = 2 * VERTICALSCALE - SPACING
        assertEquals(2 * 40 - 2, v3.getBoundsInParent().getHeight(), 1e-10);
        // v3 is "--", so width = 2 * HORIZONTALSCALE -SPACINGS
        assertEquals(2 * 11 - 2, v3.getBoundsInParent().getWidth(), 1e-10);

        // V4 is second element, must be drawned after the first
        assertEquals(0 + (3 * 11), v4.getLayoutX(), 1e-10);
        // V4 is the upper element
        assertEquals(0, v4.getLayoutY(), 1e-10);
        // V4 contains two sequences, so height = 2 * VERTICALSCALE - SPACING
        assertEquals(2 * 40 - 2, v4.getBoundsInParent().getHeight(), 1e-10);
        // v4 is "--", so width = 2 * HORIZONTALSCALE -SPACINGS
        assertEquals(2 * 11 - 2, v4.getBoundsInParent().getWidth(), 1e-10);
    }

    @Test
    public void clickVertexTest() {
        creategraph();
        Group result = tileview.drawGraph(gr);
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
        Group result = tileview.drawGraph(gr);

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
        Group result = tileview.drawGraph(gr);

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

    private void createSplitGraph() {
        GraphFactory<SequenceSegment> gf = FactoryProducer
                .getFactory("JGraphT");

        SequenceSegment v1, v2, v3, v4;

        v1 = new SequenceSegment(new HashSet<Sequence>(Arrays.asList(s1, s2)),
                0, 2, new SegmentEmpty(2));
        v2 = new SequenceSegment(new HashSet<Sequence>(Arrays.asList(s1)), 2,
                5, new SegmentEmpty(3));
        v3 = new SequenceSegment(new HashSet<Sequence>(Arrays.asList(s2)), 2,
                4, new SegmentEmpty(2));
        v4 = new SequenceSegment(new HashSet<Sequence>(Arrays.asList(s1, s2)),
                5, 7, new SegmentEmpty(2));

        v1.setUnifiedStart(0);
        v1.setUnifiedEnd(2);
        v2.setUnifiedStart(2);
        v2.setUnifiedEnd(5);
        v3.setUnifiedStart(2);
        v3.setUnifiedEnd(4);
        v4.setUnifiedStart(5);
        v4.setUnifiedEnd(7);

        v3.setMutation(Mutation.POLYMORPHISM);

        gr = gf.getGraph();

        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addVertex(v3);
        gr.addVertex(v4);

        gr.addEdge(v1, v2);
        gr.addEdge(v1, v3);
        gr.addEdge(v2, v4);
        gr.addEdge(v3, v4);
    }

    private void createCrossGraph() {
        GraphFactory<SequenceSegment> gf = FactoryProducer
                .getFactory("JGraphT");

        SequenceSegment v1, v2, v3, v4;

        v1 = new SequenceSegment(new HashSet<Sequence>(Arrays.asList(s1, s2)),
                0, 2, new SegmentEmpty(2));
        v2 = new SequenceSegment(new HashSet<Sequence>(Arrays.asList(s3, s4)),
                0, 3, new SegmentEmpty(3));
        v3 = new SequenceSegment(new HashSet<Sequence>(Arrays.asList(s1, s3)),
                3, 5, new SegmentEmpty(2));
        v4 = new SequenceSegment(new HashSet<Sequence>(Arrays.asList(s2, s4)),
                3, 5, new SegmentEmpty(2));

        v1.setUnifiedStart(0);
        v1.setUnifiedEnd(2);
        v2.setUnifiedStart(0);
        v2.setUnifiedEnd(3);
        v3.setUnifiedStart(3);
        v3.setUnifiedEnd(5);
        v4.setUnifiedStart(3);
        v4.setUnifiedEnd(5);

        v2.setMutation(Mutation.POLYMORPHISM);
        v4.setMutation(Mutation.POLYMORPHISM);

        gr = gf.getGraph();

        gr.addVertex(v1);
        gr.addVertex(v2);
        gr.addVertex(v3);
        gr.addVertex(v4);

        gr.addEdge(v1, v3);
        gr.addEdge(v1, v4);
        gr.addEdge(v2, v3);
        gr.addEdge(v2, v4);
    }
}
