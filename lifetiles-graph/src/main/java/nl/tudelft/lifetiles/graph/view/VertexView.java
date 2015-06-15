package nl.tudelft.lifetiles.graph.view;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import nl.tudelft.lifetiles.annotation.model.GeneAnnotation;
import nl.tudelft.lifetiles.core.util.ColorUtils;

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
    private final Rectangle clip;

    /**
     * this is the region coloring the text.
     */
    private final Rectangle rectangle;

    /**
     * Horizontal and vertical spacing between rectangles.
     */
    public static final double SPACING = 2;

    /**
     * Horizontal scale for each coordinate.
     */
    public static final double HORIZONTALSCALE = 11;

    /**
     * Vertical scale for each coordinate.
     */
    public static double VERTICALSCALE = 0;

    /**
     * The minimal size of the text before it is drawn.
     */
    private static final double MINTEXTSIZE = 10;

    /**
     * Name of the font used in the Vertex View.
     */
    private static final String FONTNAME = "Oxygen Mono";

    /**
     * Color of the stroke of the annotation on the vertex.
     */
    private static final Paint STROKE_COLOR = Color.PURPLE;

    /**
     * Width of the stroke of the annotation on the vertex.
     */
    private static final double STROKE_WIDTH = 5;

    /**
     * this is the DNA strain the display on the vertex.
     */
    private final Text text;

    /**
     * Creates a new Block to be displayed on the screen. The width is already
     * computed by the length of the string after applying css styling. The
     * following data can be set:
     *
     * @param string
     *            Base-pair sequence
     * @param topLeftPoint
     *            top-left (x,y) coordinate
     * @param width
     *            the width of the vertex
     * @param height
     *            the height of the vertex
     * @param scale
     *            the resize factor of the vertex
     * @param color
     *            the color of the vertex
     */
    public VertexView(final String string, final Point2D topLeftPoint,
            final double width, final double height, final double scale,
            final Color color) {

        clip = new Rectangle(width * HORIZONTALSCALE * scale - SPACING, 0);

        text = new Text(string);
        text.setFont(Font.font("Oxygen Mono", HORIZONTALSCALE));
        text.getStyleClass().add("vertexText");
        text.setClip(clip);

        rectangle = new Rectangle(width * HORIZONTALSCALE * scale - SPACING, 0);

        rectangle.setStyle("-fx-fill:" + ColorUtils.webCode(color));
        rectangle.getStyleClass().add("vertexText");

        setLayoutX(topLeftPoint.getX() * HORIZONTALSCALE * scale);
        setLayoutY(topLeftPoint.getY() * VERTICALSCALE);

        getChildren().addAll(rectangle, text);

    }

    /**
     * Get the current height of the vertex.
     *
     * @return width
     */
    public double getHeight() {
        return rectangle.getLayoutBounds().getHeight();
    }

    /**
     * Get the current width of the vertex.
     *
     * @return width
     */
    public double getWidth() {
        return rectangle.getLayoutBounds().getWidth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final void layoutChildren() {
        double width = rectangle.getWidth();
        double height = rectangle.getHeight();

        double fontWidth = text.getLayoutBounds().getWidth();
        double fontHeight = text.getLayoutBounds().getHeight();

        text.setFont(Font.font(FONTNAME, (HORIZONTALSCALE * width) / fontWidth));
        text.setLayoutX(width / 2 - text.getLayoutBounds().getWidth() / 2);
        text.setLayoutY(height / 2);

        // Don't draw text if either the Font size is too small or the text is
        // partially drawn out of the rectangle.
        text.setVisible(text.getFont().getSize() >= MINTEXTSIZE
                && fontHeight <= height);

        clip.setWidth(width);
        clip.setHeight(height);
        clip.setLayoutX(0);
        clip.setLayoutY(-height / 2);
    }

    /**
     * Change the Colour of the Vertex.
     *
     * @param color
     *            the new color
     */
    public void setColor(final Color color) {
        this.rectangle.setFill(color);
    }

    /**
     * Resize the width of the Vertex.
     *
     * @param height
     *            new width of the vertex
     */
    public void setHeight(final double height) {
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
    public void setWidth(final double width) {
        rectangle.setWidth(width);
        clip.setWidth(width);
        layoutChildren();
    }

    /**
     * Annotates the vertex view with a gene annotations.
     *
     * @param geneAnnotation
     *            The annotation to annotate the vertex with.
     */
    public void annotate(final GeneAnnotation geneAnnotation) {
        rectangle.setStroke(STROKE_COLOR);
        rectangle.setStrokeWidth(STROKE_WIDTH);

        Tooltip tooltip = new Tooltip(geneAnnotation.toString());
        Tooltip.install(this, tooltip);
    }

}
