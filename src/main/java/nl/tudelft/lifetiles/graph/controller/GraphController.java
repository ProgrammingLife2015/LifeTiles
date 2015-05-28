package nl.tudelft.lifetiles.graph.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
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
     * The notification factory.
     */
    private NotificationFactory nf;

    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        this.nf = new NotificationFactory();

        listen(Message.OPENED, (controller, args) -> {
            assert controller instanceof MenuController;
            assert args[0] instanceof File && args[1] instanceof File;

            try {
                loadGraph((File) args[0], (File) args[1]);
            } catch (Exception e) {
                shout(NotificationController.NOTIFY, nf.getNotification(e));
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
        FactoryProducer<SequenceSegment> fp = new FactoryProducer<>();
        GraphFactory<SequenceSegment> gf = fp.getFactory("JGraphT");
        GraphParser gp = new DefaultGraphParser();
        try {
            graph = gp.parseGraph(vertexfile, edgefile, gf);
        } catch (IOException e) {
            throw new IOException("Graph file not found.");
        }

        shout(Message.LOADED, graph, gp.getSequences());
        repaint();
    }

    /**
     * Repaints the view.
     */
    private void repaint() {
        if (graph != null) {
            System.out.println("repainting graph...");
            GraphContainer model = new GraphContainer(graph);
            TileView view = new TileView();
            TileController tileController = new TileController(view, model);

            Group root = tileController.drawGraph();
            wrapper.setContent(root);
        }
    }

}
