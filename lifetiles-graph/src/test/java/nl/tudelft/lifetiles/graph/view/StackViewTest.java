package nl.tudelft.lifetiles.graph.view;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class StackViewTest {

    private StackView s;

    @Before
    public void setup() {
        ArrayList<Long> stack = new ArrayList<Long>();
        stack.add((long) 10);
        stack.add((long) 20);
        stack.add((long) 30);
        stack.add((long) 40);
        s = new StackView(stack, 20, (long) 90);
    }
    
    @Test 
    public void childrenTest() {
        assertEquals(4, s.getChildren().size());
    }

    @Test
    public void xLayoutTest() {
        assertEquals(0, s.getLayoutX(), 1e-10);
    }

    @Test
    public void yLayoutTest() {
        assertEquals(0, s.getLayoutY(), 1e-10);
    }
    
}
