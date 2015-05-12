package nl.tudelft.lifetiles.graph.models.sequence.mutation;

import javafx.scene.paint.Color;

/**
 * Insertion Mutation type of Mutation annotations.
 * Sequence with content on absolute range where
 * reference sequence has no content.
 * 
 * @author Jos
 *
 */
public class InsertionMutation implements Mutation {

    /**
     * Color value of this mutation in the view.
     * Currently here because of javafx css bug.
     */
    private static final Color color = Color.web("8df08c");

    /**
     * @return Tile color of the mutation type.
     */
    @Override
    public final Color getColor() {
        return color;
    }

}