package nl.tudelft.lifetiles.tree.model;

import static org.junit.Assert.*;
import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeParser;
import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;

import org.junit.Test;

/**
 * Test class for PhylogeneticTreeItem.
 * tests setParent, equals and hashCode
 */
public class TestPhylogeneticTreeItem {


    /**
     * Test for setParent.
     */
	@Test
	public void testSetParent(){
	    // set up
	    PhylogeneticTreeItem parent = new PhylogeneticTreeItem();
	    parent.setName("Parent");
	    PhylogeneticTreeItem child = new PhylogeneticTreeItem();
	    child.setName("Child");
	    
	    // set the parent
	    child.setParent(parent);
	    
	    // check parent and child are setup correctly
	    
	    assertEquals("parent was not correctly set","Parent",child.getParent().getName());
	    assertTrue("Child was not properly added", parent.getChildren().contains(child));
	}
	
	/**
	 * Test for number descendants
	 */
	@Test
	public void testNumberDescendants() {
	    // create the test tree
	    // (A:0.1,B:0.2,(C:0.3,D:0.4)E:0.5)F;
	    PhylogeneticTreeItem root = new PhylogeneticTreeItem();
        // add 3 child nodes
        PhylogeneticTreeItem test2 = new PhylogeneticTreeItem();
        test2.setParent(root);
        test2.setDistance(0.1);
        PhylogeneticTreeItem current = new PhylogeneticTreeItem();
        current.setDistance(0.2);
        current.setParent(root);
        current = new PhylogeneticTreeItem();
        current.setParent(root);
        current.setDistance(0.5);
        // add 2 child nodes to the third node
        PhylogeneticTreeItem current2 = new PhylogeneticTreeItem();
        current2.setParent(current);
        current2.setDistance(0.4);
        current2 = new PhylogeneticTreeItem();
        current2.setParent(current);
        current2.setDistance(0.3);
        
        assertEquals("numberDescendants did not match for full tree",5,root.numberDescendants());
        assertEquals("numberDescendants did not match for childless tree",0,test2.numberDescendants());
	}
	
	/**
	 * Test for equals.
	 * tests equals between 2 empty nodes
	 */
    @Test
    public void testEqualsNoDataNoChildren() {
        // set up
        PhylogeneticTreeItem node1 = new PhylogeneticTreeItem();
        PhylogeneticTreeItem node2 = new PhylogeneticTreeItem();
        PhylogeneticTreeItem node3 = new PhylogeneticTreeItem();
        node3.setName("AnotherNode");
        
        // test equal nodes
        assertTrue("two empty nodes should be the same",node1.equals(node2));
        assertTrue("two empty nodes should be the same",node2.equals(node1));
        
        // test different nodes
        assertFalse("two different nodes should not match",node1.equals(node3));
        assertFalse("two different nodes should not match",node3.equals(node1));
               
    }
    
    /**
     * Test for equals.
     * tests equals between named nodes
     */
    @Test
    public void testEqualsNameOnlyNoChildren() {
        // set up
        PhylogeneticTreeItem node1 = new PhylogeneticTreeItem();
        node1.setName("DuplicateNode");
        PhylogeneticTreeItem node2 = new PhylogeneticTreeItem();
        node2.setName("DuplicateNode");
        PhylogeneticTreeItem node3 = new PhylogeneticTreeItem();
        node3.setName("AnotherNode");
        
        // test equal nodes
        assertTrue("two equal nodes should be the same",node1.equals(node2));
        assertTrue("two equal nodes should be the same",node2.equals(node1));
        
        // test different nodes
        assertFalse("two different nodes should not match",node1.equals(node3));
        assertFalse("two different nodes should not match",node3.equals(node1));
                
    }
    
    /**
     * Test for equals.
     * tests equals between nodes with only distance
     */
    @Test
    public void testEqualsDistanceOnlyNoChildren() {
        // set up
        PhylogeneticTreeItem node1 = new PhylogeneticTreeItem();
        node1.setDistance(0.2);
        PhylogeneticTreeItem node2 = new PhylogeneticTreeItem();
        node2.setDistance(0.2);
        PhylogeneticTreeItem node3 = new PhylogeneticTreeItem();
        node3.setName("AnotherNode");
        node3.setDistance(0.3);
        
        // test equal nodes
        assertTrue("two equal nodes should be the same",node1.equals(node2));
        assertTrue("two equal nodes should be the same",node2.equals(node1));
        
        // test different nodes
        assertFalse("two different nodes should not match",node1.equals(node3));
        assertFalse("two different nodes should not match",node3.equals(node1));
    }
    
    /**
     * Test for equals.
     * tests equals between nodes with both distance and name
     */
    @Test
    public void testEqualsNameAndDistanceNoChildren() {
        // set up
        PhylogeneticTreeItem node1 = new PhylogeneticTreeItem();
        node1.setDistance(0.2);
        node1.setName("DuplicateNode");
        PhylogeneticTreeItem node2 = new PhylogeneticTreeItem();
        node2.setDistance(0.2);
        node2.setName("DuplicateNode");
        PhylogeneticTreeItem node3 = new PhylogeneticTreeItem();
        node3.setName("AnotherNode");
        node3.setDistance(0.3);
        
        // test equal nodes
        assertTrue("two equal nodes should be the same",node1.equals(node2));
        assertTrue("two equal nodes should be the same",node2.equals(node1));
        
        // test different nodes
        assertFalse("two different nodes should not match",node1.equals(node3));
        assertFalse("two different nodes should not match",node3.equals(node1));
    }
    
    /**
     * Test for equals.
     * tests equals with two empty nodes with children
     */
    @Test
    public void testEqualsNoDataWithChildren() {
        // set up
        PhylogeneticTreeItem node1 = new PhylogeneticTreeItem();
        PhylogeneticTreeItem node2 = new PhylogeneticTreeItem();
        PhylogeneticTreeItem node3 = new PhylogeneticTreeItem();
        node3.setName("AnotherNode");
        
        
        PhylogeneticTreeItem node11 = new PhylogeneticTreeItem();
        node11.setParent(node1);
        PhylogeneticTreeItem node21 = new PhylogeneticTreeItem();
        node21.setParent(node2);
        // test equal nodes
        assertTrue("two empty nodes should be the same",node1.equals(node2));
        assertTrue("two empty nodes should be the same",node2.equals(node1));
        
        // test different nodes
        assertFalse("two different nodes should not match",node1.equals(node3));
        assertFalse("two different nodes should not match",node3.equals(node1));
               
    }
    
    /**
     * Test for equals.
     * tests equals with two named nodes with children
     */
    @Test
    public void testEqualsNameOnlyWithChildren() {
        // set up
        PhylogeneticTreeItem node1 = new PhylogeneticTreeItem();
        node1.setName("DuplicateNode");
        PhylogeneticTreeItem node2 = new PhylogeneticTreeItem();
        node2.setName("DuplicateNode");
        PhylogeneticTreeItem node3 = new PhylogeneticTreeItem();
        node3.setName("AnotherNode");
        
        PhylogeneticTreeItem node11 = new PhylogeneticTreeItem();
        node11.setParent(node1);
        PhylogeneticTreeItem node21 = new PhylogeneticTreeItem();
        node21.setParent(node2);
        
        // test equal nodes
        assertTrue("two equal nodes should be the same",node1.equals(node2));
        assertTrue("two equal nodes should be the same",node2.equals(node1));
        
        // test different nodes
        assertFalse("two different nodes should not match",node1.equals(node3));
        assertFalse("two different nodes should not match",node3.equals(node1));
                
    }
    
    /**
     * Test for equals.
     * tests equals with two nodes with distance and children
     */
    @Test
    public void testEqualsDistanceOnlyWithChildren() {
        // set up
        PhylogeneticTreeItem node1 = new PhylogeneticTreeItem();
        node1.setDistance(0.2);
        PhylogeneticTreeItem node2 = new PhylogeneticTreeItem();
        node2.setDistance(0.2);
        PhylogeneticTreeItem node3 = new PhylogeneticTreeItem();
        node3.setName("AnotherNode");
        node3.setDistance(0.3);
        
        PhylogeneticTreeItem node11 = new PhylogeneticTreeItem();
        node11.setParent(node1);
        PhylogeneticTreeItem node21 = new PhylogeneticTreeItem();
        node21.setParent(node2);
        // test equal nodes
        assertTrue("two equal nodes should be the same",node1.equals(node2));
        assertTrue("two equal nodes should be the same",node2.equals(node1));
        
        // test different nodes
        assertFalse("two different nodes should not match",node1.equals(node3));
        assertFalse("two different nodes should not match",node3.equals(node1));
    }
    /**
     * Test for equals.
     * tests equals with two nodes with names, distance and children
     */
    @Test
    public void testEqualsNameAndDistanceWithChildren() {
        // set up
        PhylogeneticTreeItem node1 = new PhylogeneticTreeItem();
        node1.setDistance(0.2);
        node1.setName("DuplicateNode");
        PhylogeneticTreeItem node2 = new PhylogeneticTreeItem();
        node2.setDistance(0.2);
        node2.setName("DuplicateNode");
        PhylogeneticTreeItem node3 = new PhylogeneticTreeItem();
        node3.setName("AnotherNode");
        node3.setDistance(0.3);
        
        PhylogeneticTreeItem node11 = new PhylogeneticTreeItem();
        node11.setParent(node1);
        PhylogeneticTreeItem node21 = new PhylogeneticTreeItem();
        node21.setParent(node2);
        
        // test equal nodes
        assertTrue("two equal nodes should be the same",node1.equals(node2));
        assertTrue("two equal nodes should be the same",node2.equals(node1));
        
        // test different nodes
        assertFalse("two different nodes should not match",node1.equals(node3));
        assertFalse("two different nodes should not match",node3.equals(node1));
    }
    
    /**
     * Test for equals.
     * tests equals with itself
     */
    @Test
    public void testEqualsItself(){
        // set up
        PhylogeneticTreeItem node = new PhylogeneticTreeItem();
        
        assertTrue("a node should be equal to itself",node.equals(node));
        
        node.setDistance(0.2);
        assertTrue("a node should be equal to itself",node.equals(node));
        
        node.setName("name");
        
        assertTrue("a node should be equal to itself",node.equals(node));
    }
    
    /**
     * Test for equals.
     * tests equals with null
     */
    @Test
    public void testEqualsNull(){
     // set up
        PhylogeneticTreeItem node = new PhylogeneticTreeItem();
        
        assertFalse("a node should not be equal to null",node.equals(null));
    }
    
    /**
     * Test for hashCode.
     * tests hashCode with 2 empty nodes
     */
    @Test
    public void testHashCodeNoDataNoChildren() {
        // set up
        PhylogeneticTreeItem node1 = new PhylogeneticTreeItem();
        PhylogeneticTreeItem node2 = new PhylogeneticTreeItem();
              
        // test equal nodes
        assertEquals("two empty nodes should have the same haxh",node1.hashCode(),node2.hashCode());
      
    }
    
    /**
     * Test for hashCode.
     * tests hashCode with 2 named nodes
     */
    @Test
    public void testHashCodeNameOnlyNoChildren() {
        // set up
        PhylogeneticTreeItem node1 = new PhylogeneticTreeItem();
        node1.setName("DuplicateNode");
        PhylogeneticTreeItem node2 = new PhylogeneticTreeItem();
        node2.setName("DuplicateNode");
        
        // test equal nodes
        assertEquals("two equal nodes should hace the same hash",node1.hashCode(),node2.hashCode());
       
                
    }
    /**
     * Test for hashCode.
     * tests hashCode with 2 nodes with distance
     */
    @Test
    public void testHashCodeDistanceOnlyNoChildren() {
        // set up
        PhylogeneticTreeItem node1 = new PhylogeneticTreeItem();
        node1.setDistance(0.2);
        PhylogeneticTreeItem node2 = new PhylogeneticTreeItem();
        node2.setDistance(0.2);
        
        
        // test equal nodes
        assertEquals("two equal nodes should have the same hash",node1.hashCode(),node2.hashCode());
    }
    
    /**
     * Test for hashCode.
     * tests hashCode with 2 nodes with distance and names
     */
    @Test
    public void testHashCodeNameAndDistanceNoChildren() {
        // set up
        PhylogeneticTreeItem node1 = new PhylogeneticTreeItem();
        node1.setDistance(0.2);
        node1.setName("DuplicateNode");
        PhylogeneticTreeItem node2 = new PhylogeneticTreeItem();
        node2.setDistance(0.2);
        node2.setName("DuplicateNode");
        
        // test equal nodes
        assertEquals("two equal nodes should have the same hash",node1.hashCode(),node2.hashCode());
   }
    
    /**
     * Test for hashCode.
     * tests hashCode with 2 empty nodes with children
     */
    @Test
    public void testHashCodeNoDataWithChildren() {
        // set up
        PhylogeneticTreeItem node1 = new PhylogeneticTreeItem();
        PhylogeneticTreeItem node2 = new PhylogeneticTreeItem();
     
        PhylogeneticTreeItem node11 = new PhylogeneticTreeItem();
        node11.setParent(node1);
        PhylogeneticTreeItem node21 = new PhylogeneticTreeItem();
        node21.setParent(node2);
        
        // test equal nodes
        assertEquals("two empty nodes should have the same hash",node1.hashCode(),node2.hashCode());
                       
    }
    
    /**
     * Test for hashCode.
     * tests hashCode with 2 named nodes with children
     */
    @Test
    public void testHashCodeNameOnlyWithChildren() {
        // set up
        PhylogeneticTreeItem node1 = new PhylogeneticTreeItem();
        node1.setName("DuplicateNode");
        PhylogeneticTreeItem node2 = new PhylogeneticTreeItem();
        node2.setName("DuplicateNode");
                
        PhylogeneticTreeItem node11 = new PhylogeneticTreeItem();
        node11.setParent(node1);
        PhylogeneticTreeItem node21 = new PhylogeneticTreeItem();
        node21.setParent(node2);
        
        // test equal nodes
        assertEquals("two equal nodes should have the same hash",node1.hashCode(),node2.hashCode());
       
    }
    
    /**
     * Test for hashCode.
     * tests hashCode with 2 nodes with children and distance
     */
    @Test
    public void testHashCodeDistanceOnlyWithChildren() {
        // set up
        PhylogeneticTreeItem node1 = new PhylogeneticTreeItem();
        node1.setDistance(0.2);
        PhylogeneticTreeItem node2 = new PhylogeneticTreeItem();
        node2.setDistance(0.2);
              
        PhylogeneticTreeItem node11 = new PhylogeneticTreeItem();
        node11.setParent(node1);
        PhylogeneticTreeItem node21 = new PhylogeneticTreeItem();
        node21.setParent(node2);
        // test equal nodes
        assertEquals("two equal nodes should have the same hash",node1.hashCode(),node2.hashCode());
    }
    
    /**
     * Test for hashCode.
     * tests hashCode with 2 named nodes with children and distance
     */
    @Test
    public void testHashCodeNameAndDistanceWithChildren() {
        // set up
        PhylogeneticTreeItem node1 = new PhylogeneticTreeItem();
        node1.setDistance(0.2);
        node1.setName("DuplicateNode");
        PhylogeneticTreeItem node2 = new PhylogeneticTreeItem();
        node2.setDistance(0.2);
        node2.setName("DuplicateNode");
                
        PhylogeneticTreeItem node11 = new PhylogeneticTreeItem();
        node11.setParent(node1);
        PhylogeneticTreeItem node21 = new PhylogeneticTreeItem();
        node21.setParent(node2);
        
        // test equal nodes
        assertEquals("two equal nodes should have the same hash",node1.hashCode(),node2.hashCode());
    }

}
