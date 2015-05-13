package nl.tudelft.lifetiles.graph.view;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;

/**
 * A Vertex is the equivalent of a node from the graph datastructure but this is
 * the visualisation.
 *
 * @author AC Langerak
 *
 */
public class VertexView extends Group {
    /**
     * this will hold text in the right place.
     */
    private Rectangle clip;
    /**
     * The fontsize of the string.
     */
    private final double fontSize = 12;
    /**
     * This value is the width of the largest single character
     * a string can have. This is used to calculate the width of the vertex.
     */
    private double letterWidth = 0;
    /**
     * this is the region coloring the text.
     */
    private Rectangle rectangle;

    /**
     * this value is to make sure that when this vertex has to be resized, it
     * will be the new resized width.
     */
    private double resizeWidth = -1;

    /**
     * Spacing between rectangles.
     */
    private final double spacingX = 3;

    /**
     * this is the DNA strain the display on the vertex.
     */
    private Text text;

    /**
     * Creates a new Block to be displayed on the screen. The width is already
     * computed by the length of the string after applying css styling. The
     * following data can be set:
     *
     * @param string
     *            Base-pair sequence
     * @param initX
     *            top-left x coordinate
     * @param initY
     *            top-left y coordinate
     * @param height
     *            the height of the vertex
     * @param color
     *            the color of the vertex
     */
    public VertexView(final String string, final double initX, final double initY,
            final double height, final Color color) {
        // TODO: move style related code to css
        this.text = new Text(string);
        text.setTextOrigin(VPos.CENTER);
        text.setFill(Color.WHITE);
        text.setFontSmoothingType(FontSmoothingType.LCD);

        text.setFont(Font.font("Verdana", fontSize));

        // Is filled out later when the css is applied to it
        double width = 0;

        this.rectangle = new Rectangle(width, height);
        rectangle.setFill(color);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(1.0);
        dropShadow.setOffsetY(1.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        rectangle.setEffect(dropShadow);

        rectangle.setArcWidth(5);
        rectangle.setArcHeight(5);

        this.clip = new Rectangle(width, height);
        text.setClip(clip);

        this.setLayoutX(initX * this.getLetterWidth() * 2);
        this.setLayoutY(initY);

        this.setWidth(string.length() * this.getLetterWidth() * 2 - spacingX);

        this.getChildren().addAll(rectangle, text);
    }

    /**
     * Get the current height of the vertex.
     *
     * @return width
     */
    public final double getHeight() {
        return rectangle.getLayoutBounds().getHeight();
    }

    /**
     * Get the width of displaying one letter using the current font
     * and fontSize.
     *
     * @return returns the width of the largest letter.
     */
    public final double getLetterWidth() {
        if (letterWidth == 0) {
            // All possible characters
            String chars = "ATCG";

            Text textmatch = new Text(String.valueOf(chars.charAt(0)));
            textmatch.setFont(text.getFont());
            double largestWidth = textmatch.getLayoutBounds().getWidth();

            for (int i = 1; i < chars.length(); i++) {
                Text textmatch2 = new Text(String.valueOf(chars.charAt(i)));
                textmatch2.setFont(text.getFont());
                double width2 = textmatch2.getLayoutBounds().getWidth();

                if (width2 > largestWidth) {
                    largestWidth = width2;
                }

            }
            letterWidth = largestWidth;
            return largestWidth;

        } else {
            return letterWidth;
        }
    }

    /**
     * Get the current width of the vertex.
     *
     * @return width
     */
    public final double getWidth() {
        return rectangle.getLayoutBounds().getWidth();
    }

    @Override
    protected final void layoutChildren() {
        final double w = rectangle.getWidth();
        final double h = rectangle.getHeight();
        clip.setWidth(w);
        clip.setHeight(h);
        clip.setLayoutX(0);
        clip.setLayoutY(-h / 2);

        if (resizeWidth == -1) {
            rectangle.setWidth(text.getLayoutBounds().getWidth());
        } else {
            rectangle.setWidth(resizeWidth);
        }

        text.setLayoutX(w / 2 - text.getLayoutBounds().getWidth() / 2);
        text.setLayoutY(h / 2);
    }

    /**
     * Change the Colour of the Vertex.
     *
     * @param color
     *            the new color
     */
    public final void setColor(final Color color) {
        this.rectangle.setFill(color);
    }

    /**
     * Resize the width of the Vertex.
     *
     * @param height
     *            new width of the vertex
     */
    public final void setHeight(final double height) {
        rectangle.setHeight(height);
        clip.setHeight(height);
        // redraw
        layoutChildren();
    }

    /**
     * Resize the width of the Vertex.
     *
     * @param width
     *            new width of the vertex
     */
    public final void setWidth(final double width) {
        rectangle.setWidth(width);
        clip.setWidth(width);
        // redraw
        resizeWidth = width;
        layoutChildren();
    }
}
