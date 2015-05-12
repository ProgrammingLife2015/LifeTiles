package nl.tudelft.lifetiles.graph.models.sequence.mutation;

import javafx.scene.paint.Color;

/**
 * Deletion Mutation type of Mutation annotations.
 * Sequence with no content on absolute range where
 * reference sequence has content.
 * 
 * @author Jos
 *
 */
public class DeletionMutation implements Mutation {

    private static final Color color = Color.web("f35959");

    /**
     * @return Tile color of the mutation type.
     */
    @Override
    public Color getColor() {
        return color;
    }

}