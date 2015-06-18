/**
 * 
 */
package nl.tudelft.lifetiles.tree.view;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Albert Smit
 *
 */
public class TestDegreeRange {


    private static final double DELTA = 1e-15;
    @Test
    public void testCalculateAngleSmall() {
        DegreeRange test = new DegreeRange(10, 100);
        DegreeRange test2 = new DegreeRange(350, 10);

        assertEquals("Angle between to points was not correct", 90, test.angle(), DELTA);
        assertEquals("Angle between to points was not correct", 20, test2.angle(), DELTA);
    }
    
    @Test
    public void testCalculateAngleLarge() {
        DegreeRange test = new DegreeRange(100, 10);
        DegreeRange test2 = new DegreeRange(10, 350);

        assertEquals("Angle between to points was not correct", 270, test.angle(), DELTA);
        assertEquals("Angle between to points was not correct", 340, test2.angle(), DELTA);
    }
    
    @Test
    public void testCalculateAngleZero() {
        DegreeRange test =new DegreeRange(10, 10);

        assertEquals("Angle between to points was not correct",0,test.angle(),DELTA);
    }

}
