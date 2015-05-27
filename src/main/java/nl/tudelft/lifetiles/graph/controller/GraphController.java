package nl.tudelft.lifetiles.graph.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.shape.Rectangle;
import nl.tudelft.lifetiles.core.controller.AbstractController;
import nl.tudelft.lifetiles.core.controller.MenuController;
import nl.tudelft.lifetiles.core.util.Message;
import nl.tudelft.lifetiles.graph.model.DefaultGraphParser;
import nl.tudelft.lifetiles.graph.model.FactoryProducer;
import nl.tudelft.lifetiles.graph.model.Graph;
import nl.tudelft.lifetiles.graph.model.GraphContainer;
import nl.tudelft.lifetiles.graph.model.GraphFactory;
import nl.tudelft.lifetiles.graph.model.GraphParser;
import nl.tudelft.lifetiles.graph.view.TileView;
import nl.tudelft.lifetiles.graph.view.VertexView;
import nl.tudelft.lifetiles.notification.controller.NotificationController;
import nl.tudelft.lifetiles.notification.model.NotificationFactory;
import nl.tudelft.lifetiles.sequence.model.SequenceSegment;

/**
 * The controller of the graph view.
 *
 * @author Joren Hammudoglu
 *
 */
public class GraphController extends AbstractController {

    /**
     * The wrapper element.
     */
    @FXML
    private ScrollPane wrapper;

    /**
     * The currently loaded graph.
     */
    private Graph<SequenceSegment> graph;

    /**
     * Graph node used to draw the update graph based on bucket cache technique.
     */
    private Group graphNode;

    /**
     * {@inheritDoc}
     */
    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        NotificationFactory notFact = new NotificationFactory();

        listen(Message.OPENED,
                (controller, args) -> {
                    assert controller instanceof MenuController;
                    assert args[0] instanceof File && args[1] instanceof File;

                    try {
                        loadGraph((File) args[0], (File) args[1]);
                    } catch (IOException exception) {
                        shout(NotificationController.NOTIFY,
                                notFact.getNotification(exception));
                    }

                });
    }

    /**
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

        shout(Message.LOADED, graph, parser.getSequences());
        repaint();
    }

    /**
     * Repaints the view.
     */
    private void repaint() {
        if (graph != null) {
            GraphContainer model = new GraphContainer(graph);
            TileView view = new TileView();
            TileController tileController = new TileController(view, model);

            Group root = new Group();
            Rectangle clip = new Rectangle(getMaxUnifiedEnd(graph)
                    * VertexView.HORIZONTALSCALE, 0);
            graphNode = new Group();
            graphNode.getChildren().add(tileController.drawGraph(0));
            root.getChildren().add(graphNode);
            root.getChildren().add(clip);
            
            repaintPosition(tileController, root, wrapper.hvalueProperty()
                    .doubleValue());
            wrapper.hvalueProperty().addListener(
                    (ChangeListener<? super Number>) (ov, oldVal, newVal) -> {
                        repaintPosition(tileController, root,
                                newVal.doubleValue());
                    });
            wrapper.setContent(root);
        }
    }

    private void repaintPosition(TileController tileController, Group root,
            double position) {
        graphNode = new Group();
        graphNode.getChildren().add(tileController.drawGraph(position));
        root.getChildren().add(graphNode);
        wrapper.setContent(root);
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
}
