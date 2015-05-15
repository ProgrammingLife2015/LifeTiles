package nl.tudelft.lifetiles.graph.controller;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import nl.tudelft.lifetiles.core.controller.ViewController;
import nl.tudelft.lifetiles.graph.models.FactoryProducer;
import nl.tudelft.lifetiles.graph.models.Graph;
import nl.tudelft.lifetiles.graph.models.GraphFactory;
import nl.tudelft.lifetiles.graph.models.sequence.SequenceSegment;
import nl.tudelft.lifetiles.graph.view.Tile;
import nl.tudelft.lifetiles.graph.view.TileView;

/**
 * The controller of the graph view.
 *
 * @author Joren Hammudoglu
 *
 */
public class GraphController implements Initializable, Observer {

    /**
     * The wrapper element.
     */
    @FXML
    private ScrollPane wrapper;

    /**
     * The view controller.
     */
    private ViewController controller;

    @Override
    public final void initialize(final URL location,
            final ResourceBundle resources) {
        controller = ViewController.getInstance();
        controller.addObserver(this);

        repaint(emptyGraph());
    }

    /**
     * Fills the graph view and removes the old content.
     *
     * @param graph the graph
     */
    private void repaint(final Graph<SequenceSegment> graph) {
        Tile model = new Tile(graph);
        TileView view = new TileView();
        TileController tc = new TileController(view, model);

        Group root = tc.drawGraph();
        wrapper.setContent(root);
    }

    /**
     * Creates an empty graph.
     *
     * @return the empty graph
     */
    private static Graph<SequenceSegment> emptyGraph() {
        FactoryProducer<SequenceSegment> fp;
        fp = new FactoryProducer<SequenceSegment>();
        GraphFactory<SequenceSegment> gf = fp.getFactory("JGraphT");
        return gf.getGraph();
    }

    @Override
    public final void update(final Observable o, final Object arg) {
        if (controller.isLoaded()) {
            repaint(controller.getGraph());
        }
    }

}
