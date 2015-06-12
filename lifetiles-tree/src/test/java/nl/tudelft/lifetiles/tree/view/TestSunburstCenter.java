/**
 * 
 */
package nl.tudelft.lifetiles.tree.view;

import static org.junit.Assert.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;

import org.junit.Test;

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

}
