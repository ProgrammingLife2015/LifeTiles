package nl.tudelft.lifetiles.tree;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * 
 * @author albert
 *
 */
public class PhylogeneticTreeItem {
	private static AtomicInteger nextID = new AtomicInteger();
    
	private PhylogeneticTreeItem parent;
	private ArrayList<PhylogeneticTreeItem> children;
	
	private String name;
	private double distance;
	private int id;
	
	/**
	 * Creates a new PhylogeneticTreeItem.
	 * Will initialize the ArrayList storing the children and
	 * assign a new and unique id to the node. 
	 */
	public PhylogeneticTreeItem(){
		children = new ArrayList<PhylogeneticTreeItem>();
		this.id = nextID.incrementAndGet();
	}
	/**
	 * Returns a String representation of the PhylogeneticTreeItem.
	 * The String will have the following format:
	 * <Node: id, name: name, Distance: distance, parent: parentID>
	 * or when the PhylogeneticTreeItem has no parent:
	 * <Node: id, Name: name, Distance: distance, ROOT>
	 */
	public String toString(){
		String result ="<Node: " + id + ", Name: " + name + ", Distance: " + distance ;
		if(parent != null){
			result += ", parent: "+ parent.getId();
		}
		else{
			result += ", ROOT ";
		}
		return  result + ">";
	}
	
	/**
	 * Adds a child to the PhylogeneticTreeItem.
	 * This method will add the PhylogeneticTreeItem child to the arraylist storing the children
	 * of this node
	 * @param child the PhylogeneticTreeItem that needs to be added to the tree
	 */
	public void addChild(PhylogeneticTreeItem child){
		children.add(child);
	}
	
	/** 
	 * Returns the ArrayList of children.
	 * @return the ArrayList containing all children of this node
	 */
	public ArrayList<PhylogeneticTreeItem> getChildren(){
		return children;
	}
	
	/**
	 * Sets the parent to be the node passed to the method
	 * This method will set the parent and also add itself to the list of children
	 * in the parent node.
	 * @param parent the node that will be this nodes parent
	 */
	public void setParent(PhylogeneticTreeItem parent){
		this.parent = parent;
		this.parent.addChild(this);
	}
	/**
	 * Returns this nodes parent node.
	 * @return the PhylogeneticTreeItem that is this nodes parent
	 */
	public PhylogeneticTreeItem getParent(){
		return parent;
	}
	
	/**
	 * Returns the name stored in this node.
	 * name is an optional property, so this method can return null.
	 * @return the name of this node
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set this nodes name to the passed String.
	 * @param name the name of this node
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
     * Returns the distance stored in this node.
     * distance is an optional property, so the distance can often be 0.0.
     * @return the distance of this node
     */
	public double getDistance() {
		return distance;
	}
	
	/**
	 * Sets this nodes distance to the passed double.
	 * @param distance the distance between the nodes
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * Returns the Id of this node.
	 * because name and distance are optional this provides an easy way of identifying nodes
	 * @return the unique id of this PhylogeneticTreeItem
	 */
	public int getId() {
		return id;
	}



}
