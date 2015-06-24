/**
 * 
 */
package nl.tudelft.lifetiles.tree.view;

import static org.junit.Assert.*;
import javafx.application.Platform;
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

    private Shape testColor;

    @Before
    public void setUp() {
        new JFXPanel(); // force to initialize Toolkit
    }
    @Test
    public void testDefaultColor() throws InterruptedException {
        DegreeRange degreeRange = Mockito.mock(DegreeRange.class);
        Point2D center = Mockito.mock(Point2D.class);
        
        //dirty hack to get around javafx threading issues in tests
        Platform.runLater(() -> {
            SunburstRingSegment test = new SunburstRingSegment(new PhylogeneticTreeItem(),
                    0, degreeRange, center, 1d);
            testColor = (Shape)test.getChildren().get(0);
        });
        Thread.sleep(1000);
        
        assertNotNull("color was null", testColor.getFill());
        assertTrue("color was not a Color", testColor.getFill() instanceof Color);
    }
    
    @Test
    public void testSequenceColor() throws InterruptedException {
        PhylogeneticTreeItem node = new PhylogeneticTreeItem();
        Sequence sequence = Mockito.mock(Sequence.class);
        Mockito.when(sequence.getIdentifier()).thenReturn("A");
        node.setSequence(sequence);
        
        DegreeRange degreeRange = Mockito.mock(DegreeRange.class);
        Point2D center = Mockito.mock(Point2D.class);
        
      //dirty hack to get around javafx threading issues in tests
        Platform.runLater(() -> {
            SunburstRingSegment test = new SunburstRingSegment(new PhylogeneticTreeItem(),
                    0, degreeRange, center, 1d);
            testColor = (Shape)test.getChildren().get(0);
        });

        Thread.sleep(1000);
        assertNotNull("color was null", testColor.getFill());
        assertTrue("color was not a Color", testColor.getFill() instanceof Color);
    }

}
