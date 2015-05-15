package nl.tudelft.lifetiles.tree.models;

import static org.junit.Assert.*;
import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeFactory;
import nl.tudelft.lifetiles.tree.model.PhylogeneticTreeItem;

import org.junit.Test;

/**
 * @author Albert Smit
 *
 */
public class TestPhylogeneticTreeFactory {

    /**
     * test the parser without any data in the tree.
     */
    @Test
    public void testPhylogeneticTreeFactoryNoNamesNoDistance() {
        // create the actual tree
        String tree = "(,,(,));";
        PhylogeneticTreeFactory np = new PhylogeneticTreeFactory(tree);
        assertTrue("Parser constructor failed, np is NULL", np != null);
        PhylogeneticTreeItem rootActual = np.getRoot();

        // create the expected Tree
        // root node
        PhylogeneticTreeItem rootExpected = new PhylogeneticTreeItem();
        // add 3 child nodes
        PhylogeneticTreeItem current = new PhylogeneticTreeItem();
        current.setParent(rootExpected);
        current = new PhylogeneticTreeItem();
        current.setParent(rootExpected);
        current = new PhylogeneticTreeItem();
        current.setParent(rootExpected);
        // add 2 child nodes to the third node
        PhylogeneticTreeItem current2 = new PhylogeneticTreeItem();
        current2.setParent(current);
        current2 = new PhylogeneticTreeItem();
        current2.setParent(current);

        // compare the trees
        assertEquals("both trees do not match",rootExpected,rootActual);
        
    }

    /**
     * test the parser with named leaf nodes.
     */
    @Test
    public void testPhylogeneticTreeFactoryNamedLeafsNoDistance() {
        // create the actual tree
        String tree = "(A,B,(C,D));";
        PhylogeneticTreeFactory np = new PhylogeneticTreeFactory(tree);
        assertTrue("Parser constructor failed, np is NULL", np != null);
        PhylogeneticTreeItem rootActual = np.getRoot();

        // create the expected Tree
        // root node
        PhylogeneticTreeItem rootExpected = new PhylogeneticTreeItem();
        // add 3 child nodes
        PhylogeneticTreeItem current = new PhylogeneticTreeItem();
        current.setParent(rootExpected);
        current.setName("A");
        current = new PhylogeneticTreeItem();
        current.setName("B");
        current.setParent(rootExpected);
        current = new PhylogeneticTreeItem();
        current.setParent(rootExpected);
        // add 2 child nodes to the third node
        PhylogeneticTreeItem current2 = new PhylogeneticTreeItem();
        current2.setParent(current);
        current2.setName("C");
        current2 = new PhylogeneticTreeItem();
        current2.setParent(current);
        current2.setName("D");

        // compare the trees
        assertEquals("both trees do not match",rootExpected,rootActual);
        
    }
    
    /**
     * test the parser with named nodes.
     */
    @Test
    public void testPhylogeneticTreeFactoryNamednodesNoDistance() {
        // create the actual tree
        String tree = "(A,B,(C,D)E)F;";
        PhylogeneticTreeFactory np = new PhylogeneticTreeFactory(tree);
        assertTrue("Parser constructor failed, np is NULL", np != null);
        PhylogeneticTreeItem rootActual = np.getRoot();

        // create the expected Tree
        // root node
        PhylogeneticTreeItem rootExpected = new PhylogeneticTreeItem();
        rootExpected.setName("F");
        // add 3 child nodes
        PhylogeneticTreeItem current = new PhylogeneticTreeItem();
        current.setParent(rootExpected);
        current.setName("A");
        current = new PhylogeneticTreeItem();
        current.setName("B");
        current.setParent(rootExpected);
        current = new PhylogeneticTreeItem();
        current.setParent(rootExpected);
        current.setName("E");
        // add 2 child nodes to the third node
        PhylogeneticTreeItem current2 = new PhylogeneticTreeItem();
        current2.setParent(current);
        current2.setName("C");
        current2 = new PhylogeneticTreeItem();
        current2.setParent(current);
        current2.setName("D");

        // compare the trees
        assertEquals("both trees do not match",rootExpected,rootActual);
       
    }
    
    /**
     * test the parser with unnamed nodes and distances.
     */
    @Test
    public void testPhylogeneticTreeFactoryUnnamednodesAndDistance() {
        // create the actual tree
        String tree = "(:0.1,:0.2,(:0.3,:0.4):0.5);";
        PhylogeneticTreeFactory np = new PhylogeneticTreeFactory(tree);
        assertTrue("Parser constructor failed, np is NULL", np != null);
        PhylogeneticTreeItem rootActual = np.getRoot();

        // create the expected Tree
        // root node
        PhylogeneticTreeItem rootExpected = new PhylogeneticTreeItem();
        // add 3 child nodes
        PhylogeneticTreeItem current = new PhylogeneticTreeItem();
        current.setParent(rootExpected);
        current.setDistance(0.1);
        current = new PhylogeneticTreeItem();
        current.setDistance(0.2);
        current.setParent(rootExpected);
        current = new PhylogeneticTreeItem();
        current.setParent(rootExpected);
        current.setDistance(0.5);
        // add 2 child nodes to the third node
        PhylogeneticTreeItem current2 = new PhylogeneticTreeItem();
        current2.setParent(current);
        current2.setDistance(0.4);
        current2 = new PhylogeneticTreeItem();
        current2.setParent(current);
        current2.setDistance(0.3);

        // compare the trees
        assertEquals("both trees do not match",rootExpected,rootActual);
       
    }
    
    /**
     * test the parser with named nodes and distance.
     */
    @Test
    public void testPhylogeneticTreeFactoryNamednodesAndDistance() {
        // create the actual tree
        String tree = "(A:0.1,B:0.2,(C:0.3,D:0.4)E:0.5)F;";
        PhylogeneticTreeFactory np = new PhylogeneticTreeFactory(tree);
        assertTrue("Parser constructor failed, np is NULL", np != null);
        PhylogeneticTreeItem rootActual = np.getRoot();

        // create the expected Tree
        // root node
        PhylogeneticTreeItem rootExpected = new PhylogeneticTreeItem();
        rootExpected.setName("F");
        // add 3 child nodes
        PhylogeneticTreeItem current = new PhylogeneticTreeItem();
        current.setParent(rootExpected);
        current.setName("A");
        current.setDistance(0.1);
        current = new PhylogeneticTreeItem();
        current.setName("B");
        current.setDistance(0.2);
        current.setParent(rootExpected);
        current = new PhylogeneticTreeItem();
        current.setParent(rootExpected);
        current.setName("E");
        current.setDistance(0.5);
        // add 2 child nodes to the third node
        PhylogeneticTreeItem current2 = new PhylogeneticTreeItem();
        current2.setParent(current);
        current2.setName("C");
        current2.setDistance(0.3);
        current2 = new PhylogeneticTreeItem();
        current2.setParent(current);
        current2.setName("D");
        current2.setDistance(0.4);
        

        // compare the trees
        assertEquals("both trees do not match",rootExpected,rootActual);
       
    }


}
