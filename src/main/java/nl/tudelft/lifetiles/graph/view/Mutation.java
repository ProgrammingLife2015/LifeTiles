package nl.tudelft.lifetiles.graph.view;

import javafx.scene.paint.Color;

/**
 * Interface for different type of Mutation annotations.
 *
 * @author Jos
 *
 */
public enum Mutation {

    INSERTION(Color.web("8df08c")), DELETION(Color.web("f35959")), POLYMORPHISM(
            Color.web("4091ff"));

    /**
     * Color value of the mutation.
     */
    private Color color;

    /**
     * Init a mutation type with a corresponding color.
     * 
     * @param color
     *            Corresponding color.
     */
    Mutation(Color color) {
        this.color = color;
    }

    /**
     * Returns the color of the mutation type.
     * 
     * @return color of the mutation type.
     */
    public Color getColor() {
        return color;
    }

}
