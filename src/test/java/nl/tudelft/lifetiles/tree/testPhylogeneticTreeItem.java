package nl.tudelft.lifetiles.tree;

import static org.junit.Assert.*;

import org.junit.Test;

public class testPhylogeneticTreeItem {


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
    
    @Test
    public void testEqualsNull(){
     // set up
        PhylogeneticTreeItem node = new PhylogeneticTreeItem();
        
        assertFalse("a node should not be equal to null",node.equals(null));
    }
}
