package nl.tudelft.lifetiles.tree;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * a tree to store the relation between samples.
 *
 * @author Albert Smit
 *
 */
public class PhylogeneticTreeItem {
    /**
     * Used to give each node a unique id.
     */
    private static AtomicInteger nextID = new AtomicInteger();

    /**
     * The parent node, null when this node is the root node.
     */
    private PhylogeneticTreeItem parent;
    /**
     * The list of children of this node.
     */
    private ArrayList<PhylogeneticTreeItem> children;

    /**
     * The name of the sample. This is an optional field.
     */
    private String name;
    /**
     * The distance between samples. This is an optinal field.
     */
    private double distance;
    /**
     * A unique id for each node.
     */
    private int id;

    /**
     * Creates a new PhylogeneticTreeItem. Will initialize the ArrayList storing
     * the children and assign a new and unique id to the node.
     */
    public PhylogeneticTreeItem() {
        children = new ArrayList<PhylogeneticTreeItem>();
        this.id = nextID.incrementAndGet();
    }

    /**
     * Returns a String representation of the PhylogeneticTreeItem. The String
     * will have the following format: <Node: id, name: name, Distance:
     * distance, parent: parentID> or when the PhylogeneticTreeItem has no
     * parent: <Node: id, Name: name, Distance: distance, ROOT>
     *
     * @return the String version of the object.
     */
    public final String toString() {
        String result = "<Node: " + id + ", Name: " + name + ", Distance: "
                + distance;
        if (parent != null) {
            result += ", parent: " + parent.getId();
        } else {
            result += ", ROOT ";
        }
        return result + ">";
    }

    /**
     * Adds a child to the PhylogeneticTreeItem. This method will add the
     * PhylogeneticTreeItem child to the ArrayList storing the children of this
     * node
     *
     * @param child
     *            the PhylogeneticTreeItem that needs to be added to the tree
     */
    public final void addChild(final PhylogeneticTreeItem child) {
        children.add(child);
    }

    /**
     * Returns the ArrayList of children.
     *
     * @return the ArrayList containing all children of this node
     */
    public final ArrayList<PhylogeneticTreeItem> getChildren() {
        return children;
    }

    /**
     * Sets the parent to be the node passed to the method This method will set
     * the parent and also add itself to the list of children in the parent
     * node.
     *
     * @param parentNode
     *            the node that will be this nodes parent
     */
    public final void setParent(final PhylogeneticTreeItem parentNode) {
        this.parent = parentNode;
        this.parent.addChild(this);
    }

    /**
     * Returns this nodes parent node.
     *
     * @return the PhylogeneticTreeItem that is this nodes parent
     */
    public final PhylogeneticTreeItem getParent() {
        return parent;
    }

    /**
     * Returns the name stored in this node. name is an optional property, so
     * this method can return null.
     *
     * @return the name of this node
     */
    public final String getName() {
        return name;
    }

    /**
     * Set this nodes name to the passed String.
     *
     * @param n
     *            the name of this node
     */
    public final void setName(final String n) {
        this.name = n;
    }

    /**
     * Returns the distance stored in this node. distance is an optional
     * property, so the distance can often be 0.0.
     *
     * @return the distance of this node
     */
    public final double getDistance() {
        return distance;
    }

    /**
     * Sets this nodes distance to the passed double.
     *
     * @param d
     *            the distance between the nodes
     */
    public final void setDistance(final double d) {
        this.distance = d;
    }

    /**
     * Returns the Id of this node. because name and distance are optional this
     * provides an easy way of identifying nodes
     *
     * @return the unique id of this PhylogeneticTreeItem
     */
    public final int getId() {
        return id;
    }

}
