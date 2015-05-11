package nl.tudelft.lifetiles.graph.controller;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.lifetiles.graph.models.Tile;
import nl.tudelft.lifetiles.graph.view.TileView;
import nl.tudelft.lifetiles.graph.view.VertexView;
import javafx.scene.Group;
import javafx.scene.paint.Color;

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
     *            - the view that draws everything
     * @param model
     *            - the model that holds the data
     */
    public TileController(final TileView view, final Tile model) {
        this.viewVar = view;
        this.modelVar = model;
    }

    /**
     * Change the Colour of a Vertex.
     *
     * @param color
     *            - new color
     * @param v
     *            - to be changed vertex
     */
    public final void changeColour(final Color color, final VertexView v) {
        List<Object> args = new ArrayList<Object>();
        args.add(v);
        args.add(color);
        setChangedProperty("COLOR_CHANGED", args);
    }

    /**
     * Creates a drawable object of the graph from the model.
     *
     * @return Group object to be drawn on the screen
     */
	public final Group drawGraph() {
        return viewVar.drawGraph(modelVar.getGraph());
    }

    /**
     * set a certain changed property with the arguments.
     *
     * @param propertyName
     *            - Name of the property
     * @param args
     *            - Arguments for the function that needs to be called.
     */
    public final void setChangedProperty(final String propertyName,
            final List<Object> args) {

        if (propertyName.equals("COLOR_CHANGED")) {
            viewVar.changeVertexColour((VertexView) args.get(0),
                    (Color) args.get(1));
        }
    }

}
