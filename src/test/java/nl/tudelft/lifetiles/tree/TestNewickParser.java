/**
 * 
 */
package nl.tudelft.lifetiles.tree;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Albert Smit
 *
 */
public class TestNewickParser {

    /**
     * test the parser without any data in the tree.
     */
    @Test
    public void testNewickParserNoNamesNoDistance() {
        // create the actual tree
        String tree = "(,,(,));";
        NewickParser np = new NewickParser(tree);
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
     * test the parser without any data in the tree.
     */
    @Test
    public void testNewickParserNamedLeafsNoDistance() {
        // create the actual tree
        String tree = "(A,B,(C,D));";
        NewickParser np = new NewickParser(tree);
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
     * Test method for {@link nl.tudelft.lifetiles.tree.NewickParser#getRoot()}.
     */
    @Test
    public void testGetRoot() {
        fail("Not yet implemented");
    }

}
