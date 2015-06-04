package nl.tudelft.lifetiles.graph.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import nl.tudelft.lifetiles.graph.controller.GraphController;
import nl.tudelft.lifetiles.graph.model.Edge;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.annotation.model.ResistanceAnnotation;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

/**
 * The TileView is responsible for displaying the graph given from
 * the TileController.
 *
 */
public class TileView {
    /**
     * Default color of a tile element.
     */
    private static Color defaultColor = Color.web("a1d3ff");
    /**
     * The edges contains all EdgeLines to be displayed.
     */
    private final Group edges;
    /**
     * The nodes contains all Vertices to be displayed.
     */
    private final Map<SequenceSegment, VertexView> nodemap;

    /**
     * The bookmarks group contains all bookmarks and annotations to be
     * displayed.
     */
    private Group bookmarks;

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
     * Controller for the View.
     */
    private final GraphController controller;

    /**
     * Create the TileView by initializing the groups where the to be drawn
     * vertices and edges are stored.
     *
     * @param control
     *            The controller for the TileView
     */
    public TileView(final GraphController control) {
        controller = control;

        nodemap = new HashMap<SequenceSegment, VertexView>();
        edges = new Group();
        bookmarks = new Group();
    }

    /**
     * Draw the given graph.
     *
     * @param segments
     *            Graph to be drawn
     * @param graph
     *            Graph to base the edges on
     * @param annotations
     *            Map from segment to annotations.
     * @return the elements that must be displayed on the screen
     */
    public final Group drawGraph(final Set<SequenceSegment> segments,
            final Graph<SequenceSegment> graph,
            final Map<SequenceSegment, List<ResistanceAnnotation>> annotations) {
        Group root = new Group();

        lanes = new ArrayList<Long>();

        for (SequenceSegment segment : segments) {
            List<ResistanceAnnotation> segmentAnnotations = null;
            if (annotations != null && annotations.containsKey(segment)) {
                annotations.get(segment);
            }
            drawVertexLane(segment, segmentAnnotations);
        }

        drawEdges(graph);
        Group nodes = new Group();

        for (Entry<SequenceSegment, VertexView> entry : nodemap.entrySet()) {
            nodes.getChildren().add(entry.getValue());
        }

        root.getChildren().addAll(nodes, edges, bookmarks);
        return root;
    }

    /**
     * @param graph
     *            graph to draw the edges from
     */
    private void drawEdges(final Graph<SequenceSegment> graph) {
        for (Edge<SequenceSegment> edge : graph.getAllEdges()) {
            if (nodemap.containsKey(graph.getSource(edge))
                    && nodemap.containsKey(graph.getDestination(edge))) {

                VertexView source = nodemap.get(graph.getSource(edge));
                VertexView destination = nodemap
                        .get(graph.getDestination(edge));
                drawEdge(source, destination);
            }
        }
    }

    /**
     * Draws a given segment to an available position in the graph.
     *
     * @param segment
     *            segment to be drawn
     * @param annotations
     *            List of annotations in this segment
     */
    private void drawVertexLane(final SequenceSegment segment,
            final List<ResistanceAnnotation> annotations) {
        for (int index = 0; index < lanes.size(); index++) {
            if (lanes.get(index) <= segment.getUnifiedStart()
                    && segmentFree(index, segment)) {
                drawVertex(index, segment, annotations);
                segmentInsert(index, segment);
                return;
            }
        }
        drawVertex(lanes.size(), segment, annotations);
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
     * Create an Edge that can be displayed on the screen.
     *
     * @param source
     *            Node to draw from
     * @param destination
     *            Node to draw to
     */
    private void drawEdge(final Node source, final Node destination) {
        EdgeLine edge = new EdgeLine(source, destination);
        edges.getChildren().add(edge);
    }

    /**
     * Create a Vertex that can be displayed on the screen.
     *
     * @param index
     *            top left y coordinate
     * @param segment
     *            the segment to be drawn in the vertex
     * @param annotations
     *            the annotations of the vertex
     */
    private void drawVertex(final double index, final SequenceSegment segment,
            final List<ResistanceAnnotation> annotations) {
        String text = segment.getContent().toString();
        long start = segment.getUnifiedStart();
        long width = segment.getContent().getLength();
        long height = segment.getSources().size();

        Color color = sequenceColor(segment.getMutation());

        VertexView vertex = new VertexView(text, start, index, width, height,
                color);

        nodemap.put(segment, vertex);
        vertex.setOnMouseClicked(event -> controller.clicked(segment));
        // Hovering
        vertex.setOnMouseEntered(event -> controller.hovered(segment, true));
        vertex.setOnMouseExited(event -> controller.hovered(segment, false));

        if (annotations != null) {
            for (ResistanceAnnotation annotation : annotations) {
                long segmentPosition = annotation.getGenomePosition()
                        - segment.getStart();
                Bookmark bookmark = new Bookmark(vertex, annotation,
                        segmentPosition);
                bookmarks.getChildren().add(bookmark);
            }
        }
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
            int position = ind + height;
            if (position < lanes.size()
                    && lanes.get(position) > segment.getUnifiedStart()) {
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
            int position = index + height;
            if (position < lanes.size()) {
                lanes.set(position, segment.getUnifiedEnd());
            } else {
                lanes.add(position, segment.getUnifiedEnd());
            }
        }
    }

}
