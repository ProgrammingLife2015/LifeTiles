package nl.tudelft.lifetiles.graph.controller;

import javafx.scene.Group;
import nl.tudelft.lifetiles.graph.model.GraphContainer;
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
    private final GraphContainer modelVar;

    /**
     * The view.
     */
    private final TileView viewVar;

    /**
     * Creates a TileController which controls the dataflow.
     *
     * @param view
     *            the view that draws everything
     * @param model
     *            the model that holds the data
     */
    public TileController(final TileView view, final GraphContainer model) {
        this.viewVar = view;
        this.modelVar = model;
    }

    /**
     * Creates a drawable object of the graph from the model.
     *
     * @param position
     *            Bucket position of the scrollPane.
     * @return Group object to be drawn on the screen
     */
    public final Group drawGraph(final int position) {
        return viewVar.drawGraph(modelVar.getSegments(position));
    }

    /**
     * Return the position in the bucket.
     *
     * @param position
     *            Position in the scrollPane.
     * @return position in the bucket.
     */
    public final int getBucketPosition(final double position) {
        return modelVar.getBucketCache().bucketPosition(position);
    }

}
