package nl.tudelft.lifetiles.tilegraph;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import nl.tudelft.lifetiles.graph.Graph;
import nl.tudelft.lifetiles.graph.SequenceSegment;

/**
 * The TileView is responsible for displaying the graph given from
 * the TileController.
 *
 */
public class TileView {
    /**
     * The root contains all the to be displayed
     * elements.
     */
    private Group root;
    /**
     * The nodes contains all Vertices to be displayed.
     */
    private Group nodes;
    /**
     * The edges contains all EdgeLines to be displayed.
     */
    private Group edges;

    /**
     * Spacing between the height of the blocks.
     */
    private final double spacingY = 1;

    /**
     * The scaling defines the eventually height of the vertex.
     */
    private final double scale = 20;

    /**
     * The Controller that controls this view.
     */
    private TileController controller;

    /**
     * Add the controller to this view.
     *
     * @param tvc
     *            - the controller
     */
    public final void addController(final TileController tvc) {
        controller = tvc;
    }

    public TileView() {
        root = new Group();
        nodes = new Group();
        edges = new Group();
    }

    /**
     * Draw the given graph.
     *
     * @param gr
     *            - Graph to be drawn
     * @return - the elements that must be displayed on the screen
     */
    public final Group drawGraph(final Graph<SequenceSegment> gr) {

        List<Long> lanes = new LinkedList<Long>();

        Iterator<SequenceSegment> it = sortStartVar(gr);

        while (it.hasNext()) {
            SequenceSegment segment = it.next();
            checkAvailable(segment, lanes);
        }

        root.getChildren().addAll(edges, nodes);

        return root;

    }

    /**
     * This will sort the nodes based on the
     * starting position.
     *
     * @param gr
     *            - the graph that contains the to be sorted nodes
     * @return - Iterator of the sorted list
     */
    private Iterator<SequenceSegment> sortStartVar(
            final Graph<SequenceSegment> gr) {

        return gr.getAllVertices().iterator();

    }

    /**
     * Functions that will find if it can draw a segment
     * at a certain position.
     *
     * @param segment
     *            - segment to be drawn
     * @param lanes
     *            - already drawn segments
     */
    private void checkAvailable(final SequenceSegment segment,
            final List<Long> lanes) {
        for (int i = 0; i <= lanes.size(); i++) {
            if (i >= lanes.size() || lanes.get(i) <= segment.getStart()
                    && segmentFree(i, segment, lanes)) {
                segmentInsert(i, segment, lanes);
                drawVertex(segment.getContent(), segment.getStart(), i, segment
                        .getSources().size(), Color.GRAY);
                break;
            }
        }
    }

    /**
     * Check if there is a free spot to draw the segment at this location.
     *
     * @param i
     *            - location in the linked list of already drawn segments
     * @param segment
     *            - segment to be drawn
     * @param lanes
     *            - already drawn segments
     * @return - Boolean indicating if there is a free spot
     */
    private Boolean segmentFree(final int i, final SequenceSegment segment,
            final List<Long> lanes) {
        for (int w = 0; w < segment.getSources().size(); w++) {

            if ((i + w > lanes.size())
                    && (lanes.get(i + w) <= segment.getStart())) {
                return false;
            }

        }

        return true;
    }

    /**
     * Insert a segment in the linked list.
     *
     * @param i
     *            - location in the linked list of already drawn segments
     * @param segment
     *            - segment to be inserted
     * @param lanes
     *            - linked list
     */
    private void segmentInsert(final int i, final SequenceSegment segment,
            final List<Long> lanes) {
        for (int w = 0; w < segment.getSources().size(); w++) {
            if (i + w > lanes.size()) {
                lanes.set(i + w, segment.getEnd());
            } else {
                lanes.add(i + w, segment.getEnd());
            }
        }

    }

    /**
     * Create a Vertex that can be displayed on the screen.
     *
     * @param text
     *            - text of the dna segment
     * @param x
     *            - top left x coordinate
     * @param y
     *            - top left y coordinate
     * @param height
     *            - the height of the vertex
     * @param color
     *            - the colour of the vertex
     */
    private void drawVertex(final String text, final double x, final double y,
            final double height, final Color color) {

        Vertex v = new Vertex(text, x, scale * y, scale * height - spacingY,
                color);

        nodes.getChildren().add(v);

        v.setOnMouseClicked(t -> controller.changeColour(Color.RED, v));
        v.setOnMouseEntered(t -> controller.changeColour(Color.GREEN, v));
        v.setOnMouseExited(t -> controller.changeColour(Color.GRAY, v));
    }

    /**
     * Create an Edge from a Vertex to another.
     *
     * @param from
     *            - Start Vertex
     * @param to
     *            - Destination Vertex
     */
    private void drawEdge(Vertex from, Vertex to) {
        EdgeLine edge = new EdgeLine(from, to);

        edges.getChildren().add(edge);
    }

    /**
     * Change Vertex colour.
     *
     * @param v
     *            - vertex to be changed.
     * @param color
     *            - the new colour
     */
    public final void changeVertexColour(final Vertex v, final Color color) {
        v.setColour(color);
    }

}
