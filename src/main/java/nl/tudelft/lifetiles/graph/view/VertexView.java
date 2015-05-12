package nl.tudelft.lifetiles.graph.view;

import javafx.geometry.VPos;
import javafx.scene.Group;
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
     * this is the DNA strain the display on the vertex.
     */
    private Text text;
    /**
     * this is the region coloring the text.
     */
    private Rectangle rectangle;
    /**
     * this will hold text in the right place.
     */
    private Rectangle clip;
    /**
     * this value is to make sure that when this vertex has to be resized, it
     * will be the new resized width.
     */
    private double resizeWidth = -1;

    /**
     * The fontsize of the string.
     */
    private static double fontSize = 16;

    /**
     * Horizontal and vertical spacing between rectangles.
     */
    private static double spacing = 3;

    /**
     * Horizontal and vertical scale for each coordinate.
     */
    private static double scale = 25;

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
     * @param width
     *            the width of the vertex
     * @param height
     *            the height of the vertex
     * @param color
     *            the color of the vertex
     */
    public VertexView(final String string, final double initX,
            final double initY, final double width, final double height,
            final Color color) {

        this.text = new Text(string);
        text.setTextOrigin(VPos.CENTER);
        text.setFill(Color.WHITE);
        text.setFontSmoothingType(FontSmoothingType.LCD);
        text.setFont(Font.font("Open Sans", fontSize));

        this.rectangle = new Rectangle(width * scale, height * scale);
        rectangle.setFill(color);

        this.clip = new Rectangle(width * scale, height * scale);
        text.setClip(clip);

        this.setLayoutX(initX * scale);
        this.setLayoutY(initY * scale);

        this.setHeight(height * scale - spacing);
        this.setWidth(width * scale - spacing);

        this.getChildren().addAll(rectangle, text);

    }

    /**
     * Get the current width of the vertex.
     *
     * @return width
     */
    public final double getWidth() {
        return rectangle.getLayoutBounds().getWidth();
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
        resizeWidth = width;
        layoutChildren();
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
     * Resize the width of the Vertex.
     *
     * @param height
     *            new width of the vertex
     */
    public final void setHeight(final double height) {
        rectangle.setHeight(height);
        clip.setHeight(height);
        layoutChildren();
    }

    /**
     * Change the Colour of the Vertex.
     *
     * @param color
     *            the new color
     */
    public final void setColour(final Color color) {
        this.rectangle.setFill(color);
    }

    @Override
    protected final void layoutChildren() {
        final double width = rectangle.getWidth();
        final double height = rectangle.getHeight();
        clip.setWidth(width);
        clip.setHeight(height);
        clip.setLayoutX(0);
        clip.setLayoutY(-height / 2);

        if (resizeWidth == -1) {
            rectangle.setWidth(text.getLayoutBounds().getWidth());
        } else {
            rectangle.setWidth(resizeWidth);
        }

        text.setLayoutX(width / 2 - text.getLayoutBounds().getWidth() / 2);
        text.setLayoutY(height / 2);
    }
}
