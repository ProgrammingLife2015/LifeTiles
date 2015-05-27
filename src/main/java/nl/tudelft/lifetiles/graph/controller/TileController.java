package nl.tudelft.lifetiles.graph.controller;

import javafx.scene.Group;
import nl.tudelft.lifetiles.graph.model.GraphContainer;
import nl.tudelft.lifetiles.bucket.model.BucketCache;
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
     * currentPosition of the view in the scrollPane, should only redraw if
     * position has changed.
     */
    private int currentPosition = -1;

    /**
     * cached group of the currently drawn location.
     */
    private Group currentGroup = new Group();

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
     *            Position in the scrollPane.
     * @return Group object to be drawn on the screen
     */
    public final Group drawGraph(final double position) {
        BucketCache bucketCache = modelVar.getBucketCache();
        int nextPosition = bucketCache.bucketPosition(position);
        if (currentPosition != nextPosition) {
            currentGroup = viewVar
                    .drawGraph(modelVar.getSegments(nextPosition));
            currentPosition = nextPosition;
        }
        return currentGroup;
    }

}
