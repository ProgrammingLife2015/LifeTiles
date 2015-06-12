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
public class TestAbstractSunburstNode {

    private static final double DELTA = 1e-15;
    @Test
    public void testCalculateAngleSmall() {
        double test = AbstractSunburstNode.calculateAngle(10, 100);
        double test2 = AbstractSunburstNode.calculateAngle(350, 10);

        assertEquals("Angle between to points was not correct",90,test,DELTA);
        assertEquals("Angle between to points was not correct",20,test2,DELTA);
    }
    
    @Test
    public void testCalculateAngleLarge() {
        double test = AbstractSunburstNode.calculateAngle(100, 10);
        double test2 = AbstractSunburstNode.calculateAngle(10, 350);

        assertEquals("Angle between to points was not correct",270,test,DELTA);
        assertEquals("Angle between to points was not correct",340,test2,DELTA);
    }
    
    @Test
    public void testCalculateAngleZero() {
        double test = AbstractSunburstNode.calculateAngle(10, 10);

        assertEquals("Angle between to points was not correct",0,test,DELTA);
    }

}
