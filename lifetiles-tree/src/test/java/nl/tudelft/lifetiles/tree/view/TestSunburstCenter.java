/**
 * 
 */
package nl.tudelft.lifetiles.tree.view;

import static org.junit.Assert.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Albert Smit
 *
 */
public class TestSunburstCenter {

    private static final double DELTA = 1e-15;
    @Test
    public void testDefaultColor() {
        SunburstCenter test = new SunburstCenter(new PhylogeneticTreeItem(), 1d);
        Shape testColor = (Shape)test.getChildren().get(0);
        assertEquals("color was not right", Color.BLUE, testColor.getFill());
    }
    
    @Test
    public void testSequenceColor() {
        PhylogeneticTreeItem node = new PhylogeneticTreeItem();
        Sequence sequence = Mockito.mock(Sequence.class);
        Mockito.when(sequence.getIdentifier()).thenReturn("A");
        node.setSequence(sequence);
        
        SunburstCenter test = new SunburstCenter(node, 1d);
        Shape testColor = (Shape)test.getChildren().get(0);
        assertNotNull("color was null", testColor.getFill());
        assertTrue("color was not a Color", testColor.getFill() instanceof Color);
    }
    
    @Test
    public void testDisplay() {
        SunburstCenter test = new SunburstCenter(new PhylogeneticTreeItem(), 1d);
        Shape testDisplay = (Shape)test.getChildren().get(0);
        assertNotNull("display was null", testDisplay);
        assertTrue("diplay was not a Circle", testDisplay instanceof Circle);
    }
    
    @Test
    public void testDisplayRadius1() {
        SunburstCenter test = new SunburstCenter(new PhylogeneticTreeItem(), 1d);
        Circle testDisplay = (Circle)test.getChildren().get(0);
        
        assertEquals("scaling was not correct", AbstractSunburstNode.CENTER_RADIUS, testDisplay.getRadius(), DELTA);
    }
    @Test
    public void testDisplayRadius2() {
        SunburstCenter test = new SunburstCenter(new PhylogeneticTreeItem(), 2d);
        Circle testDisplay = (Circle)test.getChildren().get(0);
        
        assertEquals("scaling was not correct", 2 * AbstractSunburstNode.CENTER_RADIUS, testDisplay.getRadius(), DELTA);
    }

}
