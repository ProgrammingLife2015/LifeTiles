package nl.tudelft.lifetiles.tree.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import nl.tudelft.lifetiles.sequence.model.Sequence;

/**
 * A tree to store the relation between samples.
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
     * The list of children of this node.
     */
    private final List<PhylogeneticTreeItem> children;
    /**
     * The distance between samples. This is an optinal field.
     */
    private double distance;

    /**
     * A unique id for each node.
     */
    private final int ident;
    /**
     * The name of the sample. This is an optional field.
     */
    private String name;
    /**
     * The parent node, null when this node is the root node.
     */
    private PhylogeneticTreeItem parent;

    /**
     * The sequence this node is associated with.
     */
    private Sequence sequence;
    /**
     * The sequences this nodes descendants are associated with.
     */
    private Set<Sequence> childSequences;

    /**
     * Creates a new PhylogeneticTreeItem. Will initialize the ArrayList storing
     * the children and assign a new and unique id to the node.
     */
    public PhylogeneticTreeItem() {
        children = new ArrayList<PhylogeneticTreeItem>();
        this.ident = nextID.incrementAndGet();
    }

    /**
     * Method to determine the amount of descendant nodes each node has.
     *
     * @return the amount of descendant nodes
     */
    public int numberDescendants() {
        int result = 0;
        for (PhylogeneticTreeItem child : children) {
            result += child.numberDescendants() + 1;
        }

        return result;
    }
    /**
     * Method to determine the maximum amount of layers.
     * @return the amount of layers
     */
    public int maxDepth() {
        int result = 0;
        for (PhylogeneticTreeItem child: children) {
            result = Math.max(result, child.maxDepth() + 1);
        }

        return result;
    }

    /**
     * creates a Set containing this nodes sequence, and the sequences of its
     * children.
     *
     * @return a set with all sequences that descend from this node.
     */
    public Set<Sequence> getSequences() {
        Set<Sequence> result = new HashSet<Sequence>();
        if (sequence != null) {
            result.add(sequence);
        }
        for (PhylogeneticTreeItem child : children) {
            result.addAll(child.getSequences());
        }
        return result;
    }

    /**
     * Adds a child to the PhylogeneticTreeItem. This method will add the
     * PhylogeneticTreeItem child to the ArrayList storing the children of this
     * node.
     *
     * @param child
     *            the PhylogeneticTreeItem that needs to be added to the tree
     */
    public void addChild(final PhylogeneticTreeItem child) {
        children.add(child);
    }

    /**
     * Compares this with another Object. returns true when both are the same.
     * two PhylogeneticTreeItems are considered the same when both have the
     * same:
     *
     * <ol>
     * <li>name or both have no name</li>
     * <li>distance</li>
     * <li>children, order does not matter</li>
     * </ol>
     *
     * @param other
     *            the object to compare with
     *
     * @return true if both are the same, otherwise false
     */

    @Override
    public boolean equals(final Object other) {
        if (other == null) {
            return false;
        } else if (other == this) {
            return true;
        } else if (other instanceof PhylogeneticTreeItem) {
            PhylogeneticTreeItem that = (PhylogeneticTreeItem) other;
            boolean res;
            // compare name
            if (name == null && that.getName() == null) {
                // both are empty and thus the same
                res = true;
            } else if (name == null) {
                // the names are not both empty so not the same
                res = false;
            } else {
                // name is not null check if it is the same
                res = name.equals(that.getName());
            }

            // compare distance
            res = res && Double.compare(distance, that.getDistance()) == 0;

            // compare children
            for (PhylogeneticTreeItem child : children) {
                res = res && that.getChildren().contains(child);
            }
            return res;
        } else {
            return false;
        }
    }

    /**
     * Returns the ArrayList of children.
     *
     * @return the ArrayList containing all children of this node
     */
    public List<PhylogeneticTreeItem> getChildren() {
        return children;
    }

    /**
     * Returns the distance stored in this node. distance is an optional
     * property, so the distance can often be 0.0.
     *
     * @return the distance of this node
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Returns the Id of this node. because name and distance are optional this
     * provides an easy way of identifying nodes
     *
     * @return the unique id of this PhylogeneticTreeItem
     */
    public int getId() {
        return ident;
    }

    /**
     * Returns the name stored in this node. name is an optional property, so
     * this method can return null.
     *
     * @return the name of this node
     */
    public String getName() {
        return name;
    }

    /**
     * Returns this nodes parent node.
     *
     * @return the PhylogeneticTreeItem that is this nodes parent
     */
    public PhylogeneticTreeItem getParent() {
        return parent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + children.hashCode();
        long temp;
        temp = Double.doubleToLongBits(distance);
        result = prime * result + (int) (temp ^ (temp >>> prime + 1));
        result = prime * result;
        if (name != null) {
            result += name.hashCode();
        }
        return result;
    }

    /**
     * Sets this nodes distance to the passed double.
     *
     * @param distance
     *            the distance between the nodes
     */
    public void setDistance(final double distance) {
        this.distance = distance;
    }

    /**
     * Set this nodes name to the passed String.
     *
     * @param name
     *            the name of this node
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Sets the parent to be the node passed to the method This method will set
     * the parent and also add itself to the list of children in the parent
     * node.
     *
     * @param parentNode
     *            the node that will be this nodes parent
     */
    public void setParent(final PhylogeneticTreeItem parentNode) {
        this.parent = parentNode;
        this.parent.addChild(this);
    }

    /**
     * @return the sequence
     */
    public Sequence getSequence() {
        return sequence;
    }

    /**
     * @param seq
     *            the sequence to set
     */
    public void setSequence(final Sequence seq) {
        this.sequence = seq;
    }

    /**
     * Returns a String representation of the PhylogeneticTreeItem. The String
     * will have the following format:
     * <code>&lt;Node: id, name: name, Distance:distance, parent: parentID&gt;
     * </code> or when the PhylogeneticTreeItem has no parent:
     * <code>&lt;Node: id, Name: name, Distance: distance, ROOT&gt;</code>
     *
     * @return the String version of the object.
     */
    @Override
    public String toString() {
        String suffix;
        if (parent == null) {
            suffix = ", ROOT ";
        } else {
            suffix = ", parent: " + parent.getId();
        }
        String result = "<Node: " + ident + ", Name: " + name + ", Distance: "
                + distance + suffix + ">";

        return result;
    }

    /**
     * @return returns the list of child sequences stored in this node
     */
    public Set<Sequence> getChildSequences() {
        return childSequences;
    }

    /**
     * Sets this nodes childSequences field to the set returned by getSequences.
     */
    public void setChildSequences() {
        this.childSequences = this.getSequences();
    }

}
