package nl.tudelft.lifetiles.graph.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import nl.tudelft.lifetiles.annotation.model.ResistanceAnnotation;
import nl.tudelft.lifetiles.annotation.model.ResistanceAnnotationMapper;
import nl.tudelft.lifetiles.annotation.model.ResistanceAnnotationParser;
import nl.tudelft.lifetiles.core.controller.AbstractController;
import nl.tudelft.lifetiles.core.controller.MenuController;
import nl.tudelft.lifetiles.core.util.Message;
import nl.tudelft.lifetiles.core.util.Timer;
import nl.tudelft.lifetiles.graph.model.DefaultGraphParser;
import nl.tudelft.lifetiles.graph.model.FactoryProducer;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.graph.model.GraphContainer;
import nl.tudelft.lifetiles.graph.model.GraphFactory;
import nl.tudelft.lifetiles.graph.model.GraphParser;
import nl.tudelft.lifetiles.graph.traverser.MutationIndicationTraverser;
import nl.tudelft.lifetiles.graph.traverser.ReferencePositionTraverser;
import nl.tudelft.lifetiles.graph.view.TileView;
import nl.tudelft.lifetiles.graph.view.VertexView;
import nl.tudelft.lifetiles.notification.controller.NotificationController;
import nl.tudelft.lifetiles.notification.model.NotificationFactory;
import nl.tudelft.lifetiles.sequence.controller.SequenceController;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

/**
 * The controller of the graph view.
 *
 * @author Joren Hammudoglu
 * @author AC Langerak
 *
 */
public class GraphController extends AbstractController {

    @FXML
    private BorderPane wrapper;

    /**
     * The scrollPane element.
     */
    @FXML
    private ScrollPane scrollPane;

    /**
     * The model of the graph.
     */
    private GraphContainer model;

    /**
     * The view of the graph.
     */
    private TileView view;

    /**
     * The view controller.
     */
    private Graph<SequenceSegment> graph;

    /**
     * Current end position of a bucket.
     */
    private int currEndPosition = -1;

    /**
     * Current start position of a bucket.
     */
    private int currStartPosition = -1;

    /**
     * boolean to indicate if the controller must repaint the current position.
     */
    private boolean repaintNow;

    /**
     * The current scale to resize the graph.
     */
    private double scale = 1;

    /**
     * The currently inserted annotations.
     */
    private Map<SequenceSegment, List<ResistanceAnnotation>> annotations;

    /**
     * A shout message indicating annotations have been inserted.
     */
    public static final Message ANNOTATIONS = Message.create("annotations");

    /**
     * The current zoom level.
     */
    private int zoomLevel;

    /**
     * The factor that each zoom in step that updates the current scale.
     */
    private static final double ZOOMINFACTOR = 2;

    /**
     * The factor that each zoom out step that updates the current scale.
     */
    private static final double ZOOMOUTFACTOR = 0.5;

    /**
     * Maximal zoomed in level.
     */
    private static final int MAXZOOM = 50;

    /**
     * {@inheritDoc}
     */
    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {

        NotificationFactory notFact = new NotificationFactory();
        repaintNow = false;

        ZoomToolbar toolbar = new ZoomToolbar();

        wrapper.setRight(toolbar.getToolBar());

        scrollPane = new ScrollPane();

        // Temporary until there is a way to start of totally out zoomed
        zoomLevel = 5;

        listen(Message.OPENED, (controller, subject, args) -> {

            assert controller instanceof MenuController;
            if (!subject.equals("graph")) {
                return;
            }
            assert args.length == 2;
            assert args[0] instanceof File && args[1] instanceof File;

            try {
                loadGraph((File) args[0], (File) args[1]);
            } catch (IOException exception) {
                shout(NotificationController.NOTIFY, "", notFact
                        .getNotification(exception));
            }
        });

        listen(Message.FILTERED, (controller, subject, args) -> {
            assert args.length == 1;
            assert (args[0] instanceof Set<?>);

            model.setVisible((Set<Sequence>) args[0]);
            repaintNow = true;
            repaint();
        });

        listen(SequenceController.REFERENCE_SET,
                (controller, subject, args) -> {
                    assert args.length == 1;
                    assert args[0] instanceof Sequence;
                    Sequence sequence = (Sequence) args[0];
                    ReferencePositionTraverser.referenceMapGraph(graph,
                            sequence);
                    MutationIndicationTraverser.indicateGraphMutations(graph,
                            sequence);
                });

        listen(Message.OPENED,
                (controller, subject, args) -> {
                    assert controller instanceof MenuController;
                    if (!subject.equals("annotations")) {
                        return;
                    }
                    assert args[0] instanceof File;

                    if (graph == null) {
                        shout(NotificationController.NOTIFY,
                                "",
                                notFact.getNotification(new IllegalStateException(
                                        "Graph not loaded while attempting to add annotations.")));
                    } else {
                        try {
                            insertAnnotations((File) args[0]);
                        } catch (IOException exception) {
                            shout(NotificationController.NOTIFY, "", notFact
                                    .getNotification(exception));
                        }
                    }
                });

        toolbar.getZoomlevel().addListener((observeVal, oldVal, newVal) -> {

            int diffLevel = oldVal.intValue() - newVal.intValue();
            zoomLevel = Math.abs(newVal.intValue());
            if (diffLevel < 0) {
                zoom(Math.pow(ZOOMOUTFACTOR, diffLevel * -1));

            } else if (diffLevel > 0) {
                zoom(Math.pow(ZOOMINFACTOR, diffLevel));

            }
        });

        listen(ANNOTATIONS, (controller, subject, args) -> {
            assert controller instanceof MenuController;
            assert args[0] instanceof File;

            if (graph == null) {
                shout(NotificationController.NOTIFY, "", notFact
                        .getNotification(new IllegalStateException(
                                "Graph not loaded.")));
            } else {
                try {
                    insertAnnotations((File) args[0]);
                } catch (IOException exception) {
                    shout(NotificationController.NOTIFY, "", notFact
                            .getNotification(exception));
                }
            }
        });
    }

    /**
     *
     * @return the currently loaded graph.
     */
    public final Graph<SequenceSegment> getGraph() {
        if (graph == null) {
            throw new IllegalStateException("Graph not loaded.");
        }
        return graph;
    }

    /**
     * Load a new graph from the specified file.
     *
     *
     * @param vertexfile
     *            The file to get vertices for.
     * @param edgefile
     *            The file to get edges for.
     * @throws IOException
     *             When an IO error occurs while reading one of the files.
     */
    private void loadGraph(final File vertexfile, final File edgefile)
            throws IOException {
        // create the graph
        GraphFactory<SequenceSegment> factory = FactoryProducer.getFactory();
        GraphParser parser = new DefaultGraphParser();
        graph = parser.parseGraph(vertexfile, edgefile, factory);
        annotations = new HashMap<>();

        shout(Message.LOADED, "sequences", parser.getSequences());
        repaint();

    }

    /**
     * Inserts a list of annotations onto the graph from the specified file.
     *
     * @param file
     *            The file to get annotations from.
     * @throws IOException
     *             When an IO error occurs while reading one of the files.
     */
    private void insertAnnotations(final File file) throws IOException {
        Timer timer = Timer.getAndStart();
        Sequence reference = this.graph.getSources().iterator().next()
                .getSources().iterator().next();
        annotations = ResistanceAnnotationMapper.mapAnnotations(graph,
                ResistanceAnnotationParser.parseAnnotations(file), reference);

        timer.stopAndLog("Inserting annotations");
        repaintNow = true;
        repaintPosition(scrollPane.hvalueProperty().doubleValue());
    }

    /**
     * Repaints the view.
     */
    private void repaint() {
        if (graph != null) {
            if (model == null) {
                model = new GraphContainer(graph);
            }
            view = new TileView(this);

            scrollPane.hvalueProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        repaintPosition(newValue.doubleValue());
                    });

            repaintPosition(scrollPane.hvalueProperty().doubleValue());
        }
    }

    /**
     * Find the start and end bucket on the screenm given the position of the
     * scrollbar.
     *
     * @param position
     *            horizontal position of the scrollbar
     * @return an array where the first element is the start bucket and the last
     *         one is the end bucket
     */
    private int[] getStartandEndBucket(final double position) {
        List<Integer> bucketLocations = new ArrayList<Integer>();

        double scaledVertex = scale * VertexView.HORIZONTALSCALE;
        double graphWidth = getMaxUnifiedEnd(graph) * scaledVertex;
        double screenWidth = scrollPane.getViewportBounds().getWidth();
        double scaledScreenWidth = 2d * screenWidth * scale;

        double relativePosition = (position * screenWidth) / 2d + position
                * graphWidth;

        double start = (relativePosition - scaledScreenWidth) / scaledVertex;
        double end = (relativePosition + scaledScreenWidth) / scaledVertex;

        bucketLocations.add(getStartBucketPosition(start));
        bucketLocations.add(getEndBucketPosition(end) + 1);

        int[] buckets = new int[] {
                getStartBucketPosition(start), getEndBucketPosition(end) + 1
        };

        return buckets;
    }

    /**
     * Repaints the view indicated by the bucket in the given position.
     *
     * @param position
     *            Position in the scrollPane.
     */
    private void repaintPosition(final double position) {
        int[] bucketLocations = getStartandEndBucket(position);

        int startBucket = bucketLocations[0];
        int endBucket = bucketLocations[1];

        if (currEndPosition != endBucket && currStartPosition != startBucket
                || repaintNow) {
            Group graphDrawing = new Group();
            graphDrawing.getChildren().add(drawGraph(startBucket, endBucket));
            graphDrawing.getChildren().add(
                    new Rectangle(getMaxUnifiedEnd(graph) * scale
                            * VertexView.HORIZONTALSCALE, 0));

            scrollPane.setContent(graphDrawing);
            wrapper.setCenter(scrollPane);

            currEndPosition = endBucket;
            currStartPosition = startBucket;

            repaintNow = false;
        }
    }

    /**
     * Zoom on the current graph given a zoomFactor.
     *
     * @param zoomFactor
     *            factor bigger than 1 makes the graph bigger
     *            between 0 and 1 makes the graph smaller
     */
    private void zoom(final double zoomFactor) {
        scale *= zoomFactor;
        repaintNow = true;
        repaint();
    }

    /**
     * Get the maximal unified end position based on the sinks of the graph.
     *
     * @param graph
     *            Graph for which the width must be calculated.
     * @return the maximal unified end position.
     */
    private long getMaxUnifiedEnd(final Graph<SequenceSegment> graph) {
        long max = 0;
        for (SequenceSegment vertex : graph.getSinks()) {
            if (max < vertex.getUnifiedEnd()) {
                max = vertex.getUnifiedEnd();
            }
        }
        return max;
    }

    /**
     * Return the start position in the bucket.
     *
     * @param position
     *            Position in the scrollPane.
     * @return position in the bucket.
     */
    private int getStartBucketPosition(final double position) {
        return model.getBucketCache().bucketStartPosition(position);
    }

    /**
     * Return the end position in the bucket.
     *
     * @param position
     *            Position in the scrollPane.
     * @return position in the bucket.
     */
    private int getEndBucketPosition(final double position) {
        return model.getBucketCache().bucketEndPosition(position);
    }

    /**
     * Creates a drawable object of the graph from the model.
     *
     * It will draw from the startBucket all the way to the endBucket.
     *
     * @param startBucket
     *            the first buket
     * @param endBucket
     *            the last bucket
     * @return Group object to be drawn on the screen
     */
    public final Group drawGraph(final int startBucket, final int endBucket) {
        Group test = view.drawGraph(model.getVisibleSegments(startBucket,
                endBucket), graph, annotations, scale);

        return test;
    }

    /**
     * Set that this segment is selected and set those sequences visible.
     *
     * @param segment
     *            The selected segment
     */
    // Removed final for testing, cannot use PowerMockito because of current bug
    // with javafx 8
    public void clicked(final SequenceSegment segment) {
        shout(Message.FILTERED, "", segment.getSources());
    }

    /**
     * Set that this segment is hovered over.
     *
     * @param segment
     *            the hovered element
     * @param hovering
     *            set if mouse is entering this segment or leaving
     */
    // Removed final for testing, cannot use PowerMockito because of current bug
    // with javafx 8
    public void hovered(final SequenceSegment segment, final Boolean hovering) {
        // TODO: Message to say that a segment is hovered over

    }

}
