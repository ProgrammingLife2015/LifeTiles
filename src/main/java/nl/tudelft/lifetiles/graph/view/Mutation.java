package nl.tudelft.lifetiles.graph.view;

import javafx.scene.paint.Color;

/**
 * Enumeration for different type of Mutation annotations.
 *
 * @author Jos
 *
 */
public enum Mutation {

    /**
     * The types of mutations available.
     */
    INSERTION(Color.web("8df08c")), DELETION(Color.web("f35959")), POLYMORPHISM(
            Color.web("4091ff"));

    /**
     * Color value of the mutation.
     */
    private Color colorVar;

    /**
     * Init a mutation type with a corresponding color.
     *
     * @param color
     *            Corresponding color.
     */
    Mutation(final Color color) {
        this.colorVar = color;
    }

    /**
     * Returns the color of the mutation type.
     *
     * @return color of the mutation type.
     */
    public Color getColor() {
        return colorVar;
    }

}
