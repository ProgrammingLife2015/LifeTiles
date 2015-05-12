package nl.tudelft.lifetiles.graph.models.sequence.mutation;

import javafx.scene.paint.Color;

/**
 * Polymorphism Mutation type of Mutation annotations.
 * Sequence with content on absolute range where
 * reference sequence has different content.
 * 
 * @author Jos
 *
 */
public class PolymorphismMutation implements Mutation {

    private static final Color color = Color.web("4091ff");

    /**
     * @return Tile color of the mutation type.
     */
    @Override
    public Color getColor() {
        return color;
    }

}