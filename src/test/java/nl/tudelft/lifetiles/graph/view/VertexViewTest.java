package nl.tudelft.lifetiles.graph.view;

import static org.junit.Assert.assertEquals;
import javafx.scene.paint.Color;

import org.junit.Before;
import org.junit.Test;

public class VertexViewTest {

    VertexView vertex;

    @Before
    public void setup() {
        vertex = new VertexView("ATCG", 0, 0, 10, 10, Color.RED);
    }

    @Test
    public void getWidthTest() {
        // 10 * HORIZONTALSCALE - SPACING
        assertEquals(10 * 11 - 2, vertex.getWidth(), 1e-10);
    }

    @Test
    public void getHeightTest() {
        // 10 * VERTICALSCALE - SPACING
        assertEquals(10 * 40 - 2, vertex.getHeight(), 1e-10);
    }

    @Test
    public void setWidthTest() {
        vertex.setWidth(5);
        assertEquals(5, vertex.getWidth(), 1e-10);
    }

    @Test
    public void setHeightTest() {
        vertex.setHeight(5);
        assertEquals(5, vertex.getHeight(), 1e-10);
    }

    @Test
    public void xLayoutTest() {
        assertEquals(0, vertex.getLayoutX(), 1e-10);
    }

    @Test
    public void yLayoutTest() {
        assertEquals(0, vertex.getLayoutY(), 1e-10);
    }

}
