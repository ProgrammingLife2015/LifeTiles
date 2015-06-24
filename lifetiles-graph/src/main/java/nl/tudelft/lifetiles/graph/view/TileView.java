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
    private double horizontalScale;

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
    private Map<SequenceSegment, SegmentInfo> segmentInfoMap;

    /**
     * The group that holds the vertices to be drawn.
     */
    private Group nodes;
    /**
     * The vertical scale to be applied to all vertices.
     */
    private double verticalScale;

    /**
     * The amount of pixels that the height and width at least must have.
     */
    private static final int MINIMALSIZE = 10;

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

        segmentInfoMap = new HashMap<SequenceSegment, SegmentInfo>();
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
     * @param horizontalScale
     *            the horizontal scale to resize all elements of the graph
     * @return the elements that must be displayed on the screen
     */
    public Group drawGraph(final Set<SequenceSegment> segments,
            final Graph<SequenceSegment> graph,
            final Map<SequenceSegment, List<KnownMutation>> knownMutations,
            final Map<SequenceSegment, List<GeneAnnotation>> mappedAnnotations,
            final double horizontalScale) {

        Group root = new Group();

        lanes = new ArrayList<Long>();
        this.horizontalScale = horizontalScale;

        nodes = new Group();

        for (SequenceSegment segment : segments) {
            List<KnownMutation> mutations = null;
            List<GeneAnnotation> annotations = null;
            if (knownMutations != null && knownMutations.containsKey(segment)) {
                mutations = knownMutations.get(segment);
            }
            if (mappedAnnotations != null
                    && mappedAnnotations.containsKey(segment)) {
                annotations = mappedAnnotations.get(segment);
            }

            int laneIndex = drawVertexLane(segment);
            segmentInfoMap.put(segment, new SegmentInfo(laneIndex, mutations,
                    annotations));
        }

        verticalScale = screenHeight / lanes.size();

        for (Entry<SequenceSegment, SegmentInfo> entry : segmentInfoMap.entrySet()) {
            SegmentInfo container = entry.getValue();
            drawVertex(container.getLocation(), entry.getKey(), container
                    .getMutations(), container.getAnnotations());

        }

        root.getChildren().addAll(nodes, edges, bookmarks);
        return root;
    }

    /**
     * Draws a given segment to an available position in the graph.
     *
     * @param segment
     *            segment to be drawn
     * @return the index of the lane
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
        Point2D scaling = new Point2D(horizontalScale, verticalScale);

        VertexView vertex = new VertexView(text, topleft, width, height,
                scaling, color);

        vertex.setOnMouseClicked(event -> controller.clicked(segment));

        if (vertex.getHeight() > MINIMALSIZE || vertex.getWidth() > MINIMALSIZE) {
            nodes.getChildren().add(vertex);
        }

        if (knownMutations != null) {
            for (KnownMutation knownMutation : knownMutations) {
                long segmentPosition = knownMutation.getGenomePosition()
                        - segment.getStart();
                // Loop is intended to create these..
                @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
                Bookmark bookmark = new Bookmark(vertex, knownMutation,
                        segmentPosition, horizontalScale);
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

/**
 * This class holds additional information to be drawn on the screen and also
 * the lane index for the vertex.
 */
class SegmentInfo {

    /**
     * Lane index.
     */
    private Integer location;
    /**
     * Mutations of this vertex.
     */
    private List<KnownMutation> mutations;
    /**
     * Annotations of this vertex.
     */
    private List<GeneAnnotation> annotations;

    /**
     * Constructs a new segment information class.
     *
     * @param location
     *            lane index
     * @param mutations
     *            List of mutations
     * @param annotations
     *            List of annotations
     */
    public SegmentInfo(final Integer location,
            final List<KnownMutation> mutations,
            final List<GeneAnnotation> annotations) {
        this.location = location;
        this.mutations = mutations;
        this.annotations = annotations;

    }

    /**
     * @return the lane index
     */
    public Integer getLocation() {
        return location;
    }

    /**
     * @return the mutations
     */
    public List<KnownMutation> getMutations() {
        return mutations;
    }

    /**
     * @return the annotations
     */
    public List<GeneAnnotation> getAnnotations() {
        return annotations;
    }

}
