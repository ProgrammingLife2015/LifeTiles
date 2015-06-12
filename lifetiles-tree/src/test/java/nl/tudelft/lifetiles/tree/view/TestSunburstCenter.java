/**
 * 
 */
package nl.tudelft.lifetiles.tree.view;

import static org.junit.Assert.*;
import javafx.scene.paint.Color;
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

}
