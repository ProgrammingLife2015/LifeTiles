package nl.tudelft.lifetiles.tree;

import java.util.ArrayList;
import java.util.List;
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
    private List<PhylogeneticTreeItem> children;

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
    public final List<PhylogeneticTreeItem> getChildren() {
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
     * Calculates a hash for the tree.
     */
    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + children.hashCode();
        long temp;
        temp = Double.doubleToLongBits(distance);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        if (name != null) {
            result = prime * result + name.hashCode();
        } else {
            result = prime * result;
        }
        return result;
    }

    /**
     * compares this with another Object. returns true when both are the same.
     * two PhylogeneticTreeItems are considered the same when:
     *
     * 1. both have the same name or both have no name
     * 2. both have the same distance
     * 3. both have the same children, order does not matter
     *
     * @param other
     *            the object to compare with
     *
     * @return true if both are the same, otherwise false
     */

    @Override
    public final boolean equals(final Object other) {
        if (other == null) {
            return false;
        } else if (other == this) {
            return true;
        } else if (other instanceof PhylogeneticTreeItem) {
            PhylogeneticTreeItem that = (PhylogeneticTreeItem) other;
            boolean result;
            // compare name
            if (name == null && that.getName() == null) {
                // both are empty and thus the same
                result = true;
            } else if (name == null) {
                // the names are not both empty so not the same
                result = false;
            } else {
                // name is not null check if it is the same
                result = name.equals(that.getName());
            }

            // compare distance
            result = result && (distance == that.getDistance());

            // compare children
            for (PhylogeneticTreeItem child : children) {
                result = result && that.getChildren().contains(child);
            }
            return result;
        } else {
            return false;
        }
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
