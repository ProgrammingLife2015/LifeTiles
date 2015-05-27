package nl.tudelft.lifetiles.graph.controller;

import javafx.scene.Group;
import nl.tudelft.lifetiles.graph.model.Tile;
import nl.tudelft.lifetiles.graph.view.TileView;

/**
 * The TileControllers controls what from the model has to be displayed on the
 * screen by given only that info the view.
 *
 */
public class TileController {
    /**
     * the model.
     */
    private Tile modelVar;
    /**
     * The view.
     */
    private TileView viewVar;

    /**
     * Creates a TileController which controls the dataflow.
     *
     * @param view
     *            the view that draws everything
     * @param model
     *            the model that holds the data
     */
    public TileController(final TileView view, final Tile model) {
        this.viewVar = view;
        this.modelVar = model;
    }

    /**
     * Creates a drawable object of the graph from the model.
     *
     * @return Group object to be drawn on the screen
     */
    public final Group drawGraph() {
        return viewVar.drawGraph(modelVar.getGraph());
    }

}
