/**
 * 
 */
package nl.tudelft.lifetiles.tree.view;

import static org.junit.Assert.*;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import nl.tudelft.lifetiles.sequence.model.Sequence;
import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Albert Smit
 *
 */
public class TestSunburstRingSegment {

    private static final double DELTA = 1e-15;  

    @Before
    public void setUp() {
        new JFXPanel(); // force to initialize Toolkit
    }
    @Test
    public void testDefaultColor() {
        DegreeRange degreeRange = Mockito.mock(DegreeRange.class);
        Point2D center = Mockito.mock(Point2D.class);
        
        SunburstRingSegment test = new SunburstRingSegment(new PhylogeneticTreeItem(),
                0, degreeRange, center, 1d);
        Shape testColor = (Shape)test.getChildren().get(0);
        assertNotNull("color was null", testColor.getFill());
        assertTrue("color was not a Color", testColor.getFill() instanceof Color);
    }
    
    @Test
    public void testSequenceColor() {
        PhylogeneticTreeItem node = new PhylogeneticTreeItem();
        Sequence sequence = Mockito.mock(Sequence.class);
        Mockito.when(sequence.getIdentifier()).thenReturn("A");
        node.setSequence(sequence);
        
        DegreeRange degreeRange = Mockito.mock(DegreeRange.class);
        Point2D center = Mockito.mock(Point2D.class);
        
        SunburstRingSegment test = new SunburstRingSegment(new PhylogeneticTreeItem(),
                0, degreeRange, center, 1d);
        Shape testColor = (Shape)test.getChildren().get(0);
        assertNotNull("color was null", testColor.getFill());
        assertTrue("color was not a Color", testColor.getFill() instanceof Color);
    }

}
