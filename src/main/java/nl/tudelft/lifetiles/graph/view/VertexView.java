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
     * this will hold text in the right place.
     */
    private Rectangle clip;

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
     * Horizontal and vertical spacing between rectangles.
     */
    private static final double SPACING = 2;

    /**
     * Horizontal scale for each coordinate.
     */
    private static final double HORIZONTALSCALE = 11;

    /**
     * Vertical scale for each coordinate.
     */
    private static final double VERTICALSCALE = 40;

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
        text = new Text(string);
        text.setTextOrigin(VPos.CENTER);
        text.setFill(Color.WHITE);
        text.setFontSmoothingType(FontSmoothingType.LCD);
        text.setFont(Font.font("Oxygen Mono", HORIZONTALSCALE));

        rectangle = new Rectangle(width * HORIZONTALSCALE, height
                * VERTICALSCALE);
        rectangle.setFill(color);
        clip = new Rectangle(width * HORIZONTALSCALE, height * VERTICALSCALE);
        text.setClip(clip);

        setLayoutX(initX * HORIZONTALSCALE);
        setLayoutY(initY * VERTICALSCALE);

        setHeight(height * VERTICALSCALE - SPACING);
        setWidth(width * HORIZONTALSCALE - SPACING);

        getChildren().addAll(rectangle, text);

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
     * Get the current width of the vertex.
     *
     * @return width
     */
    public final double getWidth() {
        return rectangle.getLayoutBounds().getWidth();
    }

    @Override
    protected final void layoutChildren() {
        double width = rectangle.getWidth();
        double height = rectangle.getHeight();

        double fontWidth = text.getLayoutBounds().getWidth();
        text.setFont(Font.font("Oxygen Mono", (HORIZONTALSCALE) * width
                / fontWidth));

        clip.setWidth(width);
        clip.setHeight(height);
        clip.setLayoutX(0);
        clip.setLayoutY(-height / 2);

        text.setLayoutX(width / 2 - text.getLayoutBounds().getWidth() / 2);
        text.setLayoutY(height / 2);
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
        resizeWidth = width;
        layoutChildren();
    }
}
