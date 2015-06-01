package nl.tudelft.lifetiles.graph.view;

import static org.junit.Assert.assertEquals;
import javafx.scene.shape.Rectangle;

import org.junit.Test;

public class EdgeLineTest {

    @Test
    public void testStraightInsFrom_LineSameY() {
        Rectangle from = new Rectangle(0, 0, 2, 2);
        Rectangle to = new Rectangle(4, 0, 2, 2);

        EdgeLine line = new EdgeLine(from, to);
        assertEquals(line.getLine().getStartY(), line.getLine().getEndY(), 0);
        // Should be half of (start y + end y) of 'to'
        assertEquals(line.getLine().getStartY(), (double) 0 + (0 + 2) / 2,
                1e-10);

    }

    @Test
    public void testStraightLineInsFrom_DiffY() {
        Rectangle from = new Rectangle(0, 0, 2, 2);
        Rectangle to = new Rectangle(4, 1, 2, 1);

        EdgeLine line = new EdgeLine(from, to);

        assertEquals(line.getLine().getStartY(), line.getLine().getEndY(), 0);
        // Should be half of (start y + end y) of 'to'
        assertEquals(line.getLine().getStartY(), (double) (1 + (1 + 1)) / 2,
                1e-10);

    }

    @Test
    public void testStraightLineDiffYLow() {
        Rectangle from = new Rectangle(0, 0, 2, 2);
        Rectangle to = new Rectangle(4, 1, 2, 3);

        EdgeLine line = new EdgeLine(from, to);

        assertEquals(line.getLine().getStartY(), line.getLine().getEndY(), 0);
        assertEquals(line.getLine().getStartY(), 1.0, 1e-10);

    }

    @Test
    public void testStraightLineDiffYUpper() {
        Rectangle from = new Rectangle(0, 1, 2, 3);
        Rectangle to = new Rectangle(4, 0, 2, 2);

        EdgeLine line = new EdgeLine(from, to);
        assertEquals(line.getLine().getStartY(), line.getLine().getEndY(), 0);
        assertEquals(line.getLine().getStartY(), 2.5, 1e-10);

    }

    @Test
    public void testStraightLineFromSmaller() {
        Rectangle from = new Rectangle(0, 1, 2, 1);
        Rectangle to = new Rectangle(4, 0, 2, 4);

        EdgeLine line = new EdgeLine(from, to);
        assertEquals(line.getLine().getStartY(), line.getLine().getEndY(), 0);
        assertEquals(line.getLine().getStartY(), 1.5, 1e-10);

    }

    @Test
    public void testStraightLineEqualSize() {
        Rectangle from = new Rectangle(0, 0, 2, 2);
        Rectangle to = new Rectangle(4, 0, 2, 2);

        EdgeLine line = new EdgeLine(from, to);
        assertEquals(line.getLine().getStartY(), line.getLine().getEndY(), 0);
        // Should be half of (start y + end y) of 'to'
        assertEquals(line.getLine().getStartY(), (double) (0 + (0 + 2)) / 2,
                1e-10);

    }
}
