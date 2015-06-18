package nl.tudelft.lifetiles.graph.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import nl.tudelft.lifetiles.annotation.model.GeneAnnotation;
import nl.tudelft.lifetiles.annotation.model.KnownMutation;
import nl.tudelft.lifetiles.graph.controller.GraphController;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.sequence.Mutation;
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
     * Color of a collapsed segment.
     */
    private static final Color COLLAPSE_COLOR = Color.DARKGRAY;

    /**
     * The edges contains all EdgeLines to be displayed.
     */
    private final Group edges;

    /**
     * The bookmarks group contains all bookmarks and annotations to be
     * displayed.
     */
    private final Group bookmarks;

    /**
     * The lanes list which contains the occupation of the lanes inside the
     * tileview.
     */
    private List<Long> lanes;

    /**
     * The factor to apply on the vertices and bookmarks to resize them.
     */
    private double scale;

    /**
     * Controller for the View.
     */
    private final GraphController controller;

    /**
     * The maximal screen height.
     */
    private final double screenHeight;

    /**
     * Map to hold the calculated lane index of a sequencesegment.
     */
    private Map<SequenceSegment, Container> nodemap;
    /**
     * The group that holds the vertices to be drawn.
     */
    private Group nodes;
    /**
     * The vertical scale to be applied to all vertices.
     */
    private double verticalScale;

    /**
     * Create the TileView by initializing the groups where the to be drawn
     * vertices and edges are stored.
     *
     * @param control
     *            The controller for the TileView
     * @param height
     *            the maximum allowed height in pixels to draw
     */
    public TileView(final GraphController control, final double height) {
        controller = control;

        nodemap = new HashMap<SequenceSegment, Container>();
        edges = new Group();
        bookmarks = new Group();
        screenHeight = height;
    }

    /**
     * Draw the given graph.
     *
     * @param segments
     *            Graph to be drawn
     * @param graph
     *            Graph to base the edges on
     * @param knownMutations
     *            Map from segment to known mutations.
     * @param mappedAnnotations
     *            Map from segment to gene annotations.
     * @param scale
     *            the scale to resize all elements of the graph
     * @return the elements that must be displayed on the screen
     */
    public Group drawGraph(final Set<SequenceSegment> segments,
            final Graph<SequenceSegment> graph,
            final Map<SequenceSegment, List<KnownMutation>> knownMutations,
            final Map<SequenceSegment, List<GeneAnnotation>> mappedAnnotations,
            final double scale) {

        Group root = new Group();

        lanes = new ArrayList<Long>();
        this.scale = scale;

        nodes = new Group();

        for (SequenceSegment segment : segments) {
            List<KnownMutation> mutations = null;
            List<GeneAnnotation> annotations = null;
            if (knownMutations != null && knownMutations.containsKey(segment)) {
                mutations = knownMutations.get(segment);
            }

            int laneIndex = drawVertexLane(segment);
            nodemap.put(segment, new Container(laneIndex, mutations));
        }

        verticalScale = screenHeight / lanes.size();
        for (Entry<SequenceSegment, Container> entry : nodemap.entrySet()) {
            drawVertex(entry.getValue().getLocation(), entry.getKey(), entry
                    .getValue().getMutations(), null);

        }

        root.getChildren().addAll(nodes, edges, bookmarks);
        return root;
    }

    /**
     * Draws a given segment to an available position in the graph.
     *
     * @param segment
     *            segment to be drawn
     */
    private int drawVertexLane(final SequenceSegment segment) {
        for (int index = 0; index < lanes.size(); index++) {
            if (lanes.get(index) <= segment.getUnifiedStart()
                    && segmentFree(index, segment)) {

                segmentInsert(index, segment);
                return index;
            }
        }

        segmentInsert(lanes.size(), segment);

        return lanes.size();
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
     * @param index
     *            top left y coordinate
     * @param segment
     *            the segment to be drawn in the vertex
     * @param knownMutations
     *            the known mutations of the vertex
     * @param annotations
     *            the annotations of the vertex
     */
    private void drawVertex(final double index, final SequenceSegment segment,
            final List<KnownMutation> knownMutations,
            final List<GeneAnnotation> annotations) {
        String text = segment.getContent().toString();
        long start = segment.getUnifiedStart();
        long width = segment.getContent().getLength();
        long height = segment.getSources().size();

        Color color;
        if (segment.getContent().isCollapsed()) {
            color = COLLAPSE_COLOR;
        } else {
            color = sequenceColor(segment.getMutation());
        }

        Point2D topleft = new Point2D(start, index);

        VertexView vertex = new VertexView(text, topleft, width, height, scale,
                verticalScale, color);

        vertex.setOnMouseClicked(event -> controller.clicked(segment));

        // @TODO check for size of vertex, if too small don't draw it. this will
        // reduce to load on javafx

        nodes.getChildren().add(vertex);

        if (knownMutations != null) {
            for (KnownMutation knownMutation : knownMutations) {
                long segmentPosition = knownMutation.getGenomePosition()
                        - segment.getStart();
                // Loop is intended to create these..
                @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
                Bookmark bookmark = new Bookmark(vertex, knownMutation,
                        segmentPosition, scale);
                bookmarks.getChildren().add(bookmark);
            }
        }
        if (annotations != null) {
            vertex.annotate(annotations.get(0));
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

class Container {

    private Integer location;
    private List<KnownMutation> mutations;

    public Container(Integer location, List<KnownMutation> mutations) {
        this.location = location;
        this.mutations = mutations;

    }

    public Integer getLocation() {
        return location;
    }

    public List<KnownMutation> getMutations() {
        return mutations;
    }

}
