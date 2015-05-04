package nl.tudelft.lifetiles.tilegraph;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Vertex extends Group {
  private Text text;
  private Rectangle rectangle;
  private Rectangle clip;
  private double resize = 0;
  
/** Creates a new Block to be displayed on the screen. 
 * The width is already computed by the length of the string
 * after applying css styling. 
 * The following data can be set:
 * 
 * @param string - Base-pair sequence
 * @param initX - top-left x coordinate
 * @param initY - top-left y coordinate
 * @param height - the height of the vertex
 * @param color - the color of the vertex
 */
  public Vertex(String string, double initX, double initY, double height,Color color) {
    this.text = new Text(string);
    text.setTextOrigin(VPos.CENTER);

    // Is filled out later when the css is applied to it
    double width = 0;

    this.rectangle = new Rectangle(width, height);
    rectangle.setFill(color);

    this.clip = new Rectangle(width, height);
    text.setClip(clip);

    this.setLayoutX(initX);
    this.setLayoutY(initY);

    this.getChildren().addAll(rectangle, text);

    // Changes the width when the css is applied
    this.boundsInParentProperty().addListener(
        (obj, oldValue, newValue) -> {
        this.layoutChildren();
      });

    // Setup Id for css styling
    rectangle.setId("vertex");
    text.setId("vertexText");

  }

/** Resize the width of the Vertex.
 * 
 * @param width - new width of the vertex
 */
  public void setWidth(double width) {
    rectangle.setWidth(width);
    clip.setWidth(width);
    // redraw
    resize = width;
    layoutChildren();

  }

  @Override
    protected void layoutChildren() {
    final double w = rectangle.getWidth();
    final double h = rectangle.getHeight();
    clip.setWidth(w);
    clip.setHeight(h);
    clip.setLayoutX(0);
    clip.setLayoutY(-h / 2);

    if (resize == 0) {
      rectangle.setWidth(text.getLayoutBounds().getWidth());
    } else {
      rectangle.setWidth(resize);
    }

    text.setLayoutX(w / 2 - text.getLayoutBounds().getWidth() / 2);
    text.setLayoutY(h / 2);
  }
}