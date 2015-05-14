package nl.tudelft.lifetiles.graph.view;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import nl.tudelft.lifetiles.graph.models.Graph;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;

/**
 * The TileView is responsible for displaying the graph given from
 * the TileController.
 *
 */
public class TileView {
    /**
     * Default color of a tile element.
     */
    private static Color defaultColor = Color.web("a1d3ff");;
    /**
     * The edges contains all EdgeLines to be displayed.
     */
    private Group edges;
    /**
     * The nodes contains all Vertices to be displayed.
     */
    private Group nodes;

    /**
     * The root contains all the to be displayed
     * elements.
     */
    private Group root;

    /**
     * The lanes list which contains the occupation of the lanes inside the
     * tileview.
     */
    private List<Long> lanes;

    /**
     * Create the TileView by initializing the groups where the to be drawn
     * vertices and edges are stored.
     */
    public TileView() {
        root = new Group();
        nodes = new Group();
        edges = new Group();
        lanes = new LinkedList<Long>();
    }

    /**
     * Change Vertex colour.
     *
     * @param v
     *            vertex to be changed.
     * @param color
     *            the new colour
     */
    public final void changeVertexColor(final VertexView v, final Color color) {
        v.setColor(color);
    }

    /**
     * Draw the given graph.
     *
     * @param gr
     *            Graph to be drawn
     * @return the elements that must be displayed on the screen
     */
    public final Group drawGraph(final Graph<SequenceSegment> gr) {
        PriorityQueue<SequenceSegment> it = sortStartVar(gr);
        while (!it.isEmpty()) {
            SequenceSegment segment = it.poll();
            drawVertexLane(segment);
        }
        root.getChildren().addAll(edges, nodes);
        return root;
    }

    /**
     * Draws a given segment to an available position in the graph.
     *
     * @param segment
     *            segment to be drawn
     */
    private void drawVertexLane(final SequenceSegment segment) {
        String text = segment.getContent().toString();
        long start = segment.getStart();
        long width = segment.getContent().getLength();
        long height = segment.getSources().size();
        Color color = sequenceColor(segment.getMutation());
        
        for (int index = 0; index < lanes.size(); index++) {
            if (lanes.get(index) <= segment.getStart()
                    && segmentFree(index, segment)) {
                drawVertex(text, start, index, width, height, color);
                segmentInsert(index, segment);
                return;
            }
        }
        drawVertex(text, start, lanes.size(), width, height, color);
        segmentInsert(lanes.size(), segment);
    }

    /**
     * Returns the mutation color of a given mutation. Default if no mutation.
     *
     * @param mutation
     *            mutation to return color from.
     * @return color of the mutation
     */
    private Color sequenceColor(final Mutation mutation) {
        if (mutation == null) {
            return defaultColor;
        } else {
            return mutation.getColor();
        }
    }

    /**
     * Create a Vertex that can be displayed on the screen.
     *
     * @param text
     *            text of the dna segment
     * @param x
     *            top left x coordinate
     * @param y
     *            top left y coordinate
     * @param width
     *            the width of the vertex
     * @param height
     *            the height of the vertex
     * @param color
     *            the colour of the vertex
     */
    private void drawVertex(final String text, final double x, final double y,
            final double width, final double height, final Color color) {
        VertexView v = new VertexView(text, x, y, width, height, color);
        nodes.getChildren().add(v);
    }

    /**
     * Check if there is a free spot to draw the segment at this location.
     *
     * @param ind
     *            location in the linked list of already drawn segments
     * @param segment
     *            segment to be drawn
     * @return Boolean indicating if there is a free spot
     */
    private boolean segmentFree(final int ind, final SequenceSegment segment) {
        for (int height = 0; height < segment.getSources().size(); height++) {
            if ((ind + height < lanes.size())
                    && (lanes.get(ind + height) > segment.getStart())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Insert a segment in the linked list.
     *
     * @param index
     *            location in the linked list of already drawn segments
     * @param segment
     *            segment to be inserted
     */
    private void segmentInsert(final int index, final SequenceSegment segment) {
        for (int height = 0; height < segment.getSources().size(); height++) {
            if (index + height < lanes.size()) {
                lanes.set(index + height, segment.getEnd());
            } else {
                lanes.add(index + height, segment.getEnd());
            }
        }
    }

    /**
     * This will sort the nodes based on the
     * starting position.
     * Beware: temporary code which will be obsolete with #56 Internal
     * sorting of edges on destination starting position
     *
     * @param gr
     *            the graph that contains the to be sorted nodes
     * @return Iterator of the sorted list
     */
    @Deprecated
    private PriorityQueue<SequenceSegment> sortStartVar(
            final Graph<SequenceSegment> gr) {
        PriorityQueue<SequenceSegment> it;
        it = new PriorityQueue<SequenceSegment>();
        for (SequenceSegment segment : gr.getAllVertices()) {
            it.add(segment);
        }
        return it;
    }

}
